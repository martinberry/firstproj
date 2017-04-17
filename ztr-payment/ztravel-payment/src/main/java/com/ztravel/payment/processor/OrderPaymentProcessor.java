/**
 *
 */
package com.ztravel.payment.processor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableMap;
import com.google.common.eventbus.EventBus;
import com.travelzen.framework.core.time.DateTimeUtil;
import com.travelzen.framework.core.util.RandomUtil;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.enums.GateType;
import com.ztravel.common.enums.OrderPayStatus;
import com.ztravel.common.enums.OrderType;
import com.ztravel.common.enums.PaymentType;
import com.ztravel.common.enums.ProductType;
import com.ztravel.common.enums.TradeStatus;
import com.ztravel.common.enums.TradeType;
import com.ztravel.common.payment.OrderPayBean;
import com.ztravel.common.payment.OrderPayInfoBean;
import com.ztravel.common.payment.PaymentResponse;
import com.ztravel.common.payment.TradeBean;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.common.util.IdGeneratorUtil;
import com.ztravel.order.client.service.IOrderThriftClientService;
import com.ztravel.order.entity.OrderCancelNeedToDoEntity;
import com.ztravel.paygate.wx.client.entity.WxPayResponse;
import com.ztravel.paygate.wx.client.service.IWxPayService;
import com.ztravel.payment.bo.CouponBo;
import com.ztravel.payment.dao.AccountDao;
import com.ztravel.payment.dao.AccountHistoryDao;
import com.ztravel.payment.dao.CouponAccountDao;
import com.ztravel.payment.dao.CouponItemDao;
import com.ztravel.payment.dao.OrderPayDao;
import com.ztravel.payment.dao.PaymentDao;
import com.ztravel.payment.paygate.model.CommonOrderRefundRequest;
import com.ztravel.payment.paygate.model.RefundRequestBean;
import com.ztravel.payment.paygate.model.RefundResponseBean;
import com.ztravel.payment.paygate.model.VoucherOrderRefundRequest;
import com.ztravel.payment.po.Account;
import com.ztravel.payment.po.CouponAccount;
import com.ztravel.payment.po.OrderPay;
import com.ztravel.payment.po.Payment;
import com.ztravel.payment.po.factory.ModelFactory;
import com.ztravel.payment.service.ThirdPaymentService;
import com.ztravel.payment.service.TradeService;

/**
 * @author zuoning.shen
 *
 */
@Component
public class OrderPaymentProcessor {
    private static Logger logger = LoggerFactory.getLogger(OrderPaymentProcessor.class);
    @Resource
    private AccountDao accountDao;
    @Resource
    private AccountHistoryDao accountHistoryDao;
    @Resource
    private OrderPayDao orderPayDao;
    @Resource
    private PaymentDao paymentDao;
    @Resource
    private CouponItemDao couponItemDao;
    @Resource
    private CouponAccountDao couponAccountDao;
    @Resource
    private CouponBo couponBo;
    @Resource
    private ThirdPaymentService thirdPaymentService;
    @Resource
    private TradeService tradeService;
    @Resource
	private IWxPayService wxPayService;
    @Resource
	private IdGeneratorUtil idGeneratorUtil ;

    @Resource(name="orderThriftClientServiceImpl")
	private IOrderThriftClientService orderThriftClientServiceImpl;

    @Resource(name = "paymentEventBus")
    private EventBus paymentEventBus;

    public String reqPayChecksum(OrderPayBean order) {
        Map<String, String> params = new ImmutableMap.Builder<String, String>().put("memberId", order.getMemberId()).put("orderId", order.getOrderId())
                .put("orderAmount", String.valueOf(order.getOrderAmount())).put("payAmount", String.valueOf(order.getPayAmount()))
                .put("useRewardPoint", String.valueOf(order.getUseRewardPoint())).put("useCoupon", String.valueOf(order.getUseCoupon()))
                .put("couponItemId", order.getCouponItemId()).build();
        return thirdPaymentService.getSignStr(params);
    }

