package com.ztravel.payment.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.constants.OperatorConstants;
import com.ztravel.common.entity.CouponSnapshot;
import com.ztravel.common.enums.AccountType;
import com.ztravel.common.enums.GateType;
import com.ztravel.common.enums.InoutDetailType;
import com.ztravel.common.enums.InoutType;
import com.ztravel.common.enums.OrderPayStatus;
import com.ztravel.common.enums.OrderType;
import com.ztravel.common.enums.PaymentStatus;
import com.ztravel.common.enums.PaymentType;
import com.ztravel.common.enums.ProductType;
import com.ztravel.common.enums.TradeStatus;
import com.ztravel.common.enums.TradeType;
import com.ztravel.common.enums.VoucherType;
import com.ztravel.common.order.OrderPaidBean;
import com.ztravel.common.payment.CouponGrantBean;
import com.ztravel.common.payment.DNCouponGrantBean;
import com.ztravel.common.payment.OrderPayBean;
import com.ztravel.common.payment.TradeBean;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.common.util.IdGeneratorUtil;
import com.ztravel.order.client.service.IOrderLogClientService;
import com.ztravel.order.client.service.IOrderThriftClientService;
import com.ztravel.order.po.VoucherOrder;
import com.ztravel.payment.bo.CouponBo;
import com.ztravel.payment.dao.AccountDao;
import com.ztravel.payment.dao.AccountHistoryDao;
import com.ztravel.payment.dao.OrderPayDao;
import com.ztravel.payment.dao.PaymentDao;
import com.ztravel.payment.http.HttpRequest;
import com.ztravel.payment.http.HttpRequestType;
import com.ztravel.payment.model.PaymentResultEntity;
import com.ztravel.payment.paygate.PaygateClient;
import com.ztravel.payment.paygate.PaygateEncryptUtil;
import com.ztravel.payment.paygate.model.PayResultBean;
import com.ztravel.payment.paygate.model.RefundRequestBean;
import com.ztravel.payment.paygate.model.RefundResponseBean;
import com.ztravel.payment.paygate.model.RefundResultBean;
import com.ztravel.payment.po.AccountHistory;
import com.ztravel.payment.po.OrderPay;
import com.ztravel.payment.po.Payment;
import com.ztravel.payment.po.factory.ModelFactory;
import com.ztravel.payment.processor.CouponProcessor;

/**
 * @author zuoning.shen
 *
 */
@Service
public class ThirdPaymentService{
	private static Logger logger = LoggerFactory.getLogger(ThirdPaymentService.class);

	/* 终端标识号 */
	@Value("#{paygateProps.clientId}")
	private String clientId;
	/* 付款URL */
	@Value("#{paygateProps.payUrl}")
	private String payUrl;
	@Value("#{paygateProps.mobilePayUrl}")
	private String mobilePayUrl;
	/* 签名key */
	@Value("#{paygateProps.signKey}")
	private String signKey;

	@Resource
	private PaygateClient paygateClient;
	@Resource
	private OrderPayDao orderPayDao;
	@Resource
    private PaymentDao paymentDao;
	@Resource
    private AccountDao accountDao;
	@Resource
    private AccountHistoryDao accountHistoryDao;
	@Resource
    private CouponBo couponBo;
	@Resource
    private CouponProcessor couponProcessor;
	@Resource
	private IdGeneratorUtil idGeneratorUtil ;

    @Resource(name="orderLogClientServiceImpl")
	IOrderLogClientService orderLogClientServiceImpl;

    @Resource(name="orderThriftClientServiceImpl")
	private IOrderThriftClientService orderThriftClientServiceImpl;

	@Resource
	private TradeService tradeService;

	public String getSignStr(Map<String, String> params) {
		return PaygateEncryptUtil.getSignStr(params, signKey);
	}

	public boolean verifySignStr(Map<String, String> params, String signStr) {
		return PaygateEncryptUtil.verifySignStr(params, signStr, signKey);
	}

