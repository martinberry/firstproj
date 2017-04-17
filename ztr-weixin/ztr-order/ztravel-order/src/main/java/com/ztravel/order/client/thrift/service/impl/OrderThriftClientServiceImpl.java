package com.ztravel.order.client.thrift.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.travelzen.framework.core.time.DateTimeUtil;
import com.travelzen.framework.core.util.RandomUtil;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.util.TZBeanUtils;
import com.travelzen.swordfish.thrift.client.annotation.ThriftServiceEndpoint;
import com.travelzen.swordfish.thrift.util.JacksonUtil;
import com.ztravel.common.adapter.sms.MobileCaptchaEntity;
import com.ztravel.common.adapter.sms.SmsAdapter;
import com.ztravel.common.entity.VoucherCombineOrderInfo;
import com.ztravel.common.enums.CommonOrderStatus;
import com.ztravel.common.enums.MessageTitleType;
import com.ztravel.common.enums.NoticeType;
import com.ztravel.common.enums.ProductType;
import com.ztravel.common.enums.VoucherOrderStatus;
import com.ztravel.common.enums.ZtOrderStatus;
import com.ztravel.common.mail.MailEntity;
import com.ztravel.common.mail.MailEnums;
import com.ztravel.common.order.OrderPaidBean;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.common.security.SignUtil;
import com.ztravel.common.util.MailSender;
import com.ztravel.common.util.OperatorMessageContentUtil;
import com.ztravel.common.util.SmsContentUtil;
import com.ztravel.common.util.SystemNoticeContentUtil;
import com.ztravel.common.util.WebEnv;
import com.ztravel.member.client.service.IMemberClientService;
import com.ztravel.member.client.service.IOperatorMessageClientService;
import com.ztravel.member.client.service.ISystemNoticeClientService;
import com.ztravel.order.client.service.ICommonOrderClientService;
import com.ztravel.order.client.service.IOrderClientService;
import com.ztravel.order.client.service.IOrderThriftClientService;
import com.ztravel.order.client.service.IVoucherOrderClientService;
import com.ztravel.order.client.thrift.service.IOrderPayedService;
import com.ztravel.order.dao.ICommonOrderDao;
import com.ztravel.order.dao.IOrderContactorDao;
import com.ztravel.order.dao.IOrderDao;
import com.ztravel.order.dao.IOrderProductDao;
import com.ztravel.order.entity.OrderCancelNeedToDoEntity;
import com.ztravel.order.po.CommonOrder;
import com.ztravel.order.po.Order;
import com.ztravel.order.po.OrderContactor;
import com.ztravel.order.po.OrderProduct;
import com.ztravel.order.po.VoucherOrder;
import com.ztravel.reuse.order.service.ICommonOrderReuseService;
import com.ztravel.reuse.order.service.IOrderLogReuseService;
@Service(value="OrderThriftClientServiceImpl")
@ThriftServiceEndpoint
public class OrderThriftClientServiceImpl implements IOrderThriftClientService {
	private static final Logger LOGGER = RequestIdentityLogger.getLogger(OrderThriftClientServiceImpl.class);

	@Resource
	IOrderDao orderDao;

	@Resource
	IOrderContactorDao orderContactorDao;

	@Resource
	IOrderProductDao orderProductDao;

	@Resource
	IOrderPayedService orderPayedServiceImpl;

	@Resource
	ISystemNoticeClientService systemNoticeClientServiceImpl;

	@Resource
	IOperatorMessageClientService operatorMessageClientServiceImpl;

	@Resource
	IMemberClientService memberClientServiceImpl;

	@Resource
	IOrderLogReuseService orderLogReuseService;

	@Resource
	IOrderClientService orderClientServiceImpl;

	@Resource
	private IVoucherOrderClientService voucherOrderClientService;

	@Resource
	IMemberClientService memberClientService;

	@Resource
	ICommonOrderClientService commonOrderClientService ;

	@Resource
	ICommonOrderDao commonOrderDao;

	@Resource
	ICommonOrderReuseService commonOrderReuseService;