    public boolean verifyPayChecksum(OrderPayBean order, String checksum) {
        Map<String, String> params = new ImmutableMap.Builder<String, String>().put("memberId", order.getMemberId()).put("orderId", order.getOrderId())
                .put("orderAmount", String.valueOf(order.getOrderAmount())).put("payAmount", String.valueOf(order.getPayAmount()))
                .put("useRewardPoint", String.valueOf(order.getUseRewardPoint())).put("useCoupon", String.valueOf(order.getUseCoupon()))
                .put("couponItemId", order.getCouponItemId()).build();
        return thirdPaymentService.verifySignStr(params, checksum);
    }

    @Profiled
    @Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public PaymentResponse placeOrder(OrderPayBean order) throws Exception{
        checkParams(order);
        Account account = accountDao.selectForUpdate(order.getMemberId());
        if(account == null){
            throw new Exception("账户不存在");
        }
        recordOrderPay(order);
        recordPayment(order);
        recordTradeRequest(order);
        PaymentResponse res = new PaymentResponse();
    	String paymentUrl = thirdPaymentService.requestPaymentUrl(order);
        res.setSuccess(true);
        res.setPaymentUrl(paymentUrl);
        return res;
    }

    @Profiled
    @Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public WxPayResponse placeWxOrder(OrderPayBean order) throws Exception{
        checkParams(order);
        Account account = accountDao.selectForUpdate(order.getMemberId());
        if(account == null){
            throw new Exception("账户不存在");
        }
        recordOrderPay(order);
        recordPayment(order);
        recordTradeRequest(order);
    	WxPayResponse response = wxPayService.unifiedOrder(buildRequsetParms(order)) ;
        return response;
    }

    public boolean closeWxOrder(String orderId) throws Exception{
    	return wxPayService.closeOrder(orderId) ;
    }

    private String buildRequsetParms(OrderPayBean order) {
		String requestParams = "{";
		requestParams += "'orderId':'"+order.getOrderId()+"'," ;
		requestParams += "'requestIp':'127.0.0.1'," ;
		requestParams += "'orderAmount':'"+order.getPayAmount()+"'," ;
		requestParams += "'tradeType':'"+order.getTradeType()+"'," ;
		requestParams += "'openId':'"+order.getOpenId()+"'}" ;

		logger.debug("wx unifiedOrder requestParams:{}", requestParams);
		return requestParams;
	}

    private void checkParams(OrderPayBean order) throws Exception{
        String orderId = order.getOrderId();
        String memberId = order.getMemberId();
        PaymentType paymentType = order.getPaymentType();
        long orderAmount = order.getOrderAmount();
        long payAmount = order.getPayAmount();
        long useCoupon = order.getUseCoupon();
        long useRewardPoint = order.getUseRewardPoint();
        String couponItemId = order.getCouponItemId();

        if(StringUtils.isBlank(orderId) || StringUtils.isBlank(memberId)){
            throw new Exception("订单信息错误");
        }
        if(orderAmount< 0 || payAmount < 0 || useCoupon < 0 || useRewardPoint < 0){
            throw new Exception("订单金额计算错误");
        }
        /*if(orderAmount != payAmount + useCoupon + useRewardPoint){
            throw new Exception("订单金额计算错误");
        }*/
        if(useCoupon >0){
            if(StringUtils.isBlank(couponItemId)) throw new Exception("订单信息错误");
            if(couponItemDao.selectById(couponItemId) == null) throw new Exception("代金券不存在");
            CouponAccount couponAccount = couponAccountDao.selectByMemberId(memberId);
            if(couponAccount == null) throw new Exception("代金券账户不存在");
            if(!couponAccount.isActive()) throw new Exception("代金券账户已冻结");
        }
        if(!paymentType.isNetPaymentType()){
            throw new Exception("订单信息错误");
        }
    }

