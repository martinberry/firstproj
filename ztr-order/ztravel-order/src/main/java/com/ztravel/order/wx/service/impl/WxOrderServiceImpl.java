package com.ztravel.order.wx.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.core.time.DateTimeUtil;
import com.travelzen.framework.core.util.MoneyUtil;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.enums.AdditionalRule;
import com.ztravel.common.enums.Gender;
import com.ztravel.common.enums.PassengerType;
import com.ztravel.common.enums.ZtOrderStatus;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.order.dao.ICommonOrderDao;
import com.ztravel.order.dao.IOrderCommentDao;
import com.ztravel.order.dao.IOrderContactorDao;
import com.ztravel.order.dao.IOrderDao;
import com.ztravel.order.dao.IOrderPassengerDao;
import com.ztravel.order.dao.IOrderProductDao;
import com.ztravel.order.po.CommonOrder;
import com.ztravel.order.po.Order;
import com.ztravel.order.po.OrderPassenger;
import com.ztravel.order.po.OrderProduct;
import com.ztravel.order.wx.service.IWxOrderService;
import com.ztravel.order.wx.vo.OrderVo;
import com.ztravel.order.wx.vo.PassengerInfoVo;
import com.ztravel.payment.service.ICouponService;
import com.ztravel.product.client.entity.ProductClientEntity;
import com.ztravel.product.client.service.IProductClientService;
import com.ztravel.reuse.order.service.ICommonOrderReuseService;
import com.ztravel.reuse.order.service.IOrderCommentReuseService;
import com.ztravel.reuse.order.service.IOrderProductReuseService;
import com.ztravel.reuse.product.entity.ProductBookVo;

@Service
public class WxOrderServiceImpl implements IWxOrderService {

	@Resource
	IOrderDao orderDaoImpl;

	@Resource
	IOrderProductDao orderProductDaoImpl;

	@Resource
	IOrderCommentDao orderCommentDao;

	@Resource
	IOrderContactorDao orderContactorDao;

	@Resource
	IOrderPassengerDao orderPassengerDao;

	@Resource
	ICouponService couponService;

	@Resource
	ICommonOrderDao commonOrderDao;
	
	@Resource
	IProductClientService productClientServiceImpl;
	
	@Resource
	IOrderCommentReuseService orderCommentReuseService;
	
	@Resource
	ICommonOrderReuseService commonOrderReuseService;
	
	@Resource
	IOrderProductReuseService orderProuctReuseService;

	private static Logger LOGGER = RequestIdentityLogger.getLogger(WxOrderListServiceImpl.class);
	private final String BED_TIP_COUNTRY = "日本"; 


	/**
	 * true:国内;false:国际
	 * */
	public Boolean isDomestic(String snapshot,String orderId) throws Exception{
		if(StringUtils.isNotBlank(snapshot)){
			String domestic = JSON.parseObject(snapshot, ProductBookVo.class).getIsDomestic();
			return domestic.equals("domestic");
		}else{
			throw ZtrBizException.instance("", "订单{"+orderId+"}产品信息不存在");
		}
	}

	public PassengerInfoVo passenger2Vo(OrderPassenger passenger,Boolean isDomestic){
		PassengerInfoVo passengerInfoVo = new PassengerInfoVo();
		passengerInfoVo.setName(passenger.getName());
		passengerInfoVo.setFirstName(passenger.getFirstName());
		passengerInfoVo.setLastName(passenger.getLastName());
		passengerInfoVo.setGender(passenger.getGender());
		passengerInfoVo.setFirstEnName(passenger.getFirstEnName());
		passengerInfoVo.setLastEnName(passenger.getLastEnName());
		passengerInfoVo.setCredentialType(passenger.getCredentialType());
		passengerInfoVo.setPassengerTypeDesc(PassengerType.valueOf(passenger.getPassengerType()).getDesc());
		passengerInfoVo.setGenderDesc(Gender.valueOf(passenger.getGender()).getDesc());
		passengerInfoVo.setBirthday(passenger.getBirthday());//差乘客中英文姓名,待分支合并后修改
		if(!isDomestic){//国际
			passengerInfoVo.setEnName(passenger.getEnName());
			passengerInfoVo.setCountry(passenger.getCountry());
			passengerInfoVo.setCredentialDeadLine(passenger.getCredentialDeadLine());
		}
		return passengerInfoVo;
	}

