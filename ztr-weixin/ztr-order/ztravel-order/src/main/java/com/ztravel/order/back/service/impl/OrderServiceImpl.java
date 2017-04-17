package com.ztravel.order.back.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.travelzen.framework.core.time.DateTimeUtil;
import com.travelzen.framework.core.util.MoneyUtil;
import com.travelzen.framework.logger.core.TZMarkers;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.redis.client.RedisClient;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.adapter.sms.MobileCaptchaEntity;
import com.ztravel.common.adapter.sms.SmsAdapter;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.OrderConstans;
import com.ztravel.common.entity.AdditionalProduct;
import com.ztravel.common.entity.Attachment;
import com.ztravel.common.entity.PassengerInfo;
import com.ztravel.common.entity.PayDetailInfo;
import com.ztravel.common.enums.AdditionalRule;
import com.ztravel.common.enums.CommonOrderStatus;
import com.ztravel.common.enums.CommonOrderType;
import com.ztravel.common.enums.CredentialType;
import com.ztravel.common.enums.Gender;
import com.ztravel.common.enums.MessageTitleType;
import com.ztravel.common.enums.Nature;
import com.ztravel.common.enums.NoticeType;
import com.ztravel.common.enums.OrderOperate;
import com.ztravel.common.enums.PassengerType;
import com.ztravel.common.enums.ZtOrderStatus;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.common.mail.MailEntity;
import com.ztravel.common.payment.CouponItemBean;
import com.ztravel.common.payment.PaymentTestArgs;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.common.util.MailSender;
import com.ztravel.common.util.OperatorMessageContentUtil;
import com.ztravel.common.util.PriceUtil;
import com.ztravel.common.util.SmsContentUtil;
import com.ztravel.common.util.SystemNoticeContentUtil;
import com.ztravel.common.util.WebEnv;
import com.ztravel.common.weixin.notice.GiftBoxSendNotice;
import com.ztravel.common.weixin.notice.OpConfirmNotice;
import com.ztravel.common.weixin.notice.OuttingNotice;
import com.ztravel.mediaserver.client.MediaClientUtil;
import com.ztravel.mediaserver.client.MediaClientUtil.MediaType;
import com.ztravel.member.client.service.IMemberClientService;
import com.ztravel.member.client.service.IOperatorMessageClientService;
import com.ztravel.member.client.service.ISystemNoticeClientService;
import com.ztravel.order.back.convertor.OrderConvertor;
import com.ztravel.order.back.criteria.OrderSearchCriteria;
import com.ztravel.order.back.service.ICommonOrderService;
import com.ztravel.order.back.service.IOrderService;
import com.ztravel.order.back.vo.AbstractPdfView4Itext5;
import com.ztravel.order.back.vo.ContactorVO;
import com.ztravel.order.back.vo.FeesDetailVO;
import com.ztravel.order.back.vo.OpConfirmPDFEntity;
import com.ztravel.order.back.vo.OpConfirmPdfView;
import com.ztravel.order.back.vo.OrderDetailVO;
import com.ztravel.order.back.vo.OrderListVO;
import com.ztravel.order.back.vo.OrderLogVO;
import com.ztravel.order.back.vo.OrderProductVO;
import com.ztravel.order.back.vo.TravellerVO;
import com.ztravel.order.back.vo.VisaOrderConfirmPDFEntity;
import com.ztravel.order.back.vo.VisaOrderConfirmPdfView;
import com.ztravel.order.dao.ICommonOrderDao;
import com.ztravel.order.dao.IOrderContactorDao;
import com.ztravel.order.dao.IOrderDao;
import com.ztravel.order.dao.IOrderLogDao;
import com.ztravel.order.dao.IOrderOpenIdDao;
import com.ztravel.order.dao.IOrderOrderProductDao;
import com.ztravel.order.dao.IOrderPassengerDao;
import com.ztravel.order.dao.IOrderProductDao;
import com.ztravel.order.po.CommonOrder;
import com.ztravel.order.po.Order;
import com.ztravel.order.po.OrderContactor;
import com.ztravel.order.po.OrderContactorPoMaterial;
import com.ztravel.order.po.OrderLog;
import com.ztravel.order.po.OrderOpenId;
import com.ztravel.order.po.OrderPO;
import com.ztravel.order.po.OrderPassenger;
import com.ztravel.order.po.OrderProduct;
import com.ztravel.payment.paygate.model.CommonOrderRefundRequest;
import com.ztravel.payment.service.ICouponService;
import com.ztravel.payment.service.IOrderPaymentService;
import com.ztravel.product.client.service.IProductClientService;
import com.ztravel.reuse.order.converter.Order2DetailConverter;
import com.ztravel.reuse.order.converter.OrderReuseConverter;
import com.ztravel.reuse.order.entity.AdditionalProductWo;
import com.ztravel.reuse.order.entity.AttachmentWo;
import com.ztravel.reuse.order.entity.OrderDetailWo;
import com.ztravel.reuse.order.entity.OrderProductWo;
import com.ztravel.reuse.order.entity.OrderWo;
import com.ztravel.reuse.order.service.ICommonOrderReuseService;
import com.ztravel.reuse.order.service.IOrderContactorReuseService;
import com.ztravel.reuse.order.service.IOrderLogReuseService;
import com.ztravel.reuse.order.service.IOrderReuseService;
import com.ztravel.reuse.product.entity.ProductBookVo;
import com.ztravel.reuse.product.entity.ProductFlightInfo;

@Service
public class OrderServiceImpl implements IOrderService {

	private static Logger LOGGER  = RequestIdentityLogger.getLogger(OrderServiceImpl.class);

	private final RedisClient redisClient = RedisClient.getInstance();

	@Resource
	private IOrderDao orderDao;

	@Resource
	private IOrderPassengerDao orderPassengerDao;

	@Resource
	private IOrderOrderProductDao orderOrderProductDao;

	@Resource
	IOrderContactorDao orderContactorDao;

	@Resource
	IOrderProductDao orderProductDao;

	@Resource
	private IOrderLogDao orderLogDao;

	@Resource
	private ICommonOrderDao commonOrderDaoImpl;

	@Resource
	private  IProductClientService productClientServiceImpl;

	@Resource
	private IOrderLogReuseService orderLogReuseService;

	@Resource
	IOrderPaymentService orderPaymentService;

	@Resource
	ICouponService couponService;

	@Resource
	IOrderOpenIdDao orderOpenIdDaoImpl;

	@Resource
	ISystemNoticeClientService systemNoticeClientServiceImpl;

	@Resource
	IOperatorMessageClientService operatorMessageClientServiceImpl;

	@Resource
	IMemberClientService memberClientServiceImpl;

	@Resource
	ICommonOrderService commonOrderService ;

	@Resource
	private ICommonOrderReuseService commonOrderReuseService;
	
	@Resource
	private IOrderContactorReuseService orderContactorReuseService;
	
