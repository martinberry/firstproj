package com.ztravel.order.timming.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.plexus.util.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.travelzen.framework.aop.TransactionalRetryAnnotation;
import com.travelzen.framework.core.time.DateTimeUtil;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.util.TZBeanUtils;
import com.travelzen.swordfish.thrift.util.JacksonUtil;
import com.ztravel.common.adapter.sms.MobileCaptchaEntity;
import com.ztravel.common.adapter.sms.SmsAdapter;
import com.ztravel.common.enums.MessageTitleType;
import com.ztravel.common.enums.Nature;
import com.ztravel.common.enums.NoticeType;
import com.ztravel.common.enums.OrderOperate;
import com.ztravel.common.enums.ZtOrderStatus;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.common.util.OperatorMessageContentUtil;
import com.ztravel.common.util.SmsContentUtil;
import com.ztravel.common.util.SystemNoticeContentUtil;
import com.ztravel.member.client.service.IBalanceDetailClientService;
import com.ztravel.member.client.service.IMemberClientService;
import com.ztravel.member.client.service.IOperatorMessageClientService;
import com.ztravel.member.client.service.ISystemNoticeClientService;
import com.ztravel.order.client.service.IOrderClientService;
import com.ztravel.order.client.service.IVoucherOrderClientService;
import com.ztravel.order.dao.IOrderDao;
import com.ztravel.order.dao.IOrderProductDao;
import com.ztravel.order.po.Order;
import com.ztravel.order.po.OrderProduct;
import com.ztravel.order.po.VoucherCombineOrder;
import com.ztravel.order.timming.service.OrderTimmingService;
import com.ztravel.payment.service.IAccountService;
import com.ztravel.payment.service.ICouponService;
import com.ztravel.payment.service.IOrderPaymentService;
import com.ztravel.product.client.service.IProductClientService;
import com.ztravel.reuse.order.service.IOrderCommentReuseService;
import com.ztravel.reuse.order.service.IOrderOpenIdReuseService;

@Service
public class OrderTimmingServiceImpl implements OrderTimmingService {

	@Resource
	IOrderDao orderDaoImpl;
	@Resource
	IOrderProductDao OrderProductDaoImpl;
	@Resource
	IMemberClientService memberClientService;
	@Resource
	ISystemNoticeClientService systemNoticeClientServiceImpl;
	@Resource
	IProductClientService productClientService;
	@Resource
	IOrderPaymentService orderPaymentService;
	@Resource
	ICouponService  couponService;
	@Resource
	IAccountService accountService;
	@Resource
	IBalanceDetailClientService balanceDetailClientService;
	@Resource
	IOperatorMessageClientService operatorMessageClientServiceImpl;

	@Resource
	private IVoucherOrderClientService voucherOrderClientService;

	@Resource
	IOrderClientService orderClientServiceImpl;
	
	@Resource
	IOrderOpenIdReuseService orderOpenIdReuseService;

	@Resource
	IOrderCommentReuseService orderCommentReuseService;
	
	private static final Logger LOGGER = RequestIdentityLogger.getLogger(OrderTimmingServiceImpl.class);

	@Override
	public void setAutoTravelling() {
		String bookDate = DateTimeUtil.getTodayStr();
		List<Order> orders = new LinkedList<Order>();
		try {
			LOGGER.debug("开始将{}日的已发放出行通知的订单状态更新为出行中", bookDate);
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("bookDate", DateTimeUtil.convertStringToDate(bookDate).toDate());
		    orders = orderDaoImpl.selectAutoTravelling(params) ;
			int updateCount = 0;
			for(Order order : orders){
				Order o = new Order();
				o.setOrderId(order.getOrderId());
				o.setFrontState(ZtOrderStatus.OUTTING.getCode());
				o.setBackState(ZtOrderStatus.OUTTING.getCode());
				o.setStateChangeHistory(order.getStateChangeHistory()+","+ZtOrderStatus.OUTTING.getCode());
				if(StringUtils.isNotBlank(order.getOperateRecord())){
					JSONObject operateRecord = (JSONObject) JSONObject.parse(order.getOperateRecord());
					operateRecord.put(OrderOperate.ZTMANAGER.getCode(), DateTimeUtil.date10());
					o.setOperateRecord(operateRecord.toString());
				}
				o.setUpdateDate(new Date());
				orderDaoImpl.update(o);
				updateCount++;
				LOGGER.info("订单{}的状态更新为出行中",order.getOrderId());
			}
			LOGGER.info("已将[{}]条订单的状态更新为出行中",updateCount);
		} catch (Exception e) {
			LOGGER.error("自动将订单刷为出行中状态出现异常", e);
		}
	}