	public OrderVo order2Vo (Order order,OrderProduct orderProduct){
		OrderVo orderVo = new OrderVo();
		if(null != order){
			//设置订单自身信息
			orderVo.setOrderId(order.getOrderId());
			orderVo.setOrderCode(order.getOrderNo());
			orderVo.setPayment(MoneyUtil.cent2Yuan(order.getPayAmount()));
			orderVo.setStatus(order.getBackState());
			orderVo.setStatusDesc(ZtOrderStatus.getByCode(order.getBackState()).getDesc());
			orderVo.setCreateDate(DateTimeUtil.convertDateToString(DateTimeUtil.DATE_PATTERN,order.getCreateDate()));
			orderVo.setPayAmount(MoneyUtil.cent2Yuan(order.getPayAmount()));
			orderVo.setProductNature(order.getProductNature());
			try{
				setCommonOrderInfo(orderVo);
			}catch(Exception e){
				LOGGER.error("设置补款订单信息失败",e);
			}
			//设置订单产品信息
			try {
				if(null != orderProduct){
					if(StringUtils.isNotBlank(orderProduct.getProductSnapshot())){
						ProductBookVo productbook = JSON.parseObject(orderProduct.getProductSnapshot(), ProductBookVo.class);
						orderVo.setCostPriceName(productbook.getCostPriceName());
						Map<AdditionalRule, String> additionalInfos = productbook.getAdditionalInfos();
						Map<String, String> infos = new HashMap<>();
						if(additionalInfos != null && additionalInfos.keySet() != null){
							Iterator<AdditionalRule> it = additionalInfos.keySet().iterator();
							while(it.hasNext()){
								AdditionalRule rule = it.next();
								infos.put(rule.toString(), additionalInfos.get(rule));
								}
						}
						orderVo.setAdditionalInfos(infos);
					}
					orderVo.setIsComment(isOrderCanComment(order.getOrderId(),DateTimeUtil.convertDateToString(DateTimeUtil.DATE_PATTERN,orderProduct.getBackDate())));
					orderVo.setProductName(orderProduct.getProductTitle());
					orderVo.setDepartDate(DateTimeUtil.convertDateToString(DateTimeUtil.DATE_PATTERN,orderProduct.getBookDate()));
				}
			} catch (Exception e) {
				LOGGER.error("判断订单[{}]是否可评价时出现异常:", order.getOrderId(),e);
			}
			//设置订单操作进度信息
			orderVo.setOrderOperate(convertOrderOperate(order.getOperateRecord(),orderVo.getIsComment(),order.getBackState()));
			return orderVo;
		}
		return null;
	}


	private void setCommonOrderInfo(OrderVo orderVo) throws Exception{
		String orderId = orderVo.getOrderId();
		if(StringUtils.isNotEmpty(orderId)){
			CommonOrder commonOrder = commonOrderReuseService.getRepairCommonOrderByOrderId(orderId);
			if(commonOrder != null){
				orderVo.setCommonOrderId(commonOrder.getOrderId());
				orderVo.setCommonOrderPrice(MoneyUtil.cent2Yuan(commonOrder.getPrice()));
				if(commonOrder.getStatus() != null){
					orderVo.setCommonOrderStatus(commonOrder.getStatus().toString());
				}
			}
		}
	}


	private Map<String,String> convertOrderOperate(String operateLog,Boolean iscomment,String backState){
		Map<String,String> orderOperate = new HashMap<String,String>();
		if(StringUtils.isNotBlank(operateLog)){
			JSONObject record = (JSONObject) JSONObject.parse(operateLog);
			String passKey="",nowKey="",futureKey="";
			String passKeyDesc="",nowKeyDesc="",futureKeyDesc="";
			Integer currentIndex = 0;
			for(String key : record.keySet()){
				Integer index = 0;
				if(StringUtils.isNotBlank(record.getString(key))){
					switch (key) {
					case "PAY":
						index = 1;
						passKey = record.getString("CREATE");nowKey = record.getString("PAY");futureKey = "";passKeyDesc = "填写订单";nowKeyDesc="支付订单";futureKeyDesc="支付成功";
						break;
					case "PAYSUCCESS":
						index = 2;
						passKey = record.getString("PAY");nowKey = record.getString("PAYSUCCESS");futureKey = "";passKeyDesc = "支付订单";
						if(!backState.equals(ZtOrderStatus.UNPAY.name()) && !backState.equals(ZtOrderStatus.PAYED.name()) && !backState.equals(ZtOrderStatus.CANCELED.name()) && !backState.equals(ZtOrderStatus.REFUNDING.name()) && !backState.equals(ZtOrderStatus.REFUNDFAILED.name())){
							nowKeyDesc = "支付成功,已确认";
							orderOperate.put("confirmed", "true");
						}else if(backState.equals(ZtOrderStatus.PAYED.name()) || backState.equals(ZtOrderStatus.CANCELED.name()) || backState.equals(ZtOrderStatus.REFUNDING.name()) || backState.equals(ZtOrderStatus.REFUNDFAILED.name())){
							nowKeyDesc="支付成功";
						}
						futureKeyDesc="寄送行程手册";
						break;
					case "GIFTBOX":
						index = 3;
						passKey = record.getString("PAYSUCCESS");nowKey = record.getString("GIFTBOX");futureKey = "";passKeyDesc = "支付成功";nowKeyDesc="寄送行程手册";futureKeyDesc="出行通知";
						break;
					case "OUTNOTICE":
						index = 4;
						passKey = record.getString("GIFTBOX");nowKey = record.getString("OUTNOTICE");futureKey = "";passKeyDesc = "寄送行程手册";nowKeyDesc="出行通知";futureKeyDesc="出行中";
						break;
					case "ZTMANAGER":
						index = 5;
						passKey = record.getString("OUTNOTICE");nowKey = record.getString("ZTMANAGER");futureKey = "";passKeyDesc = "出行通知";nowKeyDesc="出行中";futureKeyDesc="评价";
						break;
					case "COMPLETED":
						index = 6;
						passKey = record.getString("ZTMANAGER");nowKey = record.getString("COMPLETED");nowKeyDesc = "出行中";passKeyDesc = "出行中";futureKey = "";
						futureKeyDesc = "";
						nowKey = record.getString("COMPLETED");
						nowKeyDesc = "评价";
						break;
					case "EVALATE":
						index = 7;
						passKey = record.getString("ZTMANAGER");nowKey = record.getString("EVALATE");futureKey = "";nowKeyDesc = "评价";passKeyDesc = "出行中";
						futureKeyDesc="";
						break;
					default:
						break;
					}
					if(index > currentIndex){
						orderOperate.put("passKeyDesc",passKeyDesc);
						orderOperate.put("nowKeyDesc", nowKeyDesc);
						orderOperate.put("futureKeyDesc",futureKeyDesc );
						orderOperate.put("nowKey", nowKey);
						orderOperate.put("passKey", passKey);
						orderOperate.put("futureKey",futureKey );
						currentIndex = index;
					}
					orderOperate.put(key, record.get(key).toString());
				}
			}
		}
		return orderOperate;
	}