    private void recordOrderPay(OrderPayBean order) throws Exception{
        String orderId = order.getOrderId();
        OrderPay orderPay = orderPayDao.selectByOrderId(orderId);
        if(orderPay == null){
            orderPay = ModelFactory.createOrderPay(idGeneratorUtil.getOrderPayId());
            orderPay.setOrderId(orderId);
            orderPay.setMemberId(order.getMemberId());
            orderPay.setProductType(order.getProductType().toString());
            orderPay.setOrderAmount(order.getOrderAmount());
            orderPay.setPayAmount(order.getPayAmount());
            orderPay.setUseRewardPoint(order.getUseRewardPoint());
            orderPay.setUseCoupon(order.getUseCoupon());
            orderPay.setCouponItemId(order.getCouponItemId());
            orderPay.setMobile(order.isMobile());
            orderPay.setFgNotifyUrl(order.getFgNotifyUrl());
            orderPay.setComment(order.getComment());
            orderPayDao.insert(orderPay);
        }else if(!OrderPayStatus.AWAIT.name().equals(orderPay.getOrderPayStatus())){
            throw new Exception("订单已支付");
        }
    }

    private void recordPayment(OrderPayBean order) throws Exception{
        String orderId = order.getOrderId();
        PaymentType thirdPaymentType = order.getPaymentType();
        Payment thirdPayment = paymentDao.selectPayment(orderId, thirdPaymentType);
        if(thirdPayment == null){
            thirdPayment = ModelFactory.createPayment(idGeneratorUtil.getPaymentId());
            thirdPayment.setOrderId(orderId);
            thirdPayment.setPaymentType(thirdPaymentType.name());
            thirdPayment.setPayAmount(order.getPayAmount());
            paymentDao.insert(thirdPayment);
        }

        long useCoupon = order.getUseCoupon();
        String couponItemId = order.getCouponItemId();
        if(useCoupon > 0 &&  StringUtils.isNotBlank(couponItemId)){
            Payment couponPayment = paymentDao.selectPayment(orderId, PaymentType.Coupon);
            if(couponPayment == null){
                couponPayment = ModelFactory.createPayment(idGeneratorUtil.getPaymentId());
                couponPayment.setOrderId(orderId);
                couponPayment.setCouponItemId(couponItemId);;
                couponPayment.setPaymentType(PaymentType.Coupon.name());
                couponPayment.setPayAmount(order.getUseCoupon());
                paymentDao.insert(couponPayment);
            }
        }
    }

    private void recordTradeRequest(OrderPayBean order) throws Exception {
        String memberId = order.getMemberId();
        String orderId = order.getOrderId();
        long orderAmount = order.getOrderAmount();
        long payAmount = order.getPayAmount();
        long useCoupon = order.getUseCoupon();
        String couponItemId = order.getCouponItemId();

        TradeBean thirdTradeBean = new TradeBean();
        thirdTradeBean.setMemberId(memberId);
        thirdTradeBean.setOrderId(orderId);
        if(ProductType.OPCONFIRM == order.getProductType()){
        	thirdTradeBean.setOrderType(OrderType.OP_REPAIR_ORDER);
        }else{
        	thirdTradeBean.setOrderType(OrderType.PAY_ORDER);
        }
        thirdTradeBean.setTradeType(TradeType.PAYMENT);
        thirdTradeBean.setProductType(order.getProductType());
        thirdTradeBean.setOrderAmount(orderAmount);
        thirdTradeBean.setTradeAmount(payAmount);
        thirdTradeBean.setPaymentType(order.getPaymentType());
        thirdTradeBean.setComment(order.getComment());
        thirdTradeBean.setTradeDate(DateTime.now());
        thirdTradeBean.setTradeStatus(TradeStatus.REQUEST);
        tradeService.recordTradeRequest(thirdTradeBean);

        if (useCoupon > 0 && StringUtils.isNotBlank(couponItemId)) {
            TradeBean couponTradeBean = new TradeBean();
            couponTradeBean.setMemberId(memberId);
            couponTradeBean.setOrderId(orderId);
            couponTradeBean.setOrderType(OrderType.PAY_ORDER);
            couponTradeBean.setTradeType(TradeType.PAYMENT);
            couponTradeBean.setProductType(order.getProductType());
            couponTradeBean.setOrderAmount(orderAmount);
            couponTradeBean.setTradeAmount(useCoupon);
            couponTradeBean.setPaymentType(PaymentType.Coupon);
            couponTradeBean.setComment(order.getComment());
            couponTradeBean.setTradeDate(DateTime.now());
            couponTradeBean.setTradeStatus(TradeStatus.REQUEST);
            couponTradeBean.setCouponItemId(couponItemId);
            tradeService.recordTradeRequest(couponTradeBean);
        }
    }