	@Override
	public void setAutoCompleted() {
		String backDate = DateTimeUtil.getTodayStr()+" 23:59:59";
		List<Order> orders = new LinkedList<Order>();
		try {
			LOGGER.debug("开始将{}日的出行中订单状态更新为已完成", backDate);
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("backDate", DateTimeUtil.convertStringToDate(backDate,DateTimeUtil.DATE_TIME_PATTERN));
			orders = orderDaoImpl.selectAutoCompleted(params);
			int updateCount = 0;
			for(Order o: orders){
				if(o.getProductNature().equals("visa")||o.getProductNature().equals("unvisa")){
					LOGGER.info("碎片化订单{}不更新为completed状态",o.getOrderNo());			
				}else{
					Order order = new Order();
					order.setOrderId(o.getOrderId());
					order.setFrontState(ZtOrderStatus.COMPLETED.getCode());
					order.setBackState(ZtOrderStatus.COMPLETED.getCode());
					order.setStateChangeHistory(o.getStateChangeHistory()+","+ZtOrderStatus.COMPLETED.getCode());
					if(StringUtils.isNotBlank(o.getOperateRecord())){
						JSONObject operateRecord = (JSONObject) JSONObject.parse(o.getOperateRecord());
						operateRecord.put(OrderOperate.COMPLETED.getCode(), DateTimeUtil.date10());
						order.setOperateRecord(operateRecord.toString());
					}
					order.setUpdateDate(new Date());
					orderDaoImpl.update(order);
					updateCount++;
					LOGGER.info("订单{}的状态更新为已完成",o.getOrderNo());
					try {
						if(orderDaoImpl.countCompleted(o.getCreator()) == 1){//第一次才发放
							String memberStr = memberClientService.getMemberByMid(o.getCreator());
							Map<String,Object> memberMap = JacksonUtil.json2map(memberStr);
							String recommenderId = (String)memberMap.get("recommender");
							if(StringUtils.isNotBlank(recommenderId)){
								String memberId = (String)memberMap.get("id");
								//获取奖励金额
								long bonus = balanceDetailClientService.getBonusByRecommanderAndFriend(recommenderId, memberId);
								//财务为账户添加金额
								accountService.addAmount(recommenderId, bonus);
								//钱包处修改状态
								balanceDetailClientService.setBonusIssueAndFriendTravel(recommenderId, memberId);
								//发送领取金额提醒
								sendRecCompletedNotice(recommenderId);
							}
						}
					} catch (Exception e) {
						LOGGER.error("发送提醒出现异常", e);
						e.printStackTrace();
					}						
				}
		
			}
			LOGGER.info("已将[{}]条订单的状态更新为已完成",updateCount);
		} catch (Exception e) {
			LOGGER.error("自动将订单刷为已完成状态出现异常", e);
		}
	}
	@Override
	public void setAutoNotice(){
		DateTime now = DateTime.now();
		now = now.minusDays(2);
		List<Order> orders = new LinkedList<Order>();
		try {
			LOGGER.debug("开始向{}日的已回程的用户发送评价提醒", now);
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("backDate", new Date(now.getMillis() - now.getMillisOfDay()));
			orders = orderDaoImpl.selectAutoNotice(params);
			int updateCount = 0;
			for(Order order: orders){
				boolean isCommented = orderCommentReuseService.isOrderAlreadyCommented(order.getOrderId());
				if(isCommented){
					continue;
				}
				String memberStr = memberClientService.getMemberByMid(order.getCreator());
				Map<String,Object> memberMap = JacksonUtil.json2map(memberStr);
				String memberId = (String)memberMap.get("id");
				String nickName = (String)memberMap.get("nickName");
				String mobile = (String)memberMap.get("mobile");
				String noticeContent = SystemNoticeContentUtil.getOdContent(3, nickName, order.getOrderId(),order.getProductNature());
				updateCount++;
				systemNoticeClientServiceImpl.add(memberId, NoticeType.ORDERTYPE, noticeContent);
				if(StringUtils.isNotBlank(mobile)){
					MobileCaptchaEntity smsEntity = new MobileCaptchaEntity();
					smsEntity.setMsgType("提醒");
					smsEntity.setMobileNum(mobile);
					smsEntity.setContent(SmsContentUtil.get44NoticeContent());
					SmsAdapter.sendMessage(smsEntity);
				}
				/*微信订单添加消息推送**/
				try {
					String orderOpenId = orderOpenIdReuseService.getOpenIdByOrderId(order.getOrderId());
					if(StringUtils.isNotBlank(orderOpenId)){
						orderClientServiceImpl.sendTravelEndMsg(order.getOrderId());
					}
				} catch (Exception e) {
					LOGGER.error("订单[{}]完成行程消息推送失败", e);
				}
				LOGGER.debug("向用户["+memberId+"]发送评价提醒成功");
			}
			LOGGER.debug("已发送[{}]条评价提醒",updateCount);
		} catch (Exception e) {
			LOGGER.debug("向44h前已回程的用户发送评价提醒出现异常", e);
		}
	}