	public String requestPaymentUrl(OrderPayBean order) throws Exception {
        Map<String, String> params = genRequestParams(order);

        try {
            // Get data as a String
            String responseBody = order.isMobile() ? HttpRequest.sendRequest(mobilePayUrl, params, HttpRequestType.POST) : HttpRequest.sendRequest(payUrl, params,
                    HttpRequestType.POST);

            Gson gson = new Gson();
            Map<String, String> responseMap = gson.<Map<String, String>>fromJson(responseBody, HashMap.class);

            String payUrl = responseMap.get("payUrl");

            if (payUrl != null && payUrl.length() > 0 && verifySignStr(responseMap, responseMap.get("sign"))) {
                logger.info("get payUrl: {}", payUrl);
                return payUrl;
            } else {
                logger.error("error getting response: {}", responseMap.get("retMsg"));
            }

        } catch (Exception e) {
            logger.error("error getting response.", e);
        }
        throw new Exception("获取支付链接失败");
    }

	@Profiled
	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
	public boolean processPaymentResult(boolean needpay, PayResultBean payResult) throws Exception {
		String orderId = payResult.getOrderNum();
        OrderPay orderPay = orderPayDao.selectByOrderId(orderId);
        return processPaymentResult(payResult, orderPay) ;
	}

	/**
	 *
	 * @param needpay 用于测试环境修改支付金额
	 * @param payResult
	 * @return 支付处理结果，true表示成功， false表示已处理,  exception表示处理异常
	 * @throws Exception
	 */
	@Profiled
	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
	public boolean processPaymentResult(PayResultBean payResult, OrderPay orderPay) throws Exception {
		String orderId = payResult.getOrderNum();
        String traceNum = payResult.getTraceNum();

        Payment payment = paymentDao.selectByTraceNum(traceNum);
        if(payment != null && PaymentStatus.PAID.name().equals(payment.getPaymentStatus())){
            logger.info("Payment result already processed, orderId: {}, traceNum: {}", orderId, traceNum);
            return false;
        }
        if(orderPay == null){
        	orderPay = orderPayDao.selectByOrderId(orderId) ;
        }
        if(orderPay != null && OrderPayStatus.PAID.name().equals(orderPay.getOrderPayStatus())){
            logger.info("Payment result already processed, orderId: {}, traceNum: {}", orderId, traceNum);
            return false;
        }
        //Lock Account
        accountDao.selectForUpdate(orderPay.getMemberId());
        updatePayment(orderPay, payResult);
        updateOrderPay(orderPay, payResult);
		return true;
	}

    private void updatePayment(OrderPay orderPay, PayResultBean payResult) throws Exception{
	    String orderId = payResult.getOrderNum();
        String traceNum = payResult.getTraceNum();
        String bankPaymentTime = payResult.getBankPaymentTime();
        String gateCode = payResult.getGateType();
        PaymentType paymentType = PaymentType.fromGateType(GateType.fromGateCode(gateCode));
        DateTime now = DateTime.now();
        Payment payment = paymentDao.selectPayment(orderId, paymentType);
        payment.setTraceNum(traceNum);
        payment.setBankPaymentTime(bankPaymentTime);
        payment.setPaymentStatus(PaymentStatus.PAID.name());
        payment.setUpdatedby(OperatorConstants.AUTO_USER);
        payment.setUpdated(now);
        paymentDao.update(payment);
        ProductType productType = ProductType.valueOf(orderPay.getProductType()) ;

        if(productType == ProductType.VOUCHER){
        	List<VoucherOrder> voucherOrders = orderThriftClientServiceImpl.selectVoucherOrderbyCBID(orderId) ;
        	for(VoucherOrder voucherOrder : voucherOrders){
        		CouponSnapshot couponSnapshot = JSONObject.toJavaObject(JSONObject.parseObject(voucherOrder.getCouponSnapshot()), CouponSnapshot.class) ;
        		CouponGrantBean bean = buildDNCouponGrantBean(couponSnapshot, voucherOrder.getVoucherCode(), orderPay.getMemberId()) ;
        		logger.debug("DNCouponGrantBean:::{}", TZBeanUtils.describe(bean));
        		couponProcessor.buy(bean, orderPay.getOrderId()) ;
        	}
        }else if(productType == ProductType.TRAVEL || productType == ProductType.VISA || productType == ProductType.UNVISA){
        	Payment couponPayment = paymentDao.selectPayment(orderId, PaymentType.Coupon);
            if(couponPayment != null){
                couponPayment.setPaymentStatus(PaymentStatus.PAID.name());
                couponPayment.setUpdatedby(OperatorConstants.AUTO_USER);
                couponPayment.setUpdated(now);
                paymentDao.update(couponPayment);

                String couponItemId = couponPayment.getCouponItemId();
                couponBo.consume(couponItemId, orderId);
                createAccountHistory(orderPay);
            }
        }
	}