    public OrderPayInfoBean getOrderPayInfo(String orderId) {
        OrderPayInfoBean orderPayInfo = new OrderPayInfoBean();
        orderPayInfo.setOrderId(orderId);
        OrderPay orderPay = orderPayDao.selectByOrderId(orderId);
        if(orderPay == null){
            return null;
        }
        orderPayInfo.setMobile(orderPay.isMobile());
        orderPayInfo.setMemberId(orderPay.getMemberId());
        orderPayInfo.setOrderAmount(orderPay.getOrderAmount());
        orderPayInfo.setPayAmount(orderPay.getPayAmount());
        orderPayInfo.setUseRewarPoint(orderPay.getUseRewardPoint());
        orderPayInfo.setUseCoupon(orderPay.getUseCoupon());
        orderPayInfo.setCouponItemId(orderPay.getCouponItemId());
        orderPayInfo.setPaidAmount(orderPay.getPaidAmount());
        orderPayInfo.setOrderPayStatus(OrderPayStatus.valueOf(orderPay.getOrderPayStatus()));
        return orderPayInfo;
    }

    @Profiled
    @Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public CommonResponse cancelOrder(String orderId, String operator, String paymentType) throws Exception {
        CommonResponse res = new CommonResponse();
        OrderPay orderPay = orderPayDao.selectByOrderId(orderId);
        if (orderPay == null) {
            throw new Exception("订单不存在");
        }
        OrderPayStatus orderPayStatus = OrderPayStatus.valueOf(orderPay.getOrderPayStatus());
        if (OrderPayStatus.CANCELLED.equals(orderPayStatus)) {
            logger.warn("Order already cancelled, orderId: {}", orderId);
            res.setSuccess(true);
            return res;
        }
        Account account = accountDao.selectForUpdate(orderPay.getMemberId());
        if (account == null) {
            throw new Exception("账户不存在");
        }

        long useCoupon = orderPay.getUseCoupon();
        String couponItemId = orderPay.getCouponItemId();

        if (OrderPayStatus.AWAIT.equals(orderPayStatus)) {
        	if(useCoupon > 0 && StringUtils.isNotBlank(couponItemId)){
        		 couponBo.unFreeze(couponItemId);
        	}
            orderPay.setOrderPayStatus(OrderPayStatus.CANCELLED.name());
            orderPay.setUpdatedby(operator);
            orderPay.setUpdated(DateTime.now());
            orderPayDao.update(orderPay);
            res = orderThriftClientServiceImpl.updateOrderToCancled(orderId);
            if(!res.isSuccess()){
            	throw new Exception("updateOrderToCancled failed... orderId:::{"+orderId+"}") ;
            }
        } else if (OrderPayStatus.PAID == orderPayStatus || OrderPayStatus.PART_CANCELLED == orderPayStatus) {
        	res = orderThriftClientServiceImpl.updateOrderToRefunding(orderId);
        	if(!res.isSuccess()){
            	throw new Exception("updateOrderToRefunding failed... orderId:::{"+orderId+"}") ;
            }
        	OrderCancelNeedToDoEntity entity = orderThriftClientServiceImpl.needToDoBeforeOrderCancel(orderPay.getOrderId()) ;
        	long needRefundPrice = orderPay.getPaidAmount() ;
        	if(entity.isNeedTodo()){
        		if(entity.getAlreadyRefundPrice() != null){
        			needRefundPrice = orderPay.getPaidAmount() - entity.getAlreadyRefundPrice() ;
        		}else if(entity.getNeedRefundTraceNum() != null){
        			OrderPay orderPay2 = orderPayDao.selectByTraceNum(entity.getNeedRefundTraceNum());
        			res = processOrderRefund(orderPay2, orderPay2.getPaidAmount(), entity.getPaymentType(), operator) ;
        			if(!res.isSuccess()){
                    	throw new Exception("processOrderRefund failed... orderId:::{"+orderPay2.getOrderId()+"}") ;
                    }
        		}
        	}
        	res = processOrderRefund(orderPay, needRefundPrice, paymentType, operator) ;
        }
        return res;
    }