	/**
	 * 将订单更新为已支付状态
	 * */
	@Override
	public CommonResponse updateOrderStatus(OrderPaidBean orderPayedBean){
		LOGGER.info("将订单[{}]更新为已支付的回调流程开始",orderPayedBean.getOrderId());
		CommonResponse response = new CommonResponse();
		Boolean result = false;
		String orderId = null;
		String orderNo = orderPayedBean.getOrderId() ;
		String operator = null;
		try {
			result = orderPayedServiceImpl.updateOrderStatus(orderPayedBean);
			Order order = orderDao.selectByOrderCode(orderNo).get(0);
			orderId = order.getOrderId();
			operator = order.getCreator();
			response.setSuccess(result);
			if(!response.isSuccess()){
				LOGGER.info("将订单[{}]更新为已支付的回调流程失败",orderNo);
				orderLogReuseService.save(orderId, operator, "支付失败", "");
			}else{
				sendPaidMessage(order, ProductType.TRAVEL) ;
				LOGGER.info("将订单[{}]更新为已支付的回调流程成功结束",orderNo);
			}
		} catch (Exception e) {
			LOGGER.error("将订单[{}]更新为已支付的回调流程异常",orderNo,e);
			response.setSuccess(false);
			response.setErrMsg(e.getMessage());
		}
		return response;
	}

	/**
	 * 将通用订单更新为已支付状态
	 * */
	@Override
	public CommonResponse updateCommonOrder4Paid(OrderPaidBean orderPayedBean){
		CommonResponse response = new CommonResponse();
		LOGGER.info("将common订单{}更新为已支付的回调流程开始",orderPayedBean.getOrderId());
		String commonOrderId = orderPayedBean.getOrderId();
		CommonOrder commonOrder = commonOrderReuseService.selectByOrderId(commonOrderId) ;
		if(commonOrder == null){
			response.setSuccess(false);
			response.setErrMsg("can't find commonOrder by commonOrderId:::" + commonOrderId);
			return response ;
		}
		Order order = orderDao.selectById(commonOrder.getOrderIdOrigin()) ;
		if(order == null){
			response.setSuccess(false);
			response.setErrMsg("can't find order by orderId:::" + commonOrder.getOrderIdOrigin());
			return response ;
		}
		String operator = order.getCreator();
		try {
			commonOrderClientService.updateCommonOrder4Paid(commonOrderId, orderPayedBean.getPaymentType(), orderPayedBean.getTraceNum());
			response.setSuccess(true);
			LOGGER.info("将common订单{}更新为已支付的回调流程成功结束",commonOrderId);
			sendPaidMessage(order, ProductType.OPCONFIRM) ;
			try {
				orderLogReuseService.save(order.getOrderId(), operator, "补单" + commonOrderId + "支付成功,金额:" +  Long.valueOf(orderPayedBean.getTradeAmount()).floatValue()/100f, "");
			} catch (Exception e1) {
				LOGGER.error(e1.getMessage(), e1);
			}
		} catch (Exception e) {
			LOGGER.error("将common订单{}更新为已支付的回调流程异常",commonOrderId,e);
			try {
				orderLogReuseService.save(order.getOrderId(), operator, "补单" + commonOrderId + "支付失败", "");
			} catch (Exception e1) {
				LOGGER.error(e1.getMessage(), e1);
			}
			response.setSuccess(false);
			response.setErrMsg(e.getMessage());
		}
		return response;
	}

	/**
	 * 返回通用订单的主订单
	 * */
	@Override
	public String getOrderFromCommonOrder(String commonOrderId){
		CommonResponse response = new CommonResponse();
		CommonOrder commonOrder = commonOrderReuseService.selectByOrderId(commonOrderId) ;
		if(commonOrder == null){
			response.setSuccess(false);
			response.setErrMsg("can't find commonOrder by commonOrderId:::" + commonOrderId);
			return null ;
		}else {
			return commonOrder.getOrderNoOrigin() ;
		}
	}