    private DNCouponGrantBean buildDNCouponGrantBean(CouponSnapshot couponSnapshot, String voucherCode, String memberId) throws Exception{
		DNCouponGrantBean bean = new DNCouponGrantBean() ;
		bean.setActivityId(couponSnapshot.getActivityId());
		bean.setMemberId(memberId);
		bean.setAmount(couponSnapshot.getAmount());
		bean.setDescription(couponSnapshot.getDescription());
		bean.setName(couponSnapshot.getName());
		bean.setCouponCode(couponSnapshot.getCouponId());
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		bean.setValidDateFrom(DateTime.parse(couponSnapshot.getValidDateFrom(), format));
		bean.setValidDateTo(DateTime.parse(couponSnapshot.getValidDateTo(), format));
		bean.setVoucherCode(voucherCode);
		bean.setVoucherType(VoucherType.NORMAL);
		return bean ;
	}

	private void updateOrderPay(OrderPay orderPay, PayResultBean payResult) throws Exception{
	    long amount = payResult.getAmount();
	    if(OrderPayStatus.AWAIT.name().equals(orderPay.getOrderPayStatus())){
	        orderPay.setOrderPayStatus(OrderPayStatus.PAID.name());
	    }
	    long paidAmount = amount + orderPay.getPaidAmount();
	    orderPay.setPaidAmount(paidAmount);
	    orderPay.setTraceNum(payResult.getTraceNum());
	    orderPay.setUpdatedby(OperatorConstants.AUTO_USER);
	    orderPay.setUpdated(DateTime.now());
	    orderPayDao.update(orderPay);
	}

	private void createAccountHistory4VoucherRefunded(OrderPay orderPay, String orderId, long refundAmount) throws Exception{
	    AccountHistory accountHistory = ModelFactory.createAccountHistory(idGeneratorUtil.getAccountHistoryId());
        accountHistory.setMemberId(orderPay.getMemberId());
        accountHistory.setOrderId(orderId);
        accountHistory.setProductType(orderPay.getProductType());
        accountHistory.setAccountType(AccountType.COUPON.name());
        accountHistory.setAmount(refundAmount);
        accountHistory.setInoutType(InoutType.OUTGO.name());
        accountHistory.setInoutDetailType(InoutDetailType.REFUND_VOUCHER.name());
        accountHistoryDao.insert(accountHistory);
    }

	private void createAccountHistory(OrderPay orderPay) throws Exception{
	    AccountHistory accountHistory = ModelFactory.createAccountHistory(idGeneratorUtil.getAccountHistoryId());
        accountHistory.setMemberId(orderPay.getMemberId());
        accountHistory.setOrderId(orderPay.getOrderId());
        accountHistory.setProductType(orderPay.getProductType());
        accountHistory.setAccountType(AccountType.COUPON.name());
        accountHistory.setAmount(orderPay.getUseCoupon());
        accountHistory.setInoutType(InoutType.OUTGO.name());
        accountHistory.setInoutDetailType(InoutDetailType.PAY_ORDER.name());
        accountHistoryDao.insert(accountHistory);
    }

	private void createAccountHistoryRefund(OrderPay orderPay) throws Exception{
	    AccountHistory accountHistory = ModelFactory.createAccountHistory(idGeneratorUtil.getAccountHistoryId());
        accountHistory.setMemberId(orderPay.getMemberId());
        accountHistory.setOrderId(orderPay.getOrderId());
        accountHistory.setProductType(orderPay.getProductType());
        accountHistory.setAccountType(AccountType.COUPON.name());
        accountHistory.setAmount(orderPay.getUseCoupon());
        accountHistory.setInoutType(InoutType.INCOME.name());
        accountHistory.setInoutDetailType(InoutDetailType.REFUND_ORDER.name());
        accountHistoryDao.insert(accountHistory);
    }

	/**
	 *
	 * @param refundOrderId
	 * @param orderGroupId
	 * @return
	 * @throws Exception
	 */
	@Profiled
	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
	public boolean processRefundRequest(String refundOrderId, String orderGroupId, long totalRefundAmount) throws Exception {
		return true;
	}

//	@Async("paygateExecutor")
	public RefundResponseBean requestRefund(RefundRequestBean refundBean) throws Exception {
		return paygateClient.sendRequest(PaygateClient.Service.REFUND, refundBean, RefundResponseBean.class);
	}