    private CommonResponse processOrderRefund(OrderPay orderPay, long refundPrice, String paymentType, String operator) {
    	logger.info("processOrderRefund, request: {},{},{}", TZBeanUtils.describe(orderPay), paymentType, operator);
    	CommonResponse res = new CommonResponse();
    	try{
    		if(paymentType.equals(PaymentType.Alipay.toString())){
        		RefundRequestBean refundBean = buildAliRefundBean(orderPay, refundPrice);
                RefundResponseBean refundResponse = thirdPaymentService.requestRefund(refundBean);
                if(refundResponse.getRetCode().equals(RefundResponseBean.SUCCESS_CODE)){
             		res.setSuccess(true);
             	}else{
             		res.setSuccess(false);
             	}
             	res.setErrMsg(refundResponse.getRetMsg());
     	    } else if (paymentType.equals(PaymentType.WeChatpay.toString())) {
      	    	//生成退款请求（微信退款申请）
     	    	WxPayResponse refundResponse = wxPayService.refundOrder(getRefundRequestParms(orderPay, refundPrice, "admin"));
     	    	if(refundResponse.getResult_code().equals("SUCCESS")){
             		res.setSuccess(true);
             	}else{
             		res.setSuccess(false);
             	}
             	res.setErrMsg(refundResponse.getResult_msg());
      	    } else{
     	    	res.setSuccess(false) ;
     	    	res.setErrMsg("unsupport payment type ::: {"+paymentType+"}");
     	    }
    	}catch(Exception e){
    		res.setSuccess(false) ;
 	    	res.setErrMsg(e.getMessage());
    	}
    	logger.info("processOrderRefund, response: {}", TZBeanUtils.describe(res));
    	return res ;
    }

    @Profiled
    @Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public CommonResponse refundOPConfirmOrder(CommonOrderRefundRequest request) throws Exception {
    	CommonResponse res = new CommonResponse();
    	OrderPay orderPay = orderPayDao.selectByOrderId(request.getOriginOrderNo());
    	if (orderPay == null) {
            throw new Exception("订单不存在");
        }
    	OrderPayStatus orderPayStatus = OrderPayStatus.valueOf(orderPay.getOrderPayStatus());
    	CommonResponse response = orderThriftClientServiceImpl.isOPConfirmOrderRefunded(request.getCommonOrderId()) ;
    	if (response.isSuccess()) {
            logger.warn("OPConfirmOrder already refunded, orderId: {}", request.getCommonOrderId());
            res.setSuccess(true);
            return res;
        }
    	Account account = accountDao.selectForUpdate(orderPay.getMemberId());
        if (account == null) {
            throw new Exception("账户不存在");
        }
        if (OrderPayStatus.PAID.equals(orderPayStatus) || OrderPayStatus.PART_CANCELLED.equals(orderPayStatus)) {
        	response = orderThriftClientServiceImpl.updateOpConfirmOrderToRefunded(request.getCommonOrderId(), orderPay.getTraceNum());
        	if(!response.isSuccess()){
        		logger.error("updateOpConfirmOrderToRefunded, opConfirmOrderId: {}", request.getCommonOrderId());
                res.setSuccess(false);
                res.setErrMsg(response.getErrMsg());
                return res;
        	}
        	if(request.getPaymentType().equals(PaymentType.Alipay.toString())){
        		RefundRequestBean refundBean = buildAliRefundBean(orderPay, request);
             	RefundResponseBean refundResponse = thirdPaymentService.requestRefund(refundBean);
             	if(refundResponse.getRetCode().equals(RefundResponseBean.SUCCESS_CODE)){
             		res.setSuccess(true);
             	}else{
             		res.setSuccess(false);
             	}
             	res.setErrMsg(refundResponse.getRetMsg());
             	return res ;
     	    } else if (request.getPaymentType().equals(PaymentType.WeChatpay.toString())) {
     	    	//生成退款请求（微信退款申请）
     	    	WxPayResponse refundResponse = wxPayService.refundOrder(getRefundRequestParms(orderPay.getOrderId(), request.getCommonOrderId()
     	    			, "admin", request.getRefundAmount(), orderPay.getPaidAmount()));
     	    	if(refundResponse.getResult_code().equals("SUCCESS")){
             		res.setSuccess(true);
             	}else{
             		res.setSuccess(false);
             	}
             	res.setErrMsg(refundResponse.getResult_msg());
             	return res ;
     	    } else{
     	    	res.setSuccess(false) ;
     	    	res.setErrMsg("unsupport payment type ::: {"+request.getPaymentType()+"}");
     	    	return res ;
     	    }
        }else{
            res.setSuccess(false);
            res.setErrMsg("Order status not paid, orderId: {"+request.getOriginOrderId()+"}");
            return res;
        }
    }