	@Resource
	private IOrderReuseService orderReuseService;

	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor=RuntimeException.class)
	public AjaxResponse refundOpConfirmOrder(String orderId) throws Exception {
		AjaxResponse ajaxResponse = AjaxResponse.instance(OrderConstans.ORDER_CANCLE_FAILED,"");
		CommonOrderRefundRequest request = new CommonOrderRefundRequest() ;
		CommonOrder order = commonOrderReuseService.selectByOrderId(orderId) ;
		LOGGER.info("refundOpConfirmOrder request start:{}", TZBeanUtils.describe(order));
		request.setCommonOrderId(orderId);
		request.setOperator(OperatorSidHolder.get());
		request.setOriginOrderId(order.getOrderIdOrigin());
		request.setOriginOrderNo(order.getOrderNoOrigin());
		request.setPaymentType(order.getPayType().toString());
		request.setRefundAmount(order.getPrice());
		if(PaymentTestArgs.payAmount !=0l){
			request.setRefundAmount(1l);
		}
		if(order != null && order.getStatus() == CommonOrderStatus.REFUNDING){
			CommonResponse response = orderPaymentService.refundOPConfirmOrder(request) ;
			if(response == null || (response != null && !response.isSuccess())){
				LOGGER.error("调用orderPaymentService thrift 服务refundOPConfirmOrder申请退款失败,request:{}",TZBeanUtils.describe(request));
				throw new RuntimeException("调用orderPaymentService thrift 服务refundOPConfirmOrder申请退款失败");
			}
			//发送后台消息
			LOGGER.debug("业务员{} refundOpConfirmOrder 操作成功,,request:{}",OperatorSidHolder.get(),TZBeanUtils.describe(request));
			ajaxResponse.setRes_code(OrderConstans.ORDER_CANCLE_SUCCESS);
		}else{
			LOGGER.info("refundOpConfirmOrder失败,订单:{}",TZBeanUtils.describe(order));
		}
		return ajaxResponse;
	}

	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor=RuntimeException.class)
	public AjaxResponse cancleOrder(String orderId) throws Exception {
		AjaxResponse ajaxResponse = AjaxResponse.instance(OrderConstans.ORDER_CANCLE_FAILED,"");
		Order order = orderDao.selectById(orderId);
		OrderProduct orderProduct = orderProductDao.selectByOrderId(orderId);
		if(null != order && !order.getBackState().equals(ZtOrderStatus.CANCELED.getCode())){
			orderLogReuseService.save(orderId, redisClient.get(OperatorSidHolder.get()), "取消", "");
			if(order.getIsToPay()){//跳转支付后,取消订单时才调用thrift,否则找不到订单
				CommonResponse response = orderPaymentService.cancelOrder(order.getOrderNo(), redisClient.get(OperatorSidHolder.get()) ,order.getPayType());
				if(response == null || (response != null && !response.isSuccess())){
					LOGGER.debug("调用orderPaymentService thrift 服务取消订单[{}]失败",orderId);
					throw new RuntimeException("调用orderPaymentService thrift 服务失败");
				}
			}else{
				order.setBackState(ZtOrderStatus.CANCELED.getCode());
				order.setFrontState(ZtOrderStatus.CANCELED.getCode());
				order.setOperator(redisClient.get(OperatorSidHolder.get()));
				order.setStateChangeHistory(order.getStateChangeHistory()+"-->"+ZtOrderStatus.CANCELED.getCode());
				Boolean updateResult = orderDao.updateOrder(order);
				if(updateResult){
					if(StringUtils.isNotBlank(order.getDiscountCouponId())){
                        CommonResponse response = couponService.unfreeze(order.getDiscountCouponId());
                        if(response == null || (response != null && !response.isSuccess())){
                            LOGGER.debug("取消订单时调用couponService thrift 服务解冻代金券[{}]失败",order.getDiscountCouponId());
                            throw new RuntimeException("调用couponService thrift 服务解冻代金券失败 ");
                        }
                    }
					//自由行订单释放库存
					if(StringUtils.isEmpty(order.getProductNature())|| (order.getProductNature().equals(Nature.PACKAGE.toString()) || order.getProductNature().equals(Nature.COMBINATION.toString()))){
						LOGGER.info("后台运营人员取消订单:[],订单产品类型：[],开始释放订单库存.",orderId,order.getProductNature());
						ProductBookVo productBook = JSON.parseObject(orderProduct.getProductSnapshot(), ProductBookVo.class);
						Integer usedStock = productBook.getAdultNum() + productBook.getChildNum();
						productClientServiceImpl.updateAndModifyStock(productBook.getProductId(), productBook.getDepartDay(), -usedStock);//释放库存
						LOGGER.info("后台运营人员取消订单:[],订单产品类型：[],完成订单库存释放.",orderId,order.getProductNature());
					}
					//发送后台消息
					operatorMessageClientServiceImpl.add(MessageTitleType.CANCLE, order.getCreator(), orderProduct.getProductTitle(), OperatorMessageContentUtil.getOrderUrl(order.getOrderId(),order.getProductNature()));
				}
			}
			//发送后台消息
			LOGGER.debug("业务员{}取消订单{}操作成功",OperatorSidHolder.get(),orderId);
			ajaxResponse.setRes_code(OrderConstans.ORDER_CANCLE_SUCCESS);
		}else{
			LOGGER.info("取消订单操作失败,订单[{}]已取消",orderId);
		}
		return ajaxResponse;
	}

	@Override
	public AjaxResponse sendGiftBox(String orderId, String sendContent)throws Exception {
		AjaxResponse ajaxResponse = AjaxResponse.instance(OrderConstans.ORDER_SEND_GIFT_FAILED,"");
		Order order = orderDao.selectById(orderId);
		if(null != order && order.getBackState().equals(ZtOrderStatus.OPCONFIRM.getCode())){
			order.setBackState(ZtOrderStatus.GIFTSEND.getCode());
			if(StringUtils.isNotBlank(order.getOperateRecord())){
				JSONObject operateRecord = (JSONObject) JSONObject.parse(order.getOperateRecord());
				operateRecord.put(OrderOperate.GIFTBOX.getCode(), DateTimeUtil.date10());
				order.setOperateRecord(operateRecord.toString());
			}
			order.setStateChangeHistory(order.getStateChangeHistory()+"-->"+ZtOrderStatus.GIFTSEND.getCode());
			order.setOperator(redisClient.get(OperatorSidHolder.get()));
			Boolean updateResult = orderDao.updateOrder(order);
			OrderOpenId orderOpenId = orderOpenIdDaoImpl.getOpenIdByOrderId(orderId);
			/**微信订单需推送消息*/
			try {
				if(null != orderOpenId && StringUtils.isNotBlank(orderOpenId.getOpenId())){
					sendGiftBoxMsg(orderId, sendContent);
				}
			} catch (Exception e) {
				LOGGER.error("微信端订单[{}]推送礼盒发放消息错误", e);
			}
			if(updateResult){
				orderLogReuseService.save(orderId, redisClient.get(OperatorSidHolder.get()), "发送礼盒", "");
				if( StringUtils.isNotBlank(sendContent) ){
					OrderContactor contactor = orderContactorDao.selectContactorByOrderId(orderId);
					MobileCaptchaEntity mobileCapEntity  = buildSmsEntity(contactor.getPhone(),sendContent);
					SmsAdapter.sendMessage(mobileCapEntity);
				}
				ajaxResponse.setRes_code(OrderConstans.ORDER_SEND_GIFT_SUCCESS);
				LOGGER.debug("业务员{}对订单{}发送礼盒操作成功",OperatorSidHolder.get(),orderId);
			}else{
				LOGGER.debug("业务员{}对订单{}发送礼盒操作失败",OperatorSidHolder.get(),orderId);
			}
		}else{
			LOGGER.error("订单状态为[{}],非[{}]状态,不能发放礼盒",order.getBackState(),ZtOrderStatus.OPCONFIRM.getCode());
		}
		return ajaxResponse;
	}

	@Override
	public AjaxResponse sendOutNotice(String orderId, String noticeContent)throws Exception {
		AjaxResponse ajaxResponse = AjaxResponse.instance(OrderConstans.ORDER_OUTNOTICE_FAILED,"");
		Order order = orderDao.selectById(orderId);
		if(null != order && order.getBackState().equals(ZtOrderStatus.GIFTSEND.getCode())){
			order.setBackState(ZtOrderStatus.OUTNOTICE.getCode());
			order.setStateChangeHistory(order.getStateChangeHistory()+"-->"+ZtOrderStatus.OUTNOTICE.getCode());
			if(StringUtils.isNotBlank(order.getOperateRecord())){
				JSONObject operateRecord = (JSONObject) JSONObject.parse(order.getOperateRecord());
				operateRecord.put(OrderOperate.OUTNOTICE.getCode(), DateTimeUtil.date10());
				order.setOperateRecord(operateRecord.toString());
			}
			order.setOperator(redisClient.get(OperatorSidHolder.get()));
			Boolean updateResult = orderDao.updateOrder(order);
			OrderOpenId orderOpenId = orderOpenIdDaoImpl.getOpenIdByOrderId(orderId);
			/**微信订单需推送消息*/
			try {
				if(null != orderOpenId && StringUtils.isNotBlank(orderOpenId.getOpenId())){
					sendOutNoticeMsg(orderId);
				}
			} catch (Exception e) {
				LOGGER.error("微信端订单[{}]出行通知消息推送错误", e);
			}
			if(updateResult){
				orderLogReuseService.save(orderId, redisClient.get(OperatorSidHolder.get()), "发送出行通知", "");
				if( StringUtils.isNotBlank(noticeContent) ){
					OrderContactor contactor = orderContactorDao.selectContactorByOrderId(orderId);
					MobileCaptchaEntity mobileCapEntity  = buildSmsEntity(contactor.getPhone(),noticeContent);
					SmsAdapter.sendMessage(mobileCapEntity);
				}
				LOGGER.debug("业务员{}对订单{}成功发送出行通知",OperatorSidHolder.get(),orderId);
				ajaxResponse.setRes_code(OrderConstans.ORDER_OUTNOTICE_SUCCESS);
			}else{
				LOGGER.debug("业务员{}对订单{}发送出行通知操作失败",OperatorSidHolder.get(),orderId);
			}
		}else{
			LOGGER.error("订单状态为[{}],非[{}]状态,不能发放出行通知",order.getBackState(),ZtOrderStatus.GIFTSEND.getCode());
		}
		return ajaxResponse;
	}

	@Override
	public AjaxResponse confirmOrder(String orderId) throws Exception {
		AjaxResponse ajaxResponse = AjaxResponse.instance(OrderConstans.ORDER_OP_CONFIRM_FAILED,"");
		Order order = orderDao.selectById(orderId);
	    String productNature=order.getProductNature();
		if(null != order && order.getBackState().equals(ZtOrderStatus.PAYED.getCode())){
			OrderProduct orderProduct  = orderProductDao.selectByOrderId(orderId);		
			OrderContactor orderContactor = orderContactorDao.selectContactorByOrderId(orderId);		
			order.setBackState(ZtOrderStatus.OPCONFIRM.getCode());
			order.setFrontState(ZtOrderStatus.OPCONFIRM.getCode());
			order.setOperator(redisClient.get(OperatorSidHolder.get()));
			order.setStateChangeHistory(order.getStateChangeHistory()+"-->"+ZtOrderStatus.OPCONFIRM.getCode());
			OrderOpenId orderOpenId = orderOpenIdDaoImpl.getOpenIdByOrderId(orderId);
			/**微信订单需推送消息*/
			try {
				if(null != orderOpenId && StringUtils.isNotBlank(orderOpenId.getOpenId())){
					sendConfirmNoticeMsg(orderId);
				}
			} catch (Exception e) {
				LOGGER.error("微信端订单[{}]op确认消息推送错误", e);
			}
			String mediaId;
			if(productNature!=null){				
				if(productNature.equals("PACKAGE")||productNature.equals("COMBINATION")){
					mediaId=uploadOpConfirmPdf(orderId);
				}else{
					mediaId=uploadPieceOpConfirmPdf(orderId);
				}
			}else{
				mediaId=uploadOpConfirmPdf(orderId);
			}		
			ProductBookVo productBook = JSON.parseObject(orderProduct.getProductSnapshot(), ProductBookVo.class);
			Attachment attachment = new Attachment() ;
			attachment.setMediaId(mediaId);			
			if(StringUtils.isEmpty(productNature) || productNature.equals("PACKAGE")||productNature.equals("COMBINATION")){
				attachment.setName("行程确认单.pdf");//
			}
			else{
				attachment.setName("真旅行产品确认单.pdf");//
			}	
			List<Attachment> attachments = new ArrayList<Attachment>() ;
			if(!CollectionUtils.isEmpty(productBook.getAttachments())){
				attachments = productBook.getAttachments() ;
				attachments.add(0, attachment) ;
			}else{
				attachments.add(attachment) ;
				productBook.setAttachments(attachments);
			}
			orderProduct.setProductSnapshot(JSONObject.toJSONString(productBook));
			orderProductDao.update(orderProduct);

			Boolean updateResult = orderDao.updateOrder(order);
			if(updateResult){
				orderLogReuseService.save(orderId, redisClient.get(OperatorSidHolder.get()), "确认订单", "");
				orderReuseService.updateOperateRecord(orderId, OrderOperate.OPCONFIRM.getCode());	
				LOGGER.debug("业务员{}对订单{}进行确认",OperatorSidHolder.get(),orderId);
				MobileCaptchaEntity mobileCapEntity ;
				MailEntity mailEntity;
				if(StringUtils.isNotBlank(productNature)&&productNature.equals("VISA")){
					mobileCapEntity  = buildSmsEntity(orderContactor.getPhone(),SmsContentUtil.getVisaOpConfirmSmsContent(orderProduct.getProductTitle()));
					mailEntity = OrderConvertor.buildPieceMailEntity(order, orderContactor, orderProduct, attachments);	
				}else if(StringUtils.isBlank(productNature)||productNature.equals("PACKAGE")||productNature.equals("COMBINATION")){
					 mobileCapEntity  = buildSmsEntity(orderContactor.getPhone(),SmsContentUtil.getOpConfirmSmsContent(orderProduct.getProductTitle()));//
					 mailEntity = OrderConvertor.buildMailEntity(order, orderContactor, orderProduct, attachments);//	
				}else {
					mobileCapEntity  = buildSmsEntity(orderContactor.getPhone(),SmsContentUtil.getLocalTravelOpConfirmSmsContent(orderProduct.getProductTitle()));
					mailEntity = OrderConvertor.buildPieceMailEntity(order, orderContactor, orderProduct, attachments);			
				}			
				SmsAdapter.sendMessage(mobileCapEntity);
				MailSender.send(mailEntity);
				String noticeContent = SystemNoticeContentUtil.getOdContent(4, memberClientServiceImpl.getNickNameByMid(order.getCreator()), order.getOrderId(),productNature);
				systemNoticeClientServiceImpl.add(memberClientServiceImpl.getMemberIdByMid(order.getCreator()), NoticeType.ORDERTYPE, noticeContent);
				ajaxResponse.setRes_code(OrderConstans.ORDER_OP_CONFIRM_SUCCESS);
			}else{
				LOGGER.debug("业务员{}对订单{}进行确认操作失败",OperatorSidHolder.get(),orderId);
			}
		}else{
			LOGGER.error("订单状态为[{}],非[{}]状态,不能发放出行通知",order.getBackState(),ZtOrderStatus.PAYED.getCode());
		}
		return ajaxResponse;
	}




	private String uploadOpConfirmPdf(String orderId) throws Exception{
		String mediaId = null ;

		OrderDetailWo orderDetail = new OrderDetailWo() ;

		Order order = orderDao.selectById(orderId);
		OrderProduct product = orderProductDao.selectByOrderId(orderId);
		
		Order2DetailConverter.convertOrderDetailWo(order, product, orderDetail);
		List<OrderPassenger> passengers = orderPassengerDao.selectByOrderId(orderId) ;
		OpConfirmPDFEntity entity = convertOrderDetailWo(orderDetail, passengers) ;
		AbstractPdfView4Itext5 pdfView = new OpConfirmPdfView(entity) ;
		ByteArrayOutputStream baos = pdfView.document2ByteArrayOutputStream() ;
		mediaId = MediaClientUtil.uploadFile(baos.toByteArray(), MediaType.IMAGE.toString(), "行程确认单") ;

		return mediaId ;
	}

	
	private String uploadPieceOpConfirmPdf(String orderId) throws Exception{
		String mediaId=null;
		VisaOrderConfirmPDFEntity pdfEntity=new VisaOrderConfirmPDFEntity();
		Order order = orderDao.selectById(orderId);
		OrderProduct product = orderProductDao.selectByOrderId(orderId);		
		OrderContactor	orderContactor=orderContactorDao.selectContactorByOrderId(orderId);
		ProductBookVo productBook = JSON.parseObject(product.getProductSnapshot(), ProductBookVo.class);
		pdfEntity.setContactorName(orderContactor.getContactor());
		pdfEntity.setProductName(product.getProductTitle());
		if(productBook!=null){		
			pdfEntity.setVisaPriceType(productBook.getCostPriceName());
		}		
		pdfEntity.setFeeDetail(productBook.getAdditionalInfos().get(AdditionalRule.FEE_DETAIL));
		pdfEntity.setRefundPolicy(productBook.getAdditionalInfos().get(AdditionalRule.REFUND_POLICY));
		pdfEntity.setOrderNo(order.getOrderNo());
		PayDetailInfo payDetail=new PayDetailInfo();
		payDetail.setPayPrice(PriceUtil.cent2yuan(order.getPayAmount()));
		payDetail.setOrderPrice(PriceUtil.cent2yuan(order.getTotalPrice()));
		payDetail.setCouponPrice(PriceUtil.cent2yuan(order.getCouponSub()));
		pdfEntity.setPayDetail(payDetail);
		AbstractPdfView4Itext5 pdfView = new VisaOrderConfirmPdfView(pdfEntity) ;
		ByteArrayOutputStream baos = pdfView.document2ByteArrayOutputStream() ;
		mediaId = MediaClientUtil.uploadFile(baos.toByteArray(), MediaType.IMAGE.toString(), "真旅行产品确认单") ;
		return mediaId ;

	}


	/**消息推送测试
	 * @throws Exception */
	public static void main(String args[]) throws Exception{
//		OrderServiceImpl orderServiceImpl = new OrderServiceImpl();
//		String orderId = "";
//		try {
//			orderServiceImpl.sendOutNotice(orderId, "订单测试");
//		} catch (Exception e) {
//			LOGGER.error("发送消息错误", e);
//		}

		OpConfirmPDFEntity entity = new OpConfirmPDFEntity() ;
		AbstractPdfView4Itext5 pdfView = new OpConfirmPdfView(entity) ;
		ByteArrayOutputStream baos = pdfView.document2ByteArrayOutputStream() ;
		String mediaId = MediaClientUtil.uploadFile(baos.toByteArray(), MediaType.IMAGE.toString(), "行程确认单") ;
		System.out.println(mediaId) ;
	}

	private static OpConfirmPDFEntity convertOrderDetailWo(OrderDetailWo orderDetail, List<OrderPassenger> passengers){
		OpConfirmPDFEntity entity = new OpConfirmPDFEntity() ;
		OrderWo order = orderDetail.getOrder() ;
		OrderProductWo product = orderDetail.getProduct() ;
		entity.setOrderNo(order.getOrderNo());
		entity.setpName(product.getProductTitle());
		entity.setOrderDate(order.getCreateDate());
		entity.setPlayDate(product.getBookDate());
		entity.setOrderAmount(order.getTotalPrice());

		List<PassengerInfo> ps = new ArrayList<PassengerInfo>() ;
		for(int i=0;i<passengers.size(); i++){
			PassengerInfo pi = new PassengerInfo() ;
			OrderPassenger op = passengers.get(i) ;
			pi.setBirthday(op.getBirthday());
			pi.setCountry(op.getCountry());
			pi.setCredentialNum(op.getCredentialNum());
			pi.setCredentialsDeadLine(op.getCredentialDeadLine());
			pi.setCredentialType(op.getCredentialType());
			pi.setFirstName(op.getFirstName());
			pi.setFirstNameEn(op.getFirstEnName());
			pi.setGender(op.getGender());
			pi.setLastName(op.getLastName());
			pi.setLastNameEn(op.getLastEnName());
			pi.setPassengerEnName(op.getFirstEnName() + "/" + op.getLastEnName());
			pi.setPassengerName(op.getFirstName() + "/" + op.getLastName());
			pi.setType(op.getPassengerType());
			ps.add(pi) ;
		}
		entity.setPassengers(ps);
		List<AdditionalProductWo> additionalProductWos = orderDetail.getAdditionalProducts() ;

		if(additionalProductWos != null){
		List<AdditionalProduct> additionalProducts = new ArrayList<AdditionalProduct>();
	        for (AdditionalProductWo additionalProductWo : additionalProductWos) {
	            AdditionalProduct additionalProduct = new AdditionalProduct();
	            additionalProduct.setName(additionalProductWo.getName());
	            additionalProduct.setPrice(PriceUtil.yuan2cent(additionalProductWo.getPrice()));
	            additionalProduct.setNum(additionalProductWo.getNum());
	            additionalProduct.setTotalPrice(PriceUtil.yuan2cent(additionalProductWo.getTotalPrice()));
	            additionalProducts.add(additionalProduct);
	        }

			entity.setAdditionalProducts(additionalProducts);
		}

		List<ProductFlightInfo> pfi = new ArrayList<ProductFlightInfo>() ;
		if(product.getGoFlightList() != null){
			pfi.addAll(product.getGoFlightList()) ;
		}
		if(product.getMidlFlightList() != null){
			pfi.addAll(product.getMidlFlightList()) ;
		}
		if(product.getBackFlightList() != null){
			pfi.addAll(product.getBackFlightList()) ;
		}

		entity.setFlights(pfi);

		entity.setHotels(product.getHotelList());
		Map<String, String> additionalInfo = product.getAdditionalInfos() ;

		entity.setFeesContain(additionalInfo.get(AdditionalRule.FEE_INCLUDE.toString()));
		entity.setFeesNotContain(additionalInfo.get(AdditionalRule.FEE_NOT_INCLUDE.toString()));
		entity.setFreeItem(additionalInfo.get(AdditionalRule.GIFT_ITEMS.toString()));
		entity.setRefundPolicy(additionalInfo.get(AdditionalRule.REFUND_POLICY.toString()));
		return entity ;
	}

	@Override
	public Boolean updateFrontStatus(String orderId, String status)throws Exception {
		return updateStatus(orderId, status, null);
	}

	@Override
	public Boolean updateBackStatus(String orderId, String status)throws Exception {
		return updateStatus(orderId, null, status);
	}

	@Override
	public Boolean updateStatus(String orderId, String frontStatus,String backStatus) throws Exception {
		if(StringUtils.isBlank(orderId)){
			throw new IllegalArgumentException("更新订单状态时,订单ID不能为空");
		}
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("orderId", orderId);
		paramMap.put("frontState", frontStatus);
		paramMap.put("backState", backStatus);
		return orderDao.updateStatus(paramMap);
	}

	MobileCaptchaEntity buildSmsEntity(String mobilPhone,String smsContent){
		MobileCaptchaEntity mobileCapEntity = new MobileCaptchaEntity();
		mobileCapEntity.setMobileNum(mobilPhone);
		mobileCapEntity.setContent(smsContent);
		return mobileCapEntity;
	}


	public void sendGiftBoxMsg(String orderId,String sendContent){
		GiftBoxSendNotice giftSendNotice = buildGiftBoxMsg(orderId,sendContent);
		if(null != giftSendNotice){
			LOGGER.info("订单[{}]发送礼盒的推送消息为:",orderId,TZBeanUtils.describe(giftSendNotice));
			String msgUrl = WebEnv.get("server.path.wxServer","") + "/weixinController/giftBoxSend";
			HttpPost httppost = new HttpPost(msgUrl);
			StringEntity entity = new StringEntity(JSONObject.toJSONString(giftSendNotice), "UTF-8");
			httppost.setEntity(entity);
			HttpClient client = HttpClients.createDefault();
			try {
				HttpResponse response = client.execute(httppost);
				InputStream in  = response.getEntity().getContent();
				if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK ){
					LOGGER.info("微信订单[{}]发送礼盒消息推送成功",orderId);
				}
				else {
					LOGGER.info(IOUtils.toString(in));
				}
			} catch (ClientProtocolException e) {
				LOGGER.error(TZMarkers.p2, "http post ClientProtocolException", e);
			} catch (IOException e) {
				LOGGER.error(TZMarkers.p2, "http post IOException", e);
			}
		}
	}

	public GiftBoxSendNotice buildGiftBoxMsg(String orderId,String sendContent ){
		GiftBoxSendNotice notice = null;
		try {
			OrderOpenId orderOpenId = orderOpenIdDaoImpl.getOpenIdByOrderId(orderId);
			if(null == orderOpenId){
				LOGGER.info("订单{"+orderId+"}没有对应的openId信息");
			}else{
				if(StringUtils.isBlank(orderOpenId.getOpenId())){
					throw ZtrBizException.instance("", "微信端订单{"+orderId+"}对应的openId不存在,消息推送失败");
				}
				Order order = orderDao.selectById(orderId);			
				OrderContactor orderContactor = orderContactorDao.selectContactorByOrderId(order.getOrderId());				
				if(null != order){
					notice = new GiftBoxSendNotice();
					notice.setOpenId(orderOpenId.getOpenId());
					notice.setLogisticsCode(sendContent);
					notice.setLogisticsType(sendContent);
					if(orderContactor!=null)
					notice.setPhone(orderContactor.getPhone());
					notice.setReceiveAddress(sendContent);
					notice.setReceiver(memberClientServiceImpl.getNickNameByMid(order.getCreator()));
					notice.setUserName(memberClientServiceImpl.getNickNameByMid(order.getCreator()));
				}
			}
		} catch (SQLException e) {
			LOGGER.error("订单{}推送微信消息操作错误", orderId,e);
			return null;
		}
		return notice;
	}


	public void sendOutNoticeMsg(String orderId){
		OuttingNotice outNotice = buildOutNoticeMsg(orderId);
		if(null != outNotice){
			LOGGER.info("订单[{}]发送出行通知的推送消息为:",orderId,TZBeanUtils.describe(outNotice));
			String msgUrl = WebEnv.get("server.path.wxServer","") + "/weixinController/outting";
			HttpPost httppost = new HttpPost(msgUrl);
			StringEntity entity = new StringEntity(JSONObject.toJSONString(outNotice), "UTF-8");
			httppost.setEntity(entity);
			HttpClient client = HttpClients.createDefault();
			try {
				HttpResponse response = client.execute(httppost);
				InputStream in  = response.getEntity().getContent();
				if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK ){
					LOGGER.info("微信订单[{}发送出行通知消息推送成功",orderId);
				}
				else {
					LOGGER.info(IOUtils.toString(in));
				}
			} catch (ClientProtocolException e) {
				LOGGER.error(TZMarkers.p2, "http post ClientProtocolException", e);
			} catch (IOException e) {
				LOGGER.error(TZMarkers.p2, "http post IOException", e);
			}
		}
	}

	public OuttingNotice buildOutNoticeMsg(String orderId){
		OuttingNotice notice = null;
		try {
			OrderOpenId orderOpenId = orderOpenIdDaoImpl.getOpenIdByOrderId(orderId);
			if(null == orderOpenId){
				LOGGER.info("订单{"+orderId+"}没有对应的openId信息");
			}else{
				if(StringUtils.isBlank(orderOpenId.getOpenId())){
					throw ZtrBizException.instance("", "微信端订单{"+orderId+"}对应的openId不存在,消息推送失败");
				}
				Order order = orderDao.selectById(orderId);
				OrderProduct orderProduct = orderProductDao.selectByOrderId(orderId);
				if(null != order){
					notice = new OuttingNotice();
					notice.setBookDate(DateTimeUtil.date10(orderProduct.getBookDate()).replaceFirst("-", "年").replaceFirst("-", "月")+"日");
					notice.setComment("如果还有任何疑问,请直接联系真旅管家回复您的问题即可,或电话联系:400-620-6266转6.祝您行程愉快!");
					notice.setProductContent(orderProduct.getProductTitle());
					notice.setUserName(memberClientServiceImpl.getNickNameByMid(order.getCreator()));
					notice.setOpenId(orderOpenId.getOpenId());
				}else{
					LOGGER.error("订单[{}]不存在,推送微信消息失败",orderId);
					throw ZtrBizException.instance("", "订单{"+orderId+"}不存在");
				}

			}
		} catch (SQLException e) {
			LOGGER.error("订单{}推送微信消息操作错误", orderId,e);
			return null;
		}
		return notice;
	}

	public void sendConfirmNoticeMsg(String orderId){
		OpConfirmNotice notice = buildConfirmNoticeMsg(orderId);
		if(null != notice){
			LOGGER.info("订单[{}]op确认的推送消息为:",orderId,TZBeanUtils.describe(notice));
			String msgUrl = WebEnv.get("server.path.wxServer","") + "/weixinController/opConfirm";
			HttpPost httppost = new HttpPost(msgUrl);
			StringEntity entity = new StringEntity(JSONObject.toJSONString(notice), "UTF-8");
			httppost.setEntity(entity);
			HttpClient client = HttpClients.createDefault();
			try {
				HttpResponse response = client.execute(httppost);
				InputStream in  = response.getEntity().getContent();
				if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK ){
					LOGGER.info("微信订单[{}op确认的消息推送成功",orderId);
				}
				else {
					LOGGER.info(IOUtils.toString(in));
				}
			} catch (ClientProtocolException e) {
				LOGGER.error(TZMarkers.p2, "http post ClientProtocolException", e);
			} catch (IOException e) {
				LOGGER.error(TZMarkers.p2, "http post IOException", e);
			}
		}
	}

	public OpConfirmNotice buildConfirmNoticeMsg(String orderId){
		OpConfirmNotice notice = null;
		try {
			OrderOpenId orderOpenId = orderOpenIdDaoImpl.getOpenIdByOrderId(orderId);
			if(null == orderOpenId){
				LOGGER.info("订单{"+orderId+"}没有对应的openId信息");
			}else{
				if(StringUtils.isBlank(orderOpenId.getOpenId())){
					throw ZtrBizException.instance("", "微信端订单{"+orderId+"}对应的openId不存在,消息推送失败");
				}
				Order order = orderDao.selectById(orderId);
				if(null != order){
					notice = new OpConfirmNotice();
					notice.setCreateDate(DateTimeUtil.date10(order.getCreateDate()).replaceFirst("-", "年").replaceFirst("-", "月")+"日");
					notice.setPayAmount(MoneyUtil.cent2Yuan(order.getPayAmount()));
					notice.setComment("如果还有任何疑问,请直接联系真旅管家回复您的问题即可,或电话联系:400-620-6266转6.祝您行程愉快!");
					notice.setOpenId(orderOpenId.getOpenId());
				}else{
					LOGGER.error("订单[{}]不存在,推送微信消息失败",orderId);
					throw ZtrBizException.instance("", "订单{"+orderId+"}不存在");
				}

			}
		} catch (SQLException e) {
			LOGGER.error("订单{}推送微信消息操作错误", orderId,e);
			return null;
		}
		return notice;
	}

	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED, rollbackFor = Throwable.class)
    @Override
    public AjaxResponse confirm(String orderId) throws Exception {

        AjaxResponse ajaxResponse =  AjaxResponse.instance("FAILURE","");

        OrderDetailWo orderDetailWo = selectTempOrderById(orderId);
        OrderDetailWo originalOrderDetailWo = selectOriginalOrderById(orderId);
        OrderWo originalOrderWo = originalOrderDetailWo.getOrder();
        Order originalOrder = buildOrderByOrderWo(originalOrderWo);

        if (orderDetailWo != null) {
            OrderWo orderWo = orderDetailWo.getOrder();
            Order order = buildOrderByOrderWo(orderWo);
            orderDao.update(order);

            OrderProduct orderProduct = buildproductWo(orderDetailWo, orderWo);
            orderProductDao.update(orderProduct);

            OrderContactor orderContactor = orderDetailWo.getOrderContactor();
            orderContactorDao.update(orderContactor);

            List<OrderPassenger> passengers = orderDetailWo.getPassengers();
            for (OrderPassenger orderPassenger : passengers) {
                orderPassengerDao.update(orderPassenger);
            }

            long price = order.getPayAmount() - originalOrder.getPayAmount();
            CommonResponse response = commonOrderReuseService.createCommonOrder(originalOrder, price, orderDetailWo.getProduct().getProductTitle());
            if (response.isSuccess()) {
                ajaxResponse.setRes_code("SUCCESS");
            }

        } else {
            CommonResponse response = commonOrderReuseService.createCommonOrder(originalOrder, 0l, null);
            if (response.isSuccess()) {
                ajaxResponse.setRes_code("SUCCESS");
            }
        }

        return ajaxResponse;
    }
	private static Order buildOrderByOrderWo(OrderWo orderWo) {
        Order order = new Order();
        order.setOrderId(orderWo.getOrderId());
        order.setOrderNo(orderWo.getOrderNo());
        order.setOrderType(orderWo.getOrderType());
        order.setCreator(orderWo.getCreator());
        order.setTotalPrice(PriceUtil.yuan2cent(orderWo.getTotalPrice()));
        order.setPayAmount(PriceUtil.yuan2cent(orderWo.getPayAmount()));
        order.setCouponSub(PriceUtil.yuan2cent(orderWo.getCouponSub()));
        order.setIntegralSub(PriceUtil.yuan2cent(orderWo.getIntegralSub()));
        return order;
    }
	private static OrderProduct buildproductWo(OrderDetailWo orderDetailWo, OrderWo orderWo) {
        OrderProduct product = new OrderProduct();

        OrderProductWo productWo = orderDetailWo.getProduct();
        List<AdditionalProductWo> additionalProductWos = orderDetailWo.getAdditionalProducts();
        List<AttachmentWo> attachmentWos = orderDetailWo.getAttachments();
        product.setOrderId(orderWo.getOrderId());
        ProductBookVo productBookVo = JSON.parseObject(productWo.getProductSnapshot(), ProductBookVo.class);
        if (additionalProductWos != null && additionalProductWos.size() > 0) {
            productBookVo.setAdditionalProducts(buildAdditionalProductByAdditionalProductWo(additionalProductWos));
        }
        if (attachmentWos != null && attachmentWos.size() > 0) {
            productBookVo.setAttachments(buildAttachmentByAttachmentWo(attachmentWos));
        }
        if (productWo.getGoFlightList() != null || productWo.getBackFlightList() != null) {
            productBookVo.setGoFlightList(productWo.getGoFlightList());
            productBookVo.setMidlFlightList(productWo.getMidlFlightList());
            productBookVo.setBackFlightList(productWo.getBackFlightList());
        }
        if (productWo.getHotelList() != null && productWo.getHotelList().size() > 0) {
            productBookVo.setHotelList(productWo.getHotelList());
        }
        product.setProductSnapshot(JSONObject.toJSONString(productBookVo));
        return product;
    }
	private static List<AdditionalProduct> buildAdditionalProductByAdditionalProductWo(List<AdditionalProductWo> additionalProductWos) {
        List<AdditionalProduct> additionalProducts = new ArrayList<AdditionalProduct>();
        for (AdditionalProductWo additionalProductWo : additionalProductWos) {
            AdditionalProduct additionalProduct = new AdditionalProduct();
            additionalProduct.setName(additionalProductWo.getName());
            additionalProduct.setPrice(PriceUtil.yuan2cent(additionalProductWo.getPrice()));
            additionalProduct.setNum(additionalProductWo.getNum());
            additionalProduct.setTotalPrice(PriceUtil.yuan2cent(additionalProductWo.getTotalPrice()));
            additionalProducts.add(additionalProduct);
        }

        return additionalProducts;
    }

    private static List<Attachment> buildAttachmentByAttachmentWo(List<AttachmentWo> attachmentWos) {
        List<Attachment> attachments = new ArrayList<Attachment>();
        for (AttachmentWo attachmentWo : attachmentWos) {
            Attachment attachment = new Attachment();
            attachment.setMediaId(attachmentWo.getMediaId());
            attachment.setName(attachmentWo.getName());
            attachments.add(attachment);
        }

        return attachments;
    }

    @Override
	public Integer countOrders(OrderSearchCriteria criteria) throws Exception {
		Map<String, Object> paramsMap = convertOrderSearchCriteria(criteria);
		return orderOrderProductDao.count(paramsMap);
	}
    /**
	 * 订单搜索条件OrderSearchCriteria转换成Map
	 *
	 * @param OrderSearchCriteria
	 * @return Map
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map<String, Object> convertOrderSearchCriteria(OrderSearchCriteria criteria) {
		Map map = new HashMap();
		map.put("orderNo", criteria.getOrderNo());
		map.put("productNo", criteria.getProductId());
		// 产品标题 模糊查询
		if (StringUtils.isNotBlank(criteria.getProductTitle())) {
			String productTitle;
			if (criteria.getProductTitle().contains("%")) {
				productTitle = criteria.getProductTitle().replace("%", "\\%");
			} else {
				productTitle = criteria.getProductTitle();
			}
			map.put("productTitle", "%" + productTitle + "%");
		}

		if (StringUtils.isNotBlank(criteria.getStatus())) {
			map.put("backState", criteria.getStatus());
		}

		if (StringUtils.isNotBlank(criteria.getSource())) {
			map.put("orderFrom", criteria.getSource());
		}

		if (StringUtils.isNotBlank(criteria.getMemberId())) {
			map.put("creator", criteria.getMemberId());
		}

		if (StringUtils.isNotBlank(criteria.getTravellerNames())) {
            map.put("travellerNames", "%" + criteria.getTravellerNames() + "%");
        }

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if (StringUtils.isNotBlank(criteria.getCreateDateFrom())) {
				map.put("createDateFrom", format.parse(criteria.getCreateDateFrom() + " 00:00:00"));
			}
			if (StringUtils.isNotBlank(criteria.getCreateDateTo())) {
				map.put("createDateTo", format.parse(criteria.getCreateDateTo() + " 23:59:59"));
			}
		} catch (ParseException e) {
			LOGGER.error(e.getMessage(), e);
		}

		if (StringUtils.isNotBlank(criteria.getOrderPriceLowerBound())) {
			Long orderPriceLowerBound = Long.parseLong(MoneyUtil.yuan2Cent(criteria.getOrderPriceLowerBound()));
			map.put("orderPriceLowerBound", orderPriceLowerBound);
		}
		if (StringUtils.isNotBlank(criteria.getOrderPriceUpperBound())) {
			Long orderPriceUpperBound = Long.parseLong(MoneyUtil.yuan2Cent(criteria.getOrderPriceUpperBound()));
			map.put("orderPriceUpperBound", orderPriceUpperBound);
		}
		return map;
	}

	@Override
	public OrderDetailVO getOrderDetailByOrderId(String orderId) throws Exception {
		OrderDetailVO orderDetail = new OrderDetailVO();
		// 订单信息
		Order order = orderDao.selectById(orderId);
		if (order == null) {
			LOGGER.error(OrderConstans.ORDER_GET_ORDER_BY_ID_FAILED_MSG + "  orderId：" + orderId);
			throw ZtrBizException.instance(OrderConstans.ORDER_GET_ORDER_BY_ID_FAILED_CODE, OrderConstans.ORDER_GET_ORDER_BY_ID_FAILED_MSG);
		}
		orderDetail.setOrderId(order.getOrderId());
		orderDetail.setOrderNo(order.getOrderNo());
		orderDetail.setCreatorMid(order.getCreator());
		orderDetail.setStatus(ZtOrderStatus.getDescByCode(order.getBackState()));
		orderDetail.setStatusEnum(order.getBackState());
		orderDetail.setProductNature(order.getProductNature());
		// TO DO: 订单类型转换
		if (order.getOrderType().equals("international")) {
			orderDetail.setType("国际产品订单");
			orderDetail.setTypeEnum("INTERNATIONAL");
		} else if (order.getOrderType().equals("domestic")) {
			orderDetail.setType("国内产品订单");
			orderDetail.setTypeEnum("DOMESTIC");
		}
		orderDetail.setRequirementNotes(order.getRemark());
		if (order.getTotalPrice() != null) {
			//取自订单
			orderDetail.setOrderTotalPrice(PriceUtil.cent2yuan(order.getTotalPrice()));
		}

		// 产品信息
		OrderProduct product = orderProductDao.selectByOrderId(orderId); // 第一期一个订单只包含一个产品
		if (product == null) {
			LOGGER.error(OrderConstans.ORDER_GET_ORDER_PRODUCT_FAILED_MSG + "  orderId：" + orderId);
			throw ZtrBizException.instance(OrderConstans.ORDER_GET_ORDER_PRODUCT_FAILED_CODE, OrderConstans.ORDER_GET_ORDER_PRODUCT_FAILED_MSG);
		}
		List<OrderProductVO> productVoList = buildProductInfo(product);
		orderDetail.setProducts(productVoList);
		if(StringUtils.isEmpty(order.getProductNature()) || (order.getProductNature().equals(Nature.PACKAGE.toString()) || order.getProductNature().equals(Nature.COMBINATION.toString()))){
			// 供应商	
			String supplier = OrderReuseConverter.buildSupplier(product.getProviderInfo());
			orderDetail.setSupplier(supplier);
			//退/补款单
			CommonOrder commonOrder = commonOrderDaoImpl.selectByOriginOrderNo(order.getOrderNo());
			buildCommonOrder(orderDetail, commonOrder, false);
			buildFeesDetails(orderDetail, order, product.getProductSnapshot(), commonOrder);
		}else{
			buildFeesDetails(orderDetail, order, product.getProductSnapshot(), null);
		}
		// 联系人信息
		OrderContactorPoMaterial contactors=orderContactorReuseService.getOrderContactorsById(orderId);
		if (contactors!=null) {
			List<ContactorVO> contactorVo = buildOperatorContactorList(contactors);
			orderDetail.setContactors(contactorVo);
		} else {
			LOGGER.error(OrderConstans.ORDER_GET_ORDER_CONTACTOR_FAILED_MSG + "  orderId：" + orderId);
		}
		// 旅客信息
		if(StringUtils.isEmpty(order.getProductNature()) || !order.getProductNature().equals(Nature.VISA.toString())){
			List<OrderPassenger> travellers = orderPassengerDao.selectByOrderId(orderId);
			if (travellers != null && travellers.size() != 0) {
				List<TravellerVO> travellerVoList = buildTravellerList(travellers);
				orderDetail.setTravellers(travellerVoList);
			} else {
				LOGGER.error(OrderConstans.ORDER_GET_ORDER_TRAVELLERS_FAILED_MSG + "  orderId：" + orderId);
			}
		}
		// 订单操作日志
		List<OrderLog> logs = orderLogDao.selectByOrderId(orderId);
		if (logs != null && logs.size() != 0) {
			List<OrderLogVO> logVoList = buildOrderLogList(logs);
			orderDetail.setOperationLogs(logVoList);
		} else {
			LOGGER.error(OrderConstans.ORDER_GET_ORDER_LOGS_FAILED_MSG + "  orderId：" + orderId);
		}
		return orderDetail;
	}
	private List<OrderProductVO> buildProductInfo(OrderProduct product) {
		List<OrderProductVO> productVoList = new ArrayList<OrderProductVO>();
		OrderProductVO productVo = new OrderProductVO();
		productVo.setProductId(product.getProductId());
		productVo.setProductNo(product.getProductNo());
		productVo.setProductTitle(product.getProductTitle());
		ProductBookVo productBook = JSON.parseObject(product.getProductSnapshot(), ProductBookVo.class);
		productVo.setCostPriceName(productBook.getCostPriceName());
		productVo.setPieceType(product.getPieceType());
		productVo.setDepartureDate(DateTimeUtil.convertDateToString(DateTimeUtil.DATE_PATTERN, product.getBookDate()));
		if(product.getPieceType()==null){
			productVo.setTravelDays(product.getTripDays());
			if (product.getPackageName() != null) {
				productVo.setPackageName(product.getPackageName());
			}
		}
		productVoList.add(productVo);
		return productVoList;
	}
	private List<TravellerVO> buildTravellerList(List<OrderPassenger> travellers) {
		List<TravellerVO> travellerVoList = new ArrayList<TravellerVO>();
		for (OrderPassenger traveller : travellers) {
			TravellerVO travellerVo = new TravellerVO();
			travellerVo.setNameCn(traveller.getName());
			travellerVo.setNameEn(traveller.getEnName());
			// if( StringUtils.isNotBlank(traveller.getFirstName()) &&
			// StringUtils.isNotBlank(traveller.getLastName()) ){
			// travellerVo.setNameEn(traveller.getFirstName() + "/" +
			// traveller.getLastName());
			// }
			PassengerType passengerType = PassengerType.valueOf(traveller.getPassengerType());
			if (passengerType != null) {
				travellerVo.setType(passengerType.getDesc());
			} else {
				LOGGER.error("旅客类型转换错误");
			}

			Gender gender = Gender.valueOf(traveller.getGender());
			if (gender != null) {
				travellerVo.setGender(gender.getDesc());
			} else {
				LOGGER.error("旅客性别转换错误");
			}
			travellerVo.setNationality(traveller.getCountry());
			travellerVo.setBirthday(traveller.getBirthday());

			CredentialType credentialType = CredentialType.valueOf(traveller.getCredentialType());
			if (credentialType != null) {
				travellerVo.setCredentialType(credentialType.getDesc());
			} else {
				LOGGER.error("证件类型转换错误");
			}

			travellerVo.setCredentialNo(traveller.getCredentialNum());
			travellerVo.setCredentialExpireDate(traveller.getCredentialDeadLine());
			travellerVoList.add(travellerVo);
		}
		return travellerVoList;
	}

	private List<ContactorVO> buildContactorList(List<OrderContactor> contactors) {
		List<ContactorVO> contactorVoList = new ArrayList<ContactorVO>();
		for(OrderContactor contactor : contactors){
			ContactorVO contactorVo = new ContactorVO();
			contactorVo.setName(contactor.getContactor());
			contactorVo.setPhone(contactor.getPhone());
			contactorVo.setEmail(contactor.getEmail());
			String address = "";
			if (StringUtils.isNotBlank(contactor.getProvince())) {
				address += contactor.getProvince();
			}
			if (StringUtils.isNotBlank(contactor.getCity())) {
				address += contactor.getCity();
			}
			if (StringUtils.isNotBlank(contactor.getCounty())) {
				address += contactor.getCounty();
			}
			if (StringUtils.isNotBlank(contactor.getAddress())) {
				address += contactor.getAddress();
			}
			contactorVo.setAddress(address);
			contactorVoList.add(contactorVo);
		}
		return contactorVoList;
	}
	
	
	private List<ContactorVO> buildOperatorContactorList(OrderContactorPoMaterial orderContactor){
		List<ContactorVO> contactorVoList = new ArrayList<ContactorVO>();
		if(orderContactor!=null){
			ContactorVO contactorVo=new ContactorVO();
			ContactorVO contactorVoMaterial=new ContactorVO();
			contactorVo.setName(orderContactor.getContactor());
			contactorVo.setPhone(orderContactor.getPhone());
			contactorVo.setEmail(orderContactor.getEmail());
			contactorVo.setAddress(orderContactor.getProvince()+orderContactor.getCity()+orderContactor.getCounty()+orderContactor.getAddress());
			contactorVoList.add(contactorVo);
			if(orderContactor.getMaterialContactor()!=null){
				contactorVoMaterial.setName(orderContactor.getMaterialContactor());
				contactorVoMaterial.setPhone(orderContactor.getMaterialPhone());
				contactorVoMaterial.setEmail(orderContactor.getMaterialEmail());
				contactorVoMaterial.setAddress(orderContactor.getMaterialAddress());				
				contactorVoList.add(contactorVoMaterial);
			}

		}
		return contactorVoList;
	}
	
	private void buildCommonOrder(OrderDetailVO orderDetail, CommonOrder commonOrder, boolean isOroginal){
		if (commonOrder != null) {
			long minusedPrice = commonOrder.getPrice();
			if (CommonOrderType.OP_CONFIRM_REPAIR.equals(commonOrder.getType())) {
				minusedPrice = minusedPrice * (-1);
			}
			if(isOroginal){
				long originalTotalPrice = PriceUtil.yuan2cent(orderDetail.getOriginalTotalPrice());
//				long totalPrice = originalTotalPrice - minusedPrice;
				orderDetail.setOrderTotalPrice(PriceUtil.cent2yuan(originalTotalPrice));
			}else{
				long totalPrice = PriceUtil.yuan2cent(orderDetail.getOrderTotalPrice());
				long originalTotalPrice = totalPrice + minusedPrice;
				orderDetail.setOriginalTotalPrice(PriceUtil.cent2yuan(originalTotalPrice));
			}
			orderDetail.setMinusedTotalPrice(PriceUtil.cent2yuan(minusedPrice < 0?minusedPrice*(-1): minusedPrice));
			orderDetail.setCommonOrderStatus(commonOrder.getStatus());
			orderDetail.setCommonOrderType(commonOrder.getType().toString());
			orderDetail.setCommonOrderRemark(commonOrder.getRemark());
		}
	}
	private void buildFeesDetails(OrderDetailVO orderDetail, final Order order, String productSnapshotStr,final CommonOrder commonOrder) {
		FeesDetailVO feesDetail = new FeesDetailVO();
		ProductBookVo productSnapshot = JSON.parseObject(productSnapshotStr, ProductBookVo.class);
		putFee(feesDetail, productSnapshot);
		if (order.getCouponSub() != null) {//代金券
			feesDetail.setCouponPrice(PriceUtil.cent2yuan(order.getCouponSub()));
			if(!StringUtils.isEmpty(order.getDiscountCouponId())){
				CouponItemBean couponItem = new CouponItemBean();
				couponItem = couponService.getCouponItem(order.getDiscountCouponId());
				feesDetail.setCouponName(couponItem.getName());
			}
			
		}
		
		if (order.getPayAmount() != null) {//应付
				feesDetail.setPayPrice(PriceUtil.cent2yuan(order.getPayAmount()));
		}
		Map<String, String> paidInfo = new LinkedHashMap<String, String>(2,1F);
		Map<String, Long> orderPay = orderPaymentService.queryPaidAmount(order.getPaySerialNum());
		if(orderPay.get("paidAmount") != null){
			paidInfo.put("转账", PriceUtil.cent2yuan(orderPay.get("paidAmount")));
		}else{
			paidInfo.put("转账", PriceUtil.cent2yuan(0L));
		}
//		if(orderPay.get("refundedAmount") != null){
//			paidInfo.put("主定单退款", PriceUtil.cent2yuan(orderPay.get("refundedAmount")));
//		}
		if(commonOrder != null){
			if(CommonOrderType.OP_CONFIRM_REFUND.equals(commonOrder.getType())){
				//退款
				if(orderPay.get("refundedAmount") != null){
					paidInfo.put("OP退款单退款",PriceUtil.cent2yuan(orderPay.get("refundedAmount")));
				}
			}else{
				//补款
				if(StringUtils.isNotBlank(commonOrder.getPaySerialNum())){
					Map<String, Long> commonOrderPay = orderPaymentService.queryPaidAmount(order.getPaySerialNum());
					if(commonOrderPay.get("paidAmount") != null){
						paidInfo.put("OP补款单补款", PriceUtil.cent2yuan(commonOrderPay.get("paidAmount")));
					}
					if(commonOrderPay.get("refundedAmount") != null){
						paidInfo.put("OP补款单退款", PriceUtil.cent2yuan(commonOrderPay.get("refundedAmount")));
					}
				}
			}
		}
		feesDetail.setPaidInfo(paidInfo);
		orderDetail.setFeesDetail(feesDetail);
		orderDetail.setAdditionalProducts(productSnapshot.getAdditionalProducts());//原订单无此项
		Map<AdditionalRule, String> additionalInfos = productSnapshot.getAdditionalInfos();
		if (additionalInfos != null) {
			orderDetail.setFeesInclude(additionalInfos.get(AdditionalRule.FEE_INCLUDE));
			orderDetail.setFeesExclude(additionalInfos.get(AdditionalRule.FEE_NOT_INCLUDE));
			orderDetail.setGiftItems(additionalInfos.get(AdditionalRule.GIFT_ITEMS));
			orderDetail.setRefundPolicy(additionalInfos.get(AdditionalRule.REFUND_POLICY));
		}
	}
	private List<OrderLogVO> buildOrderLogList(List<OrderLog> logs) {
		List<OrderLogVO> logVoList = new ArrayList<OrderLogVO>();
		for (OrderLog log : logs) {
			OrderLogVO logVo = new OrderLogVO();
			logVo.setContent(log.getContent());
			logVo.setOperator(log.getOperator());
			logVo.setRemark(log.getRemark());
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			logVo.setOperateTime(formater.format(log.getOperateTime()));
			logVoList.add(logVo);
		}
		return logVoList;
	}
	private void putFee(FeesDetailVO feesDetail, ProductBookVo productSnapshot){
		if (productSnapshot.getPackageId() == null) {
			feesDetail.setAdultPrice(productSnapshot.getAdultPrice());
			feesDetail.setChildPrice(productSnapshot.getChildrenPrice());

			feesDetail.setSingleRoomSupplement(productSnapshot.getSingleRoomDiff());
			if (productSnapshot.getAdultNum() != null) {
				feesDetail.setAdultNum(productSnapshot.getAdultNum().toString());
			}
			if (productSnapshot.getChildNum() != null) {
				feesDetail.setChildNum(productSnapshot.getChildNum().toString());
			}
			if (productSnapshot.getSingleNum() != null) {
				feesDetail.setSingleRoomNum(productSnapshot.getSingleNum().toString());
			}
			feesDetail.setTotalAdultPrice(productSnapshot.getTotalAdultPrice());
			feesDetail.setTotalChildPrice(productSnapshot.getTotalChildPrice());
			feesDetail.setTotalSingleSupplement(productSnapshot.getTotalSingleDiff());
		} else {
			feesDetail.setPackageName(productSnapshot.getPackageName());
			if (productSnapshot.getPackageNum() != null) {
				feesDetail.setPackageNum(productSnapshot.getPackageNum().toString());
			}
			if (productSnapshot.getPackagePrice() != null) {
				feesDetail.setPackagePrice(productSnapshot.getPackagePrice());
			}
			if (productSnapshot.getTotalPackagePrice() != null) {
				feesDetail.setPackageTotalPrice(productSnapshot.getTotalPackagePrice());
			}
		}
	}

	@Override
	public OrderDetailVO getOriginalOrder(String orderId) throws Exception {
		OrderDetailVO originalOrderVo = new OrderDetailVO();
		// 订单信息
		Order order = orderDao.selectById(orderId);
		if (order == null || StringUtils.isBlank(order.getOriginalOrder())) {
			LOGGER.error(OrderConstans.ORDER_GET_ORDER_BY_ID_FAILED_MSG + "  orderId：" + orderId);
			throw ZtrBizException.instance(OrderConstans.ORDER_GET_ORDER_BY_ID_FAILED_CODE, OrderConstans.ORDER_GET_ORDER_BY_ID_FAILED_MSG);
		}
		originalOrderVo.setOrderId(order.getOrderId());
		originalOrderVo.setOrderNo(order.getOrderNo());
		originalOrderVo.setStatus(ZtOrderStatus.getDescByCode(order.getBackState()));
		originalOrderVo.setStatusEnum(order.getBackState());
		if (order.getOrderType().equals("international")) {
			originalOrderVo.setType("国际产品订单");
			originalOrderVo.setTypeEnum("INTERNATIONAL");
		} else if (order.getOrderType().equals("domestic")) {
			originalOrderVo.setType("国内产品订单");
			originalOrderVo.setTypeEnum("DOMESTIC");
		}
		originalOrderVo.setRequirementNotes(order.getRemark());
		OrderDetailWo originalOrderWo = JSONObject.parseObject(order.getOriginalOrder(), OrderDetailWo.class);
		if (originalOrderWo.getOrder() != null) {
			//取自原订单
			originalOrderVo.setOriginalTotalPrice(originalOrderWo.getOrder().getTotalPrice());
		}
		OrderProductWo productWo = originalOrderWo.getProduct();
		// 供应商信息
		originalOrderVo.setSupplier(productWo.getSupplier());
		// 产品信息
		List<OrderProductVO> productVoList = buildProductInfo(productWo);
		originalOrderVo.setProducts(productVoList);
		// 费用详情
		buildFeesDetails(originalOrderVo, originalOrderWo, order.getPaySerialNum());
		// 联系人信息
		OrderContactor contactor = originalOrderWo.getOrderContactor();
		if (contactor != null) {
			List<OrderContactor> contactors = new ArrayList<>();
			contactors.add(contactor);
			List<ContactorVO> contactorVo = buildContactorList(contactors);
			originalOrderVo.setContactors(contactorVo);
		} else {
			LOGGER.error(OrderConstans.ORDER_GET_ORDER_CONTACTOR_FAILED_MSG + "  orderId：" + orderId);
		}
		// 旅客信息
		List<OrderPassenger> travellers = originalOrderWo.getPassengers();
		if (travellers != null && travellers.size() != 0) {
			List<TravellerVO> travellerVoList = buildTravellerList(travellers);
			originalOrderVo.setTravellers(travellerVoList);
		} else {
			LOGGER.error(OrderConstans.ORDER_GET_ORDER_TRAVELLERS_FAILED_MSG + "  orderId：" + orderId);
		}
		// 退款/补款订单
		CommonOrder commonOrder = commonOrderDaoImpl.selectByOriginOrderNo(order.getOrderNo());
		buildCommonOrder(originalOrderVo, commonOrder, true);
		// 订单操作日志
		List<OrderLog> logs = orderLogDao.selectByOrderId(orderId);
		if (logs != null && logs.size() != 0) {
			List<OrderLogVO> logVoList = buildOrderLogList(logs);
			originalOrderVo.setOperationLogs(logVoList);
		} else {
			LOGGER.error(OrderConstans.ORDER_GET_ORDER_LOGS_FAILED_MSG + "  orderId：" + orderId);
		}
		return originalOrderVo;
	}
	private List<OrderProductVO> buildProductInfo(OrderProductWo product) {
		List<OrderProductVO> productVoList = new ArrayList<OrderProductVO>();
		OrderProductVO productVo = new OrderProductVO();
		productVo.setProductId(product.getProductId());
		productVo.setProductNo(product.getProductNo());
		productVo.setProductTitle(product.getProductTitle());
		productVo.setDepartureDate(DateTimeUtil.convertDateToString(DateTimeUtil.DATE_PATTERN, DateTimeUtil.parseDate10(product.getBookDate())));
		productVo.setTravelDays(product.getTripDays());
		if (product.getPackageName() != null) {
			productVo.setPackageName(product.getPackageName());
			productVo.setPackageName(product.getPackageName());
		}
		productVoList.add(productVo);
		return productVoList;
	}
	private void buildFeesDetails(OrderDetailVO orderDetail, final OrderDetailWo wo, final String paidSerialNum) {
		FeesDetailVO feesDetail = new FeesDetailVO();
		ProductBookVo productSnapshot = JSON.parseObject(wo.getProduct().getProductSnapshot(), ProductBookVo.class);
		putFee(feesDetail, productSnapshot);
		OrderWo oWo = wo.getOrder();
		if(oWo != null){
			if (oWo.getCouponSub() != null) {
				feesDetail.setCouponPrice(oWo.getCouponSub());
			}
			if (oWo.getPayAmount() != null) {
				feesDetail.setPayPrice(oWo.getPayAmount());
			}
		}
		Map<String, String> paidInfo = new HashMap<String, String>(1,1F);
		Map<String, Long> orderPay = orderPaymentService.queryPaidAmount(paidSerialNum);
		if(orderPay.get("paidAmount") != null){
			paidInfo.put("转账", PriceUtil.cent2yuan(orderPay.get("paidAmount")));
		}else{
			paidInfo.put("转账", PriceUtil.cent2yuan(0L));
		}
		feesDetail.setPaidInfo(paidInfo);
		orderDetail.setFeesDetail(feesDetail);
		Map<String, String> additionalInfos = wo.getProduct().getAdditionalInfos();
		if (additionalInfos != null) {
			orderDetail.setFeesInclude(additionalInfos.get(AdditionalRule.FEE_INCLUDE.toString()));
			orderDetail.setFeesExclude(additionalInfos.get(AdditionalRule.FEE_NOT_INCLUDE.toString()));
			orderDetail.setGiftItems(additionalInfos.get(AdditionalRule.GIFT_ITEMS.toString()));
			orderDetail.setRefundPolicy(additionalInfos.get(AdditionalRule.REFUND_POLICY.toString()));
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<OrderListVO> searchOrders(OrderSearchCriteria criteria) throws Exception {
		Map paramsMap = convertOrderSearchCriteria(criteria);
		paramsMap.put("offset", (criteria.getPageNo() - 1) * criteria.getPageSize());
		paramsMap.put("limit", criteria.getPageSize());
		List<OrderPO> orderList = orderOrderProductDao.select(paramsMap);
		List<OrderListVO> orderVoList = OrderConvertor.convertPOList2VOList(orderList);
		return orderVoList;
	}

	@Override
    public OrderDetailWo selectConfirmOrderById(String orderId) throws Exception{
        OrderDetailWo orderDetail = null;

        if(StringUtils.isNotBlank(orderId)){

            orderDetail = selectTempOrderById(orderId);
            if (orderDetail == null) {
                orderDetail = new OrderDetailWo() ;
                Order order = orderDao.selectById(orderId);
                OrderProduct product = orderProductDao.selectByOrderId(orderId);

                Order2DetailConverter.convertOrderDetailWo(order, product, orderDetail);

                CouponItemBean couponItem = new CouponItemBean();
                if(StringUtils.isNotBlank(orderDetail.getPrice().getCouponId())){
                    couponItem = couponService.getCouponItem(orderDetail.getPrice().getCouponId());
                }

                OrderReuseConverter.setOrderProductAndCoupon(couponItem, orderDetail);               
             	OrderContactor contactor = orderContactorDao.selectContactorByOrderId(orderId);               
                List<OrderPassenger> passengers = orderPassengerDao.selectByOrderId(orderId);

                orderDetail.setOrderContactor(contactor);
                orderDetail.setPassengers(passengers);
            }

        }

        return orderDetail;
    }

	@Override
    public OrderDetailWo selectOriginalOrderById(String orderId) throws Exception {
        if (StringUtils.isNotBlank(orderId)) {
            Order order = orderDao.selectById(orderId);
            if (order != null && StringUtils.isNotBlank(order.getOriginalOrder())) {
                OrderDetailWo orderDetailWo = JSON.parseObject(order.getOriginalOrder(), OrderDetailWo.class);
                return orderDetailWo;
            }
        }

        return null;
    }

	@Override
    public OrderDetailWo selectTempOrderById(String orderId) throws Exception {
        if (StringUtils.isNotBlank(orderId)) {
            Order order = orderDao.selectById(orderId);
            if (order != null && StringUtils.isNotBlank(order.getTempOrder())) {
                OrderDetailWo orderDetailWo = JSON.parseObject(order.getTempOrder(), OrderDetailWo.class);
                return orderDetailWo;
            }
        }

        return null;
    }

	@Override
    public AjaxResponse sendEmailByTravelConfirm(String orderId, String email) throws Exception {
        AjaxResponse ajaxResponse = AjaxResponse.instance("SUCCESS","");
        Order order = orderDao.selectById(orderId);
        if (order == null ||order.getBackState() == null || ZtOrderStatus.CANCELED.getCode().equals(order.getBackState())){
            ajaxResponse.setRes_code("FAILURE");
            ajaxResponse.setRes_msg("订单不存在或订单已取消");
        } else {
        	OrderProduct orderProduct  = orderProductDao.selectByOrderId(orderId);
    		OrderContactor	orderContactor = orderContactorDao.selectContactorByOrderId(orderId);
        	orderContactor.setEmail(email);
        	ProductBookVo pbv = JSONObject.parseObject(orderProduct.getProductSnapshot(), ProductBookVo.class) ;
        	MailEntity mailEntity = OrderConvertor.buildMailEntity(order, orderContactor, orderProduct, pbv.getAttachments());
        	MailSender.send(mailEntity);
        }
        return ajaxResponse;
    }

	@Override
    public Boolean updateTempOrder(OrderDetailWo orderDetailWo, String orderId) throws Exception {
        Map<String, Object> params = Maps.newHashMap();
        String tempOrder = JSONObject.toJSONString(orderDetailWo);
        params.put("tempOrder", tempOrder);
        params.put("orderId", orderId);
        return orderDao.tempOrderUpdate(params);
    }

}