	public void sendOperatorMessageAndLogSave(String orderNum, String refundOrderNum, long refundAmount, String operator, ProductType productType){
		try{
			if(productType == ProductType.TRAVEL){
				orderThriftClientServiceImpl.sendOperatorMessage(orderNum,true);
				orderLogClientServiceImpl.save(orderNum,operator, refundOrderNum + "退款成功,金额:" + Long.valueOf(refundAmount).floatValue()/100f, "");
			}else if(productType == ProductType.OPCONFIRM){
				String orderNo = orderThriftClientServiceImpl.getOrderFromCommonOrder(orderNum) ;
				orderLogClientServiceImpl.save(orderNo,operator, refundOrderNum + "退款成功,金额:" + Long.valueOf(refundAmount).floatValue()/100f, "");
			}else if(productType == ProductType.VISA){
				orderThriftClientServiceImpl.sendOperatorMessage(orderNum,true);
				orderLogClientServiceImpl.save(orderNum,operator, refundOrderNum + "退款成功,金额:" + Long.valueOf(refundAmount).floatValue()/100f, "");
			}else if(productType == ProductType.UNVISA){
				orderThriftClientServiceImpl.sendOperatorMessage(orderNum,true);
				orderLogClientServiceImpl.save(orderNum,operator, refundOrderNum + "退款成功,金额:" + Long.valueOf(refundAmount).floatValue()/100f, "");
			}
		}catch(Exception e){
			logger.info("processRefundResult orderLog 记录失败:"+e);
		}
	}

	/**
	 *
	 * @param refundResult
	 * @return 退款处理结果，true表示成功，false表示已处理，exception表示处理异常
	 * @throws Exception
	 */
	@Profiled
	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
	public boolean processRefundResult(RefundResultBean refundResult, OrderPay orderPay, String operator) throws Exception {
		sendOperatorMessageAndLogSave(orderPay.getOrderId(), refundResult.getOrderNum()
				, refundResult.getRefundAmount(), operator, ProductType.valueOf(orderPay.getProductType())) ;
		if(orderPay != null && OrderPayStatus.CANCELLED.name().equals(orderPay.getOrderPayStatus())){
            logger.info("Refund result already processed, orderId: {}", orderPay.getOrderId());
            return false;
        }
		long newRefundedAmount = orderPay.getRefundedAmount() + refundResult.getRefundAmount() ;
		if(newRefundedAmount > orderPay.getPaidAmount()){
			logger.info("Refund amount error, orderId: {}, refunded: {}, refund: {}", orderPay.getOrderId(), orderPay.getRefundedAmount(), refundResult.getRefundAmount());
            return false;
		}else if(newRefundedAmount < orderPay.getPaidAmount()){
			orderPay.setOrderPayStatus(OrderPayStatus.PART_CANCELLED.name());
		}else{
			orderPay.setOrderPayStatus(OrderPayStatus.CANCELLED.name());
		}
		orderPay.setRefundedAmount(newRefundedAmount);
        orderPay.setUpdatedby(operator);
        orderPay.setUpdated(DateTime.now());
        orderPayDao.update(orderPay);

        if(ProductType.valueOf(orderPay.getProductType()) == ProductType.VOUCHER){
        	long couponAmount = orderThriftClientServiceImpl.selectCouponAmountbyId(refundResult.getOrderNum()) ;
        	createAccountHistory4VoucherRefunded(orderPay, refundResult.getOrderNum(), couponAmount) ;
        }else{
        	if(OrderPayStatus.CANCELLED.name().equals(orderPay.getOrderPayStatus())){
        		long useCoupon = orderPay.getUseCoupon();
                if(useCoupon > 0 && orderPay.getCouponItemId() != null){
                	createAccountHistoryRefund(orderPay);
                	couponBo.cancel(orderPay.getCouponItemId());
                }
        	}
        }
		return true;
	}