    @Profiled
    @Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public CommonResponse cancelOrder(VoucherOrderRefundRequest request) throws Exception {
    	logger.warn("voucher order cancel, request: {}", TZBeanUtils.describe(request));
        CommonResponse res = new CommonResponse();
        OrderPay orderPay = orderPayDao.selectByOrderId(request.getCombineVoucherOrderId());
        if (orderPay == null) {
            throw new Exception("订单不存在");
        }
        OrderPayStatus orderPayStatus = OrderPayStatus.valueOf(orderPay.getOrderPayStatus());
        CommonResponse response = orderThriftClientServiceImpl.isVoucherOrderRefunded(request.getVoucherOrderId()) ;
        if (response.isSuccess()) {
            logger.warn("Order already cancelled, orderId: {}", request.getVoucherOrderId());
            res.setSuccess(true);
            return res;
        }
        Account account = accountDao.selectForUpdate(orderPay.getMemberId());
        if (account == null) {
            throw new Exception("账户不存在");
        }
        if (OrderPayStatus.PAID.equals(orderPayStatus) || OrderPayStatus.PART_CANCELLED.equals(orderPayStatus)) {
        	response = orderThriftClientServiceImpl.updateCombineVoucherOrderToRefunding(request.getVoucherOrderId(), request.getCombineVoucherOrderId());
        	if(!response.isSuccess()){
        		logger.error("updateCombineVoucherOrderToRefunding, voucherOrderId: {}, combineVoucherOrderId: {}", request.getVoucherOrderId(), request.getCombineVoucherOrderId());
                res.setSuccess(false);
                res.setErrMsg(response.getErrMsg());
                return res;
        	}
        	if(request.getPaymentType().equals(PaymentType.Alipay.toString())){
        		RefundRequestBean refundBean = buildAliRefundBean(orderPay, request);
             	RefundResponseBean refundResponse = thirdPaymentService.requestRefund(refundBean);
             	if(refundResponse.getRetCode().equals(RefundResponseBean.SUCCESS_CODE)){
             		res.setSuccess(true);
             	}else{
             		res.setSuccess(false);
             	}
             	res.setErrMsg(refundResponse.getRetMsg());
             	return res ;
     	    } else if (request.getPaymentType().equals(PaymentType.WeChatpay.toString())) {
     	    	//生成退款请求（微信退款申请）
     	    	WxPayResponse refundResponse = wxPayService.refundOrder(getRefundRequestParms(orderPay.getOrderId(), request.getVoucherOrderId()
     	    			, "admin", request.getRefundAmount(), orderPay.getPaidAmount()));
     	    	if(refundResponse.getResult_code().equals("SUCCESS")){
             		res.setSuccess(true);
             	}else{
             		res.setSuccess(false);
             	}
             	res.setErrMsg(refundResponse.getResult_msg());
             	return res ;
     	    } else{
     	    	res.setSuccess(false) ;
     	    	res.setErrMsg("unsupport payment type ::: {"+request.getPaymentType()+"}");
     	    	return res ;
     	    }
        }else{
            res.setSuccess(false);
            res.setErrMsg("Order status not paid, orderId: {"+request.getCombineVoucherOrderId()+"}");
            return res;
        }

    }