	private void sendRecCompletedNotice(String recommenderId){
		try {
			systemNoticeClientServiceImpl.add(recommenderId, NoticeType.REMAINTYPE, SystemNoticeContentUtil.getAbContent());
			LOGGER.debug("用户已完成向推荐者发送余额提现提醒成功");
		} catch (Exception e) {
			LOGGER.debug("用户已完成向推荐者发送余额提现提醒出现异常", e);
		}
	}

	@Override
	public void cancelOrder() throws Exception{
		Map<String,Object> map = Maps.newHashMap();
		map.put("frontState", ZtOrderStatus.UNPAY.getCode());
		List<Order> orderList = orderDaoImpl.select(map);
		LOGGER.info("cancel order list ::: {}", orderList);
		for(Order order : orderList){
			long now = new Date().getTime();
			long createDate	= order.getCreateDate().getTime();
			if((now - createDate)/1000>30*60){
				CommonResponse response = new CommonResponse() ;
				LOGGER.info("cancel order ::: {} start...",order.getOrderId() );
				try{
					response = orderClientServiceImpl.cancleOrder(order.getOrderId(), "AUTO") ;
					LOGGER.info("cancel order ::: {} end, response ::: {}",order.getOrderId(), TZBeanUtils.describe(response) );
				}catch(Exception e){
					LOGGER.error("cancel order ::: {} fail...",order.getOrderId()  , e);
				}
			}
		}
	}
	
	@Override
	public void cancelVoucherOrder() throws Exception{
		List<VoucherCombineOrder> voucherCombineOrderList = voucherOrderClientService.selectVoucherCombineOrders4Cancel() ;
		LOGGER.info("cancel voucherCombineOrder list ::: {}", voucherCombineOrderList);
		for(VoucherCombineOrder voucherCombineOrder : voucherCombineOrderList){
			LOGGER.info("cancel voucherCombineOrder ::: {} start...",voucherCombineOrder.getCombineOrderId());
			try{
				boolean result = voucherOrderClientService.cancelVoucherCombineOrder(voucherCombineOrder.getCombineOrderId()) ;
				LOGGER.info("cancel voucherOrder ::: {} end..., result:::{}",voucherCombineOrder.getCombineOrderId(), result);
			}catch(Exception e){
				LOGGER.error("cancel voucherOrder ::: {} fail...",voucherCombineOrder.getCombineOrderId(), e);
			}
		}
	}

	@TransactionalRetryAnnotation
	public CommonResponse paymentCancelOrder(String orderId, String paymentType) throws Exception{
		return orderPaymentService.cancelOrder(orderId,"AUTO", paymentType);
	}

	@Override
	public void sendBackMsgGift() throws Exception {
		DateTime dateLine = DateTimeUtil.addDay(new DateTime(System.currentTimeMillis()), 15);
		List<OrderProduct> ops = new LinkedList<OrderProduct>();
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("bookDate", new Date(dateLine.getMillis()-dateLine.getMillisOfDay()));
		params.put("frontState", ZtOrderStatus.OPCONFIRM.getCode());
		params.put("backState", ZtOrderStatus.OPCONFIRM.getCode());
		ops = OrderProductDaoImpl.selectBookRange(params);
		for(OrderProduct op: ops){
			operatorMessageClientServiceImpl.add(MessageTitleType.GIFTBOX, op.getProviderInfo()/*临时存放了mid*/, op.getProductTitle(), OperatorMessageContentUtil.getOrderUrl(op.getOrderId(),Nature.PACKAGE.name()));
		}
	}

	@Override
	public void sendBackMsgOutNotice() throws Exception {
		DateTime dateLine = DateTimeUtil.addDay(new DateTime(System.currentTimeMillis()), 2);
		List<OrderProduct> ops = new LinkedList<OrderProduct>();
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("bookDate", new Date(dateLine.getMillis()-dateLine.getMillisOfDay()));
		params.put("frontState", ZtOrderStatus.OPCONFIRM.getCode());
		params.put("backState", ZtOrderStatus.GIFTSEND.getCode());
		ops = OrderProductDaoImpl.selectBookRange(params);
		for(OrderProduct op: ops){
			operatorMessageClientServiceImpl.add(MessageTitleType.OUTNOTICE, op.getProviderInfo()/*临时存放了mid*/, op.getProductTitle(), OperatorMessageContentUtil.getOrderUrl(op.getOrderId(),Nature.PACKAGE.name()));
		}

	}

	public static void main(String[] args) {
//		DateTime dateLine = DateTimeUtil.addDay(new DateTime(System.currentTimeMillis()), 2);
//		System.out.println(new Date(dateLine.getMillis()-dateLine.getMillisOfDay()));
		String backDate = DateTimeUtil.getTodayStr()+" 23:59:59";
		System.out.println(DateTimeUtil.convertStringToDate(backDate,DateTimeUtil.DATE_TIME_PATTERN));

	}


}
