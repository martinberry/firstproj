package com.ztravel.order.back.service.impl;


import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
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
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.core.time.DateTimeUtil;
import com.travelzen.framework.core.util.MoneyUtil;
import com.travelzen.framework.logger.core.TZMarkers;
import com.travelzen.framework.redis.client.RedisClient;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.adapter.sms.MobileCaptchaEntity;
import com.ztravel.common.adapter.sms.SmsAdapter;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.enums.PieceType;
import com.ztravel.common.enums.ZtOrderStatus;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.common.util.WebEnv;
import com.ztravel.common.weixin.notice.OpConfirmNotice;
import com.ztravel.member.client.service.IMemberClientService;
import com.ztravel.member.client.service.IOperatorMessageClientService;
import com.ztravel.member.client.service.ISystemNoticeClientService;
import com.ztravel.order.back.convertor.OrderConvertor;
import com.ztravel.order.back.criteria.VisaOrderSearchCriteria;
import com.ztravel.order.back.service.IVisaOrderService;
import com.ztravel.order.back.vo.OrderListVO;
import com.ztravel.order.dao.IOrderContactorDao;
import com.ztravel.order.dao.IOrderDao;
import com.ztravel.order.dao.IOrderMaterialDao;
import com.ztravel.order.dao.IOrderOpenIdDao;
import com.ztravel.order.dao.IOrderOrderProductDao;
import com.ztravel.order.dao.IOrderProductDao;
import com.ztravel.order.po.MaterialContactor;
import com.ztravel.order.po.Order;
import com.ztravel.order.po.OrderOpenId;
import com.ztravel.order.po.OrderPO;
import com.ztravel.payment.service.ICouponService;
import com.ztravel.product.client.service.IProductClientService;
import com.ztravel.reuse.order.service.IOrderLogReuseService;
import com.ztravel.reuse.order.service.IOrderReuseService;

@Service
public class VisaOrderServiceImpl implements IVisaOrderService{
	@Resource 
	private IOrderMaterialDao orderMaterialDao;
	@Resource
	private IOrderDao orderDao;

	@Resource
	private IOrderContactorDao orderContactorDao;

	@Resource
	private IOrderProductDao orderProductDao;
	
	@Resource
	private IOrderOrderProductDao orderOrderProductDao;
	
	@Resource
	private  IProductClientService productClientServiceImpl;

	@Resource
	private IOrderLogReuseService orderLogReuseService;


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
	IOrderReuseService orderReuseService;


	private static final Logger LOGGER=LoggerFactory.getLogger(VisaOrderServiceImpl.class);
	
	private final RedisClient redisClient = RedisClient.getInstance();


	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public AjaxResponse materialSend (MaterialContactor orderContactor,String messageContext){
		AjaxResponse ajaxResponse=AjaxResponse.instance("", "");
		try{
			MaterialContactor contactor=orderMaterialDao.selectContactorByOrderId(orderContactor.getOrderId());
			if(contactor!=null){
				orderMaterialDao.update(contactor);
			}else{
				orderMaterialDao.insert(orderContactor);
			}			
			MobileCaptchaEntity	mobileCapEntity  = buildSmsEntity(orderContactor.getPhone(),messageContext);		
			SmsAdapter.sendMessage(mobileCapEntity);
			orderReuseService.updateStatus(ZtOrderStatus.MATERIALSEND.getCode(),ZtOrderStatus.MATERIALSEND.getCode(),orderContactor.getOrderId());
			orderLogReuseService.save(orderContactor.getOrderId(), redisClient.get(OperatorSidHolder.get()),"材料送回", "");
			ajaxResponse.setRes_code("MaterialSendSUCCESS");		
		}catch(Exception e){
			ajaxResponse.setRes_code("MaterialSendFAIL");
			LOGGER.error(e.getMessage(),e);			
		}
		return ajaxResponse;
	}
   
	MobileCaptchaEntity buildSmsEntity(String mobilPhone,String smsContent){
		MobileCaptchaEntity mobileCapEntity = new MobileCaptchaEntity();
		mobileCapEntity.setMobileNum(mobilPhone);
		mobileCapEntity.setContent(smsContent);
		return mobileCapEntity;
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
	
	
	@Override
	public List<OrderListVO> searchVisaOrder(VisaOrderSearchCriteria criteria)throws Exception{
		Map paramsMap = convertVisaOrderSearchCriteria(criteria);
		paramsMap.put("offset", (criteria.getPageNo() - 1) * criteria.getPageSize());
		paramsMap.put("limit", criteria.getPageSize());
		List<OrderPO> orderList = orderOrderProductDao.selectByVisaOrderId(paramsMap);
		List<OrderListVO> visaorderVoList = OrderConvertor.convertPiecePOList2VOList(orderList);
		return visaorderVoList;
	}
	
	

	  @Override
		public Integer countVisaOrders(VisaOrderSearchCriteria criteria) throws Exception {
			Map<String, Object> paramsMap = convertVisaOrderSearchCriteria(criteria);
			return orderOrderProductDao.countVisa(paramsMap);
		}
	  
	
	private Map<String,Object> convertVisaOrderSearchCriteria(VisaOrderSearchCriteria criteria){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("productNature",PieceType.VISA.name());
		map.put("payStatus", "PAID");
		map.put("paymentType", "Coupon");
		if(StringUtils.isNotBlank(criteria.getOrderNo())){
			map.put("orderNo", criteria.getOrderNo());
		}
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
		if (StringUtils.isNotBlank(criteria.getContactor())) {
			String contactor;
			if (criteria.getContactor().contains("%")) {
				contactor = criteria.getContactor().replace("%", "\\%");
			} else {
				contactor = criteria.getContactor();
			}
			map.put("contactor", "%" + contactor + "%");
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if (StringUtils.isNotBlank(criteria.getPurchaseTimeFrom())) {
				map.put("purchaseDateFrom", format.parse(criteria.getPurchaseTimeFrom() + " 00:00:00"));
			}
			if (StringUtils.isNotBlank(criteria.getPurchaseTimeTo())) {
				map.put("purchaseDateTo", format.parse(criteria.getPurchaseTimeTo() + " 23:59:59"));
			}
		} catch (ParseException e) {
			LOGGER.error(e.getMessage(), e);
		}
		if (StringUtils.isNotBlank(criteria.getStatus())){
			map.put("backState", criteria.getStatus());
		}		
        return map;
	}
}