    private String getRefundRequestParms( OrderPay orderPay, long refundPrice, String operator) {
		String requestParams = "{";
		requestParams += "'memberId':'" + operator + "'," ;
		requestParams += "'orderId':'" + orderPay.getOrderId() + "'," ;
		requestParams += "'orderAmount':'" + orderPay.getPaidAmount() + "'," ;
		requestParams += "'refundFee':'" + refundPrice + "'}" ;
		return requestParams;
	}

    private String getRefundRequestParms(String orderId, String subOrderId, String operator, long refundFee, long orderAmount) {
		String requestParams = "{";
		requestParams += "'memberId':'" + operator + "'," ;
		requestParams += "'orderId':'" + orderId + "'," ;
		requestParams += "'outRefundNo':'" + subOrderId + "'," ;
		requestParams += "'orderAmount':'" + orderAmount + "'," ;
		requestParams += "'refundFee':'" + refundFee + "'}" ;
		return requestParams;
	}

    private RefundRequestBean buildAliRefundBean(OrderPay orderPay, long refundPrice) throws Exception{
    	logger.info("build alipay refund bean start, orderPay:"+TZBeanUtils.describe(orderPay));
    	String orderId = orderPay.getOrderId();
  	    RefundRequestBean refundBean = new RefundRequestBean();
//        refundBean.setGateType(GateType.AliPay.code);
        refundBean.setGateType(GateType.Alipay.getGateCode());
        refundBean.setOrderNum(orderId);
        refundBean.setRefundAmount(refundPrice);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String refundTime = sdf.format(new Date());
        refundBean.setRefundTime(refundTime);
        refundBean.setRefundNum(DateTimeUtil.datetime14() + RandomUtil.getRandomStr(4));
//        Trade trade = tradeService.getAliTradeByOrderId(orderId);
//        String traceNum = "";
//        if(trade != null){
//        	traceNum = trade.getTraceNum();
//        }
        refundBean.setTraceNum(orderPay.getTraceNum());
        refundBean.setTransAmount(orderPay.getPaidAmount());
        logger.info("build alipay refund bean end, refundBean: "+TZBeanUtils.describe(refundBean));
    	return refundBean;
    }

    private RefundRequestBean buildAliRefundBean(OrderPay orderPay, VoucherOrderRefundRequest request) throws Exception{
    	RefundRequestBean refundBean = buildAliRefundBean(orderPay, request.getRefundAmount()) ;
    	refundBean.setOrderNum(request.getVoucherOrderId());
    	logger.info("build alipay refund bean end, refundBean: "+TZBeanUtils.describe(refundBean));
    	return refundBean;
    }

    private RefundRequestBean buildAliRefundBean(OrderPay orderPay, CommonOrderRefundRequest request) throws Exception{
    	RefundRequestBean refundBean = buildAliRefundBean(orderPay, request.getRefundAmount()) ;
    	refundBean.setOrderNum(request.getCommonOrderId());
    	logger.info("build alipay refund bean end, refundBean: "+TZBeanUtils.describe(refundBean));
    	return refundBean;
    }

	public String queryWxOrderTradeState(String orderId) throws Exception{
		return wxPayService.orderTradeStateQuery(orderId) ;
	}

	public Map<String, Long> queryPaidAmount(String traceNum) {
		Map<String, Long> map = new HashMap<String, Long>(2,1F);
		if(StringUtils.isNotBlank(traceNum)){
			OrderPay orderPay = orderPayDao.selectByTraceNum(traceNum);
			if(orderPay == null)return map;
			map.put("paidAmount", orderPay.getPaidAmount());
			if(orderPay.getRefundedAmount() > 0){
				map.put("refundedAmount", orderPay.getRefundedAmount());
			}
		}
		return map;
	}

}