	public Map<String, String> genRequestParams(OrderPayBean order) {
		Map<String, String> params = new HashMap<>();
		String gateType = GateType.fromPayType(order.getPaymentType()).getGateCode();
        String orderId = order.getOrderId();
        String amount = String.valueOf(order.getPayAmount());
        String fgNotifyUrl = order.getFgNotifyUrl();
        String remoteAddr = "127.0.0.1";
        String comment = "订单号: "  + orderId;
        params.put("clientId", clientId);
        params.put("orderNum", orderId);
        params.put("amount", amount);
        params.put("comment", comment);
        params.put("gateType", gateType);
        if (fgNotifyUrl != null) {
            params.put("fgNotifyUrl", fgNotifyUrl);
        }
        params.put("userIP", remoteAddr);
        String sign = getSignStr(params);
        params.put("sign", sign);
		return params;
	}

	public void recordTradeResult(PaymentResultEntity entity, OrderPay orderPay) throws Exception {
        logger.info("recordTradeResult start: {}", TZBeanUtils.describe(entity));
        String orderId = entity.getOrderId();
        String memberId = orderPay.getMemberId();
        long orderAmount = orderPay.getOrderAmount();
        long payAmount = entity.getTradeAmount() ;
        long useCoupon = orderPay.getUseCoupon();
        String couponItemId = orderPay.getCouponItemId();
        String comment = orderPay.getComment();

        TradeBean thirdTradeBean = new TradeBean();
        thirdTradeBean.setMemberId(memberId);
        thirdTradeBean.setOrderId(orderId);
        thirdTradeBean.setOrderType(entity.getOrderType());
        thirdTradeBean.setTradeType(entity.getTradeType());
        thirdTradeBean.setProductType(StringUtils.isBlank(orderPay.getProductType()) ? null : ProductType.valueOf(orderPay.getProductType()));
        thirdTradeBean.setOrderAmount(orderAmount);
        thirdTradeBean.setTradeAmount(payAmount);
        thirdTradeBean.setPaymentType(entity.getPaymentType());
        thirdTradeBean.setComment(comment);
        thirdTradeBean.setTradeDate(DateTime.now());
        thirdTradeBean.setTraceNum(entity.getThirdpartOrderId());
        thirdTradeBean.setBankPaymentTime(entity.getThirdpartOrderDate());
        thirdTradeBean.setTradeStatus(TradeStatus.SUCCESS);
        tradeService.recordTradeResult(thirdTradeBean);

        if (useCoupon > 0 && StringUtils.isNotBlank(couponItemId)) {
        	if((TradeType.REFUND == entity.getTradeType() && OrderPayStatus.valueOf(orderPay.getOrderPayStatus()) == OrderPayStatus.CANCELLED)
        			|| TradeType.PAYMENT == entity.getTradeType()){
        		TradeBean couponTradeBean = new TradeBean();
                couponTradeBean.setMemberId(memberId);
                couponTradeBean.setOrderId(orderId);
                couponTradeBean.setOrderType(entity.getOrderType());
                couponTradeBean.setTradeType(entity.getTradeType());
                couponTradeBean.setProductType(StringUtils.isBlank(orderPay.getProductType()) ? null : ProductType.valueOf(orderPay.getProductType()));
                couponTradeBean.setOrderAmount(orderAmount);
                couponTradeBean.setTradeAmount(useCoupon);
                couponTradeBean.setPaymentType(PaymentType.Coupon);
                couponTradeBean.setComment(comment);
                couponTradeBean.setTradeDate(DateTime.now());
                couponTradeBean.setTradeStatus(TradeStatus.SUCCESS);
                couponTradeBean.setCouponItemId(couponItemId);
                tradeService.recordTradeResult(couponTradeBean);
        	}
            
        }
        logger.info("recordTradeResult end: {}", TZBeanUtils.describe(entity));
    }

	public void notifyOrder(PaymentResultEntity entity, ProductType productType) throws Exception {
		String orderId = entity.getOrderId();
		OrderPaidBean bean = new OrderPaidBean();
		bean.setOrderId(orderId);
		bean.setPaymentType(entity.getPaymentType());
		bean.setTraceNum(entity.getThirdpartOrderId());
		bean.setBankPaymentTime(entity.getThirdpartOrderDate());
		bean.setTradeAmount(entity.getTradeAmount()) ;
		CommonResponse res = new CommonResponse() ;
		logger.info("notifyOrder start, orderId: {}", orderId);
		switch(productType){
		case VISA:
		case UNVISA:
		case TRAVEL:
			logger.info("ProductType Is "+productType+"...");
			res = orderThriftClientServiceImpl.updateOrderStatus(bean);
			break ;
		case VOUCHER:
			logger.info("ProductType Is VOUCHER...");
			res = orderThriftClientServiceImpl.notifyVoucherOrder4Paid(bean);
			break ;
		case OPCONFIRM:
			logger.info("ProductType Is OPCONFIRM...");
			res = orderThriftClientServiceImpl.updateCommonOrder4Paid(bean);
			break ;
		default:
			break ;
		}
		logger.info("notifyOrder end, response: {}", TZBeanUtils.describe(res));
		if(!res.isSuccess()){
			throw new Exception(res.getErrMsg()) ;
		}
	}