	private void sendPaidMessage(Order order, ProductType productType) throws Exception{
		String orderId = order.getOrderId() ;
		String orderNo = order.getOrderNo() ;
		LOGGER.info("将订单{}更新为已支付的回调流程开始发送通知消息",orderNo);
		try{
			OrderContactor contactor = orderContactorDao.selectContactorByOrderId(orderId);
			OrderProduct orderProduct = orderProductDao.selectByOrderId(orderId);
			String smsContent = "" ;
			if(productType == ProductType.TRAVEL){
				smsContent = SmsContentUtil.getPayOrderSmsContent(orderProduct.getProductTitle()) ;
				MailEntity mailEntity = buildMailEntity(order, contactor.getEmail(),orderProduct.getBookDate());
				MailSender.send(mailEntity);
				String memberStr = memberClientServiceImpl.getMemberByMid(order.getCreator());
				Map<String,Object> memberMap = JacksonUtil.json2map(memberStr);
				String noticeContent = SystemNoticeContentUtil.getOdContent(2, (String)memberMap.get("nickName"), order.getOrderId(),order.getProductNature());
				systemNoticeClientServiceImpl.add((String)memberMap.get("id"), NoticeType.ORDERTYPE, noticeContent);
				operatorMessageClientServiceImpl.add(MessageTitleType.PAYSUCCESS, order.getCreator(), orderProduct.getProductTitle(), OperatorMessageContentUtil.getOrderUrl(order.getOrderId(),order.getProductNature()));
				orderClientServiceImpl.sendWeiXinPayMsg(orderId);
			}else if(productType == ProductType.OPCONFIRM){
				smsContent = SmsContentUtil.getPayOpConfirmOrderSmsContent(orderProduct.getProductTitle()) ;
			}
			MobileCaptchaEntity mobileCapEntity = buildSmsEntity(contactor.getPhone(), smsContent);
			SmsAdapter.sendMessage(mobileCapEntity);//发送通知短信
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
		LOGGER.info("将订单{}更新为已支付的回调流程发送通知消息操作结束",orderNo);
	}

	@Override
	public CommonResponse notifyVoucherOrder4Paid(OrderPaidBean orderPaidBean){
		LOGGER.info("将订单[{}]更新为已支付的回调流程开始",orderPaidBean.getOrderId());
		CommonResponse response = new CommonResponse();
		try {
			voucherOrderClientService.updateVoucherOrder4Paid(orderPaidBean) ;
			this.sendPayVoucherOrderSuccessMessage(orderPaidBean.getOrderId()) ;
			response.setSuccess(true);
		} catch (Exception e) {
			response.setErrMsg(e.getMessage());
			response.setSuccess(false);
		}
		LOGGER.info("将订单[{}]更新为已支付的回调流程结束:::response{}", orderPaidBean.getOrderId(), TZBeanUtils.describe(response));
		return response ;
	}

	@Override
	public CommonResponse notifyVoucherOrder4Refunded(String orderId, long refundAmount){
		LOGGER.info("将订单[{}]更新为已退款的回调流程开始",orderId);
		CommonResponse response = new CommonResponse();
		try {
			voucherOrderClientService.updateVoucherOrder4Refunded(orderId, refundAmount) ;
			response.setSuccess(true);
		} catch (Exception e) {
			response.setErrMsg(e.getMessage());
			response.setSuccess(false);
		}
		LOGGER.info("将订单[{}]更新为已退款的回调流程结束:::response{}", orderId, TZBeanUtils.describe(response));
		return response ;
	}

	/**
	 * 取消订单时,将订单更新为退款中状态
	 * */
	@Override
	public CommonResponse updateOrderToRefunding(String orderCode){
		LOGGER.info("将订单[{}]更新为退款中的回调流程开始",orderCode);
		CommonResponse response = new CommonResponse(true);
		boolean result = false;
		try {
			Order order = orderDao.selectByOrderCode(orderCode).get(0);
			result = orderPayedServiceImpl.updateOrderToRefundStatus(orderCode);
			response.setSuccess(result);
			if(!response.isSuccess()){
				LOGGER.info("将订单[{}]更新为退款中的回调流程失败",orderCode);
				orderLogReuseService.save(order.getOrderId(), "AUTO", "退款中", "失败");
			}else{
				LOGGER.info("将订单[{}]更新为退款中的回调流程成功结束",orderCode);
			}
		} catch (Exception e) {
			LOGGER.error("将订单[{}]更新为退款中的回调流程异常",orderCode,e);
			response.setSuccess(false);
			response.setErrMsg(e.getMessage());
		}
		return response;
	}

	/**
	 * @throws Exception
	 * */
	@Override
	public OrderCancelNeedToDoEntity needToDoBeforeOrderCancel(String orderNo) throws Exception{
		LOGGER.info("needToDoBeforeOrderCancel:::{}", orderNo);
		OrderCancelNeedToDoEntity entity = commonOrderClientService.orderCancelNeedToDo(orderNo) ;
		LOGGER.info("needToDoBeforeOrderCancel,response:::{}", TZBeanUtils.describe(entity));
		return entity ;
	}

	@Override
	public CommonResponse updateOpConfirmOrderToRefunded(String opConfirmOrderId, String paySerialNum){
		LOGGER.info("将common订单[{}]更新为已退款的回调流程开始",opConfirmOrderId);
		CommonResponse response = new CommonResponse();
		try {
			commonOrderClientService.updateCommonOrder4Refunded(opConfirmOrderId, paySerialNum);
			response.setSuccess(true);
			LOGGER.info("将common订单[{}]更新为已退款的回调流程成功结束",opConfirmOrderId);
		} catch (Exception e) {
			LOGGER.error("将common订单[{}]更新为已退款的回调流程异常",opConfirmOrderId,e);
			response.setSuccess(false);
			response.setErrMsg(e.getMessage());
		}
		return response;
	}

	@Override
	public  CommonResponse updateCombineVoucherOrderToRefunding(String voucherOrderId, String combineVoucherOrderId){
		LOGGER.info("将订单[{}]更新为退款中的回调流程开始",combineVoucherOrderId);
		CommonResponse response = new CommonResponse();
		Boolean result = false;
		try {
			result = voucherOrderClientService.updateCombineVoucherOrder4Refunding(combineVoucherOrderId, voucherOrderId);
			response.setSuccess(result);
			if(!response.isSuccess()){
				LOGGER.info("将订单[{}]更新为退款中的回调流程失败",combineVoucherOrderId);
			}else{
				LOGGER.info("将订单[{}]更新为退款中的回调流程成功结束",combineVoucherOrderId);
			}
		} catch (Exception e) {
			LOGGER.error("将订单[{}]更新为退款中的回调流程异常",combineVoucherOrderId,e);
			response.setSuccess(false);
			response.setErrMsg(e.getMessage());
		}
		return response;
	}

	@Override
	public CommonResponse isVoucherOrderRefunded(String voucherOrderId){
		LOGGER.info("isVoucherOrderRefunded voucherOrderId:::{}",voucherOrderId);
		CommonResponse response = new CommonResponse();
		boolean result = false;
		try {
			result = voucherOrderClientService.isVoucherOrderRefunded(voucherOrderId);
			response.setSuccess(result);
		} catch (Exception e) {
			LOGGER.error("isVoucherOrderRefunded failed",e);
			response.setSuccess(false);
			response.setErrMsg(e.getMessage());
		}
		LOGGER.info("isVoucherOrderRefunded response:::{}",TZBeanUtils.describe(result));
		return response;
	}

	@Override
	public CommonResponse isOPConfirmOrderRefunded(String orderId){
		LOGGER.info("isOPConfirmOrderRefunded commonOrderId:::{}",orderId);
		CommonResponse response = new CommonResponse();
		boolean result = false;
		try {
			CommonOrder order = commonOrderReuseService.selectByOrderId(orderId);
			result = CommonOrderStatus.REFUNDED == order.getStatus() ;
			response.setSuccess(result);
		} catch (Exception e) {
			LOGGER.error("isOPConfirmOrderRefunded failed",e);
			response.setSuccess(false);
			response.setErrMsg(e.getMessage());
		}
		LOGGER.info("isOPConfirmOrderRefunded response:::{}",TZBeanUtils.describe(result));
		return response;
	}

	@Override
	public List<VoucherOrder> selectVoucherOrderbyCBID(String combineOrderId){
		List<VoucherOrder> voucherOrders = null ;
		try {
			voucherOrders = voucherOrderClientService.selectByCombineOrderId(combineOrderId) ;
		} catch (Exception e) {
			LOGGER.error("selectVoucherOrderbyCBID failed:::{}", combineOrderId, e);
		}
		if(voucherOrders == null){
			voucherOrders = new ArrayList<VoucherOrder>() ;
		}
		return voucherOrders ;
	}

	@Override
	public long selectCouponAmountbyId(String voucherOrderId){
		long amount = 0l ;
		try {
			amount = voucherOrderClientService.getCouponAmountByVoucherOrderId(voucherOrderId) ;
		} catch (Exception e) {
			LOGGER.error("selectCouponAmountbyId failed:::{}", voucherOrderId, e);
		}
		return amount ;
	}

	/**
	 * 第三方支付完成退款时,回调将订单改完已取消状态,订单取消流程结束
	 * */
	@Override
	public  CommonResponse updateOrderToCancled(String orderCode){
		LOGGER.info("将订单[{}]更新为已取消的回调流程开始",orderCode);
		CommonResponse response = new CommonResponse();
		Boolean result = false;
		try {
			result = orderPayedServiceImpl.updateOrderToCancleStatus(orderCode);
			response.setSuccess(result);
		} catch (Exception e) {
			LOGGER.error("将订单[{}]更新为已取消的回调流程异常",orderCode,e);
			response.setSuccess(false);
			response.setErrMsg(e.getMessage());
		}
		return response;
	}

	public MobileCaptchaEntity buildSmsEntity(String mobilPhone,String smsContent){
		MobileCaptchaEntity mobileCapEntity = new MobileCaptchaEntity();
		mobileCapEntity.setMobileNum(mobilPhone);
		mobileCapEntity.setContent(smsContent);
		return mobileCapEntity;
	}

	public MailEntity buildMailEntity(Order order,String email,Date bookDate){
		Map<String, String> params = new HashMap<String, String>() ;
		OrderProduct orderProduct = orderProductDao.selectByOrderId(order.getOrderId());
		String url = WebEnv.get("server.path.media", "")+"imageservice?mediaImageId=" + orderProduct.getImageId();
		DateTime bookDateTime = DateTimeUtil.getDate(bookDate.getTime());
		String detailUrl = WebEnv.get("server.path.memberServer") + "/order/front/detail/" + order.getOrderId();
		params.put("DATE", DateTime.now().toString("yyyy-MM-dd"));
		params.put("URL", url);
		params.put("DETAILURL",detailUrl);
		params.put("ORDERNO", order.getOrderNo());
		params.put("PRODUCTNAME", orderProduct.getProductTitle());
		params.put("CREATETIME",DateTimeUtil.convertDateToString("yyyy-MM-dd", order.getCreateDate()));
		params.put("OFFTIME", bookDateTime.toString("yyyy-MM-dd"));
		params.put("TOTALPRICE", String.valueOf(order.getTotalPrice()/100));
		params.put("PAYMENT", String.valueOf(order.getPayAmount()/100));
		Long couponSub = order.getCouponSub() == null ? 0l : order.getCouponSub();
		params.put("DISCOUNT", String.valueOf(couponSub/100));
		params.put("HOMEPAGE", WebEnv.get("server.path.memberServer", "")) ;
		params.put("MEMBERINFO", WebEnv.get("server.path.memberServer", "") + "/member/info") ;
		MailEntity entity = new MailEntity(email,null,MailEnums.ContentType.PAYEDHTML,params,MailEnums.BizType.PAYED);
		return entity;
	}



	/**
	 * 判断订单是否未支付
	 * @return true:未支付;false:已支付
	 * */
	@Override
	   public CommonResponse weatherOrderPayedByOrderCode(String orderCode) {
	          CommonResponse response = new CommonResponse();
	          LOGGER.info("开始查询订单[{}]是否已支付",orderCode);
	          if(StringUtils.isNotBlank(orderCode)){
                 try {
                        List<Order> orderList = orderDao.selectByOrderCode(orderCode);
                        if(CollectionUtils.isNotEmpty(orderList)){
                        	Order order = orderList.get(0);
                        	if(null != order){
	                               response.setSuccess(order.getBackState().equals(ZtOrderStatus.UNPAY.name()));
	                               response.setErrMsg("");
	                        }else{
	                               response.setErrMsg("订单{"+orderCode+"}不存在");
	                               response.setSuccess(false);
	                        }
                        }else{
                        	response.setErrMsg("订单{"+orderCode+"}不存在");
       	                 	response.setSuccess(false);
                        }
                 } catch (Exception e) {
                        LOGGER.error("获取订单[{}]是否已支付状态信息异常[{}]",orderCode,e);
                        response.setErrMsg("查询订单异常:"+e);
                        response.setSuccess(false);
                        LOGGER.info("查询订单[{}]是否已支付结束",orderCode);
                        LOGGER.info(TZBeanUtils.describe(response));
                        return response;
                 }
	          }else{
	                 response.setErrMsg("订单编号为空");
	                 response.setSuccess(false);
	          }
	          LOGGER.info("查询订单[{}]是否已支付结束",orderCode);
	          LOGGER.info(TZBeanUtils.describe(response));
	          return response;
	   }


	/**
	 * 根据订单ID查询订单是否已取消
	 *@param orderCode:表示订单编号.
	 *@return {@code CommonResponse}
	 *response:
	 *errMsg为空,且success为true时,表示订单已取消;
	 *errMsg为空,且success为false时,表示订单未取消;
	 *errMsg不为空,表示请求查询出现异常,此时success都为false
	 * */
	@Override
	public CommonResponse weatherOrderCancled(String orderCode) {
		CommonResponse response = new CommonResponse();
		LOGGER.info("开始查询订单[{}]是否已取消",orderCode);
		if(StringUtils.isNotBlank(orderCode)){
			try {
				ProductType productType = orderPayedServiceImpl.orderProductType(orderCode) ;
				LOGGER.info("product type:::{}",productType);
				switch(productType){
				case TRAVEL:
					Order order  = orderDao.selectByOrderCode(orderCode).get(0);
					if(null != order){
						response.setSuccess(order.getBackState().equals(ZtOrderStatus.CANCELED.name()));
						response.setErrMsg("");
					}else{
						response.setErrMsg("订单{"+orderCode+"}不存在");
						response.setSuccess(false);
					}
					break ;
				case VOUCHER:
					VoucherOrder voucherOrder = voucherOrderClientService.selectById(orderCode);
					response.setSuccess(voucherOrder.getStatus() == VoucherOrderStatus.CANCELED) ;
					response.setErrMsg("");
					break;
				default:
					break;
				}
			} catch (Exception e) {
				LOGGER.error("获取订单[{}]是否取消状态信息异常[{}]",orderCode,e);
				response.setErrMsg("查询订单异常:"+e);
				response.setSuccess(false);
				LOGGER.info("查询订单[{}]是否已取消结束",orderCode);
				LOGGER.info(TZBeanUtils.describe(response));
				return response;
			}
		}else{
			response.setErrMsg("订单ID为空");
			response.setSuccess(false);
		}
		LOGGER.info("查询订单[{}]是否已取消结束",orderCode);
		LOGGER.info(TZBeanUtils.describe(response));
		return response;
	}


	@Override
	public CommonResponse updateOrderToRefundFailed(String orderCode) {
		LOGGER.info("将订单[{}]更新为退款失败的回调流程开始",orderCode);
		CommonResponse response = new CommonResponse();
		Boolean result = false;
		try {
			ProductType productType = orderPayedServiceImpl.orderProductType(orderCode) ;
			LOGGER.info("product type:::{}",productType);
			switch(productType){
			case TRAVEL:
				result = orderPayedServiceImpl.updateOrderToRefundFailedStatus(orderCode);
				break ;
			case VOUCHER:
				result = voucherOrderClientService.updateVoucherOrder4RefundFail(orderCode);
			default:
				break;
			}
			response.setSuccess(result);
			if(!response.isSuccess()){
				LOGGER.info("将订单[{}]更新为退款失败的回调流程失败",orderCode);
			}else{
				sendOperatorMessage(orderCode,false);
				LOGGER.info("将订单[{}]更新为退款失败的回调流程成功结束",orderCode);
			}
		} catch (Exception e) {
			LOGGER.error("将订单[{}]更新为退款失败的回调流程异常",orderCode,e);
			response.setSuccess(false);
			response.setErrMsg(e.getMessage());
		}
		return response;
	}

	@Override
	public void sendOperatorMessage(String orderCode,Boolean isSuccess) {
		try {
			LOGGER.info("订单{}退款成功发送后台消息回调开始",orderCode);
			Order order = orderDao.selectByOrderCode(orderCode).get(0);
			OrderProduct orderProduct = orderProductDao.selectByOrderId(order.getOrderId());
			if(isSuccess){
				operatorMessageClientServiceImpl.add(MessageTitleType.REFUNDED, order.getCreator(), orderProduct.getProductTitle(), OperatorMessageContentUtil.getOrderUrl(order.getOrderId(),order.getProductNature()));
			}else{
				operatorMessageClientServiceImpl.add(MessageTitleType.REFUNDFAILED, order.getCreator(), orderProduct.getProductTitle(), OperatorMessageContentUtil.getOrderUrl(order.getOrderId(),order.getProductNature()));
			}
			LOGGER.info("订单{}退款成功发送后台消息回调结束",orderCode);
		} catch (SQLException e) {
			LOGGER.error("订单{}退款成功后发送后台消息回调错误",orderCode,e);
		}
	}

	@Override
	public void sendPayVoucherOrderSuccessMessage(String combineOrderId) {
	    LOGGER.info("代金券订单[{}]支付成功发送消息通知", combineOrderId);
	    try {
	        boolean flag = false;
            VoucherCombineOrderInfo combineOrderInfo = voucherOrderClientService.selectCombineOrderInfoByCombineOrderId(combineOrderId);
            if(combineOrderInfo == null){
                return ;
            }
            String mobile = memberClientService.getMobileById(combineOrderInfo.getCreatedBy());
            if (mobile == null && combineOrderInfo.getMobile() != null) {
                String memberInfo = memberClientService.selectMemberByMobile(combineOrderInfo.getMobile());
                if (memberInfo == null) {//手机号未被注册过，即注册给当前微信用户
                    flag = true;
                }
            }
            if (flag) {
                String password = "zlx" + RandomUtil.getRandomStr(8);
                String passwordSign = SignUtil.signPassword(password , SignUtil.FRONT_SIGN_KEY) ;
                memberClientService.bindMobileToMember(combineOrderInfo.getCreatedBy(), combineOrderInfo.getMobile(), passwordSign);
                MobileCaptchaEntity mobileCapEntity = buildSmsEntity(combineOrderInfo.getMobile(), SmsContentUtil.getPayVoucherOrderMobileSmsContent(combineOrderInfo.getMobile(), password));
                SmsAdapter.sendMessage(mobileCapEntity);//发送通知短信
            } else {
                MobileCaptchaEntity mobileCapEntity = buildSmsEntity(mobile != null ? mobile : combineOrderInfo.getMobile(), SmsContentUtil.getPayVoucherOrderSmsContent());
                SmsAdapter.sendMessage(mobileCapEntity);//发送通知短信
            }
            systemNoticeClientServiceImpl.add(combineOrderInfo.getCreatedBy(), NoticeType.COUPONTYPE, SystemNoticeContentUtil.getVoucherContent(combineOrderInfo.getCouponName()));
        } catch (Exception e) {
            LOGGER.error("代金券订单[{}]支付成功发送消息通知错误",combineOrderId,e);
        }
	}

}