	@Override
	public Boolean isOrderCanComment(String orderId, String strBackDate)throws Exception {
		// 是否已评价
		Boolean isCommented = orderCommentReuseService.isOrderAlreadyCommented(orderId);
		// 是否在订单完成的44小时以后
		Boolean hasPass44Hours;
		DateTime backDate = DateTimeUtil.getEndDate(strBackDate); // 返程当天24:00,订单状态改为已完成
		DateTime commentOpenTime = backDate.plusHours(44); // 评价入口开放时间为返程日期后第二天20:00
		if (Minutes.minutesBetween(commentOpenTime, DateTime.now())
				.getMinutes() >= 0)
			hasPass44Hours = true;
		else
			hasPass44Hours = false;
		return hasPass44Hours && !isCommented;
	}


	@Override
	public Boolean isOrderCanEdit(Date departDay,String orderStatus)throws Exception {
		if(orderStatus.equals(ZtOrderStatus.CANCELED.name()) || orderStatus.equals(ZtOrderStatus.REFUNDFAILED.name()) || orderStatus.equals(ZtOrderStatus.REFUNDING.name())){
			return false;
		}
		DateTime now = new DateTime() ;
		long interval = (departDay.getTime() - now.getMillis())/(3600 * 24 * 1000);
		return (interval >= 15) ;
	}



	@Override
	public List<Order> getOrdersByMid(Map<String,Object> params) throws Exception {
		List<Order> orderList = null;
		LOGGER.info("开始查询微信端订单列表,查询参数[{}]",TZBeanUtils.describe(params));
		if(!CollectionUtils.isEmpty(params)){
			orderList = orderDaoImpl.selectByPage(params);
		}
		LOGGER.info("查询微信端订单结束,查询结果[{}]",TZBeanUtils.describe(orderList));
		return orderList;
	}

	@Override
	public Integer getOrderCount(Map<String,String> params) throws Exception{
		LOGGER.info("查询订单总数,查询参数[]",TZBeanUtils.describe(params));
		Integer totalCount = orderDaoImpl.countOrder(params);
		return totalCount;
	}

	@Override
	public Integer getUnCommentOrderCount(Map<String,String> params) throws Exception{
		LOGGER.info("查询订单总数,查询参数[]",TZBeanUtils.describe(params));
		Integer unRecomment = 0;
		List<Order> orderList = orderDaoImpl.select(params);
		if(!CollectionUtils.isEmpty(orderList)){
			for(Order order : orderList){
				OrderProduct orderProduct = orderProductDaoImpl.selectByOrderId(order.getOrderId());
				if(null == orderProduct){
					continue;
				}
				Boolean isComment = isOrderCanComment(order.getOrderId(), DateTimeUtil.date10(orderProduct.getBackDate()));
				if(isComment && order.getBackState().equals(ZtOrderStatus.COMPLETED.name())){
					unRecomment++;
				}
			}
		}
		return unRecomment;
	}
	
	@Override
	public Boolean isBedTip(String productId){
		Boolean isBedTips = false;
		try {
			ProductClientEntity entity = productClientServiceImpl.getProductById(productId);
			if(!CollectionUtils.isEmpty(entity.getToCountry())  && entity.getToCountry().contains(BED_TIP_COUNTRY)){
				isBedTips = true;
			}
		} catch (Exception e) {
			LOGGER.info("查询产品[]错误,错误信息：[]",TZBeanUtils.describe(productId),e);
			isBedTips = false;
		}
		return isBedTips;
	}

	@Override
	public void updateOrderIsToPay(boolean isToPay, String orderId) {
		Order order = new Order();
		order.setOrderId(orderId);
		order.setIsToPay(isToPay);

		orderDaoImpl.update(order);
	}






}