	public void notifyOrder2Cancel(String orderId, ProductType productType, long refundAmount) throws Exception{
		CommonResponse res = new CommonResponse() ;
		logger.info("notifyOrder2Cancel start, orderId: {}", orderId);
		switch(productType){
		case VISA:
		case UNVISA:
		case TRAVEL:
			logger.info("ProductType Is "+productType+"...");
			res = orderThriftClientServiceImpl.updateOrderToCancled(orderId);
			break ;
		case OPCONFIRM:
			logger.info("ProductType Is OPCONFIRM...");
			res.setSuccess(true);
			break ;
		case VOUCHER:
			logger.info("ProductType Is VOUCHER...");
			res = orderThriftClientServiceImpl.notifyVoucherOrder4Refunded(orderId, refundAmount);
			break ;
		default:
			break ;
		}
		logger.info("notifyOrder2Cancel end, response: {}", TZBeanUtils.describe(res));
		if(!res.isSuccess()){
			throw new Exception(res.getErrMsg()) ;
		}
	}

	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
	public void notifyOrderPay(PayResultBean payResult) throws Exception{
		OrderPay orderPay = orderPayDao.selectForUpdateByOrderId(payResult.getOrderNum()) ;
		logger.info("processPaymentResult start, payResult: {}", TZBeanUtils.describe(payResult));
		boolean needNotify = processPaymentResult(payResult, orderPay);
		logger.info("processPaymentResult end, needNotify: {}", needNotify);
		if(needNotify){
			String gateCode = payResult.getGateType();
	        PaymentType paymentType = PaymentType.fromGateType(GateType.fromGateCode(gateCode));
	        ProductType productType = ProductType.valueOf(orderPay.getProductType()) ;
	        OrderType orderType = null ;
	        if(productType == ProductType.OPCONFIRM){
	        	orderType = OrderType.OP_REPAIR_ORDER ;
	        }else{
	        	orderType = OrderType.PAY_ORDER ;
	        }
	        PaymentResultEntity entity = new PaymentResultEntity(payResult.getOrderNum(), paymentType, payResult.getTraceNum()
	        		, payResult.getBankPaymentTime(), orderType, TradeType.PAYMENT);
	        entity.setTradeAmount(payResult.getAmount());
	        recordTradeResult(entity, orderPay);
	        notifyOrder(entity, productType);
		}
	}

	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
	public void notifyOrderRefund(RefundResultBean refundResult) throws Exception{
		OrderPay orderPay = orderPayDao.selectForUpdateByTraceNum(refundResult.getTraceNum()) ;
		logger.info("processRefundResult start, refundResult: {}", TZBeanUtils.describe(refundResult));
		boolean	needNotify = processRefundResult(refundResult,orderPay,"AUTO");
		logger.info("processRefundResult end, needNotify: {}", needNotify);
		if(needNotify){
			String gateCode = refundResult.getGateType();
	        PaymentType paymentType = PaymentType.fromGateType(GateType.fromGateCode(gateCode));
	        ProductType productType = ProductType.valueOf(orderPay.getProductType()) ;
	        OrderType orderType = null ;
	        if(productType == ProductType.OPCONFIRM){
	        	orderType = OrderType.OP_REFUND_ORDER ;
	        }else{
	        	orderType = OrderType.REFUND_ORDER ;
	        }
	        PaymentResultEntity entity = new PaymentResultEntity(refundResult.getOrderNum(), paymentType, refundResult.getTraceNum()
	        		, DateTime.now().toString("yyyy-MM-dd hh:mm:ss"), orderType, TradeType.REFUND);
	        entity.setTradeAmount(refundResult.getRefundAmount());
	        recordTradeResult(entity, orderPay);
	        notifyOrder2Cancel(refundResult.getOrderNum(), productType, refundResult.getRefundAmount()) ;
		}
	}

}
