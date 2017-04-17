package com.ztravel.order.client.thrift.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.core.time.DateTimeUtil;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.enums.MessageTitleType;
import com.ztravel.common.enums.Nature;
import com.ztravel.common.enums.OrderOperate;
import com.ztravel.common.enums.ProductType;
import com.ztravel.common.enums.ZtOrderStatus;
import com.ztravel.common.order.OrderPaidBean;
import com.ztravel.common.util.OperatorMessageContentUtil;
import com.ztravel.member.client.service.IOperatorMessageClientService;
import com.ztravel.order.client.service.ICommonOrderClientService;
import com.ztravel.order.client.thrift.service.IOrderPayedService;
import com.ztravel.order.dao.ICommonOrderDao;
import com.ztravel.order.dao.IOrderDao;
import com.ztravel.order.dao.IOrderProductDao;
import com.ztravel.order.po.CommonOrder;
import com.ztravel.order.po.Order;
import com.ztravel.order.po.OrderProduct;
import com.ztravel.product.client.service.IProductClientService;
import com.ztravel.reuse.order.service.IOrderLogReuseService;
import com.ztravel.reuse.product.entity.ProductBookVo;

@Service
public class OrderPayedServiceImpl implements IOrderPayedService{

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(OrderPayedServiceImpl.class);

	@Resource
	IOrderDao orderDao;

	@Resource
	IOrderProductDao orderProductDao;
	
	@Resource
	IOperatorMessageClientService operatorMessageClientServiceImpl;

	@Resource
	private  IProductClientService productClientServiceImpl;
	
	@Resource
	private ICommonOrderClientService commonOrderClientService ;

	@Resource
	IOrderLogReuseService orderLogReuseService;

	@Resource
	ICommonOrderDao commonOrderDao;

	@Override
	@Transactional(value = "ztravel-txManager",isolation=Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor=RuntimeException.class)
	public  Boolean updateOrderStatus(OrderPaidBean orderPayedBean) throws Exception{
		Boolean result = false;
		LOGGER.info("info of payedBean [{}]",TZBeanUtils.describe(orderPayedBean));
		List<Order> orderList = orderDao.selectByOrderCode(orderPayedBean.getOrderId());


		if(!CollectionUtils.isEmpty(orderList)){
			Order order = orderList.get(0);
			if(!order.getBackState().equals(ZtOrderStatus.UNPAY.name())){//进入第三方支付页,后台取消订单,再进行支付时,需业务人员线下退款
				LOGGER.error("订单{}当前状态为{},非{}状态,不能支付", order.getOrderNo(),order.getBackState(),ZtOrderStatus.UNPAY.getCode());
				return false;
			}
			order.setPaySerialNum(orderPayedBean.getTraceNum());
			order.setPayType(orderPayedBean.getPaymentType().name());
			order.setBackState(ZtOrderStatus.PAYED.getCode());
			order.setFrontState(ZtOrderStatus.PAYED.getCode());
			if(StringUtils.isNotBlank(order.getOperateRecord())){
				JSONObject operateRecord = (JSONObject) JSONObject.parse(order.getOperateRecord());
				operateRecord.put(OrderOperate.PAYSUCCESS.getCode(), DateTimeUtil.date10());
				order.setOperateRecord(operateRecord.toString());
			}
			orderDao.update(order);
			orderLogReuseService.save(order.getOrderId(), order.getCreator(), OrderOperate.PAYSUCCESS.getDesc(), "");
			result = true;
		}else{
			LOGGER.info("订单{}不存在",orderPayedBean.getOrderId());
		}
		return result;
	}

	@Override
	@Transactional(value = "ztravel-txManager",isolation=Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor=RuntimeException.class)
	public Boolean updateOrderToRefundStatus(String orderCode) throws Exception{
		
		Boolean result = false;
		LOGGER.info("开始将订单状态更新为退款中,订单编号[{}]",orderCode);
		List<Order> orderList = orderDao.selectByOrderCode(orderCode);
		if(!CollectionUtils.isEmpty(orderList)){
			Order order = orderList.get(0);
			if(!order.getBackState().equals(ZtOrderStatus.PAYED.name()) && !order.getBackState().equals(ZtOrderStatus.REFUNDFAILED.name())){
				LOGGER.error("订单[{}]当前状态为[{}],非[{}]状态", orderCode,order.getBackState(),ZtOrderStatus.PAYED.getCode());
				return false;
			}
			Map<String,Object> paramsMap = new HashMap<String,Object>();
			paramsMap.put("frontState",ZtOrderStatus.REFUNDING.getCode());
			paramsMap.put("backState",ZtOrderStatus.REFUNDING.getCode());
			paramsMap.put("updateDate", new Date());
			paramsMap.put("operator", "AUTO");
			if(StringUtils.isNotBlank(order.getOperateRecord())){
				JSONObject operateRecord = (JSONObject) JSONObject.parse(order.getOperateRecord());
				operateRecord.put(OrderOperate.REFUNDING.getCode(), DateTimeUtil.date10());
				paramsMap.put("operateRecord",operateRecord.toString());
			}
			paramsMap.put("orderId",orderCode);
//			orderDao.update(order);
			orderDao.refundUpdate(paramsMap);
			orderLogReuseService.save(order.getOrderId(), "AUTO", OrderOperate.REFUNDING.getDesc(), "成功");
			LOGGER.info("订单[{}]已进入退款中状态,等待第三方退款回调",orderCode);
			result = true;
		}else{
			LOGGER.info("订单[{}]不存在",orderCode);
		}
		return result;
	}
	
	@Override
	@Transactional(value = "ztravel-txManager",isolation=Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor=RuntimeException.class)
	public  Boolean updateOrderToCancleStatus(String orderCode) throws Exception{
		boolean result = false;
		LOGGER.info("开始将退款中的订单状态更新为已取消,订单编号[{}]",orderCode);
		List<Order> orderList = orderDao.selectByOrderCode(orderCode);
		if(!CollectionUtils.isEmpty(orderList)){
			Order order = orderList.get(0);
			if(!order.getBackState().equals(ZtOrderStatus.REFUNDFAILED.name()) && !order.getBackState().equals(ZtOrderStatus.UNPAY.name()) && !order.getBackState().equals(ZtOrderStatus.REFUNDING.name())){//只有退款中的状态,才能在回调时更新为已取消状态
				LOGGER.error("订单[{}]当前状态为[{}],非[{}]和{}状态", orderCode,order.getBackState(),ZtOrderStatus.REFUNDING.getCode(),ZtOrderStatus.UNPAY.getCode());
				return false;
			}
			Map<String,Object> paramsMap = new HashMap<String,Object>();
			paramsMap.put("frontState",ZtOrderStatus.CANCELED.getCode());
			paramsMap.put("backState",ZtOrderStatus.CANCELED.getCode());
			paramsMap.put("updateDate", new Date());
			paramsMap.put("operator", "AUTO");
			if(StringUtils.isNotBlank(order.getOperateRecord())){
				JSONObject operateRecord = (JSONObject) JSONObject.parse(order.getOperateRecord());
				operateRecord.put(OrderOperate.REFUNDED.getCode(), DateTimeUtil.date10());
				paramsMap.put("operateRecord",operateRecord.toString());
			}
			paramsMap.put("orderId", orderCode);
			orderDao.refundUpdate(paramsMap);
			if(StringUtils.isEmpty(order.getProductNature()) || order.getProductNature().equals(Nature.PACKAGE.name()) || order.getProductNature().equals(Nature.COMBINATION.name())){
				LOGGER.info("退款流程,订单:[],产品性质:[],开始释放库存",orderCode,order.getProductNature());
				releaseProductStock(order.getOrderId());
				LOGGER.info("退款流程,订单:[],产品性质:[],完成释放库存",orderCode,order.getProductNature());
			}
			LOGGER.info("订单：[]的退款流程已完成",orderCode);
			OrderProduct orderProduct = orderProductDao.selectByOrderId(order.getOrderId());
			//发送后台消息
			operatorMessageClientServiceImpl.add(MessageTitleType.CANCLE, order.getCreator(), orderProduct.getProductTitle(), OperatorMessageContentUtil.getOrderUrl(order.getOrderId(),order.getProductNature()));
			result = true;
		}else{
			LOGGER.info("订单[{}]不存在,寻找commonOrder",orderCode);
			CommonOrder commonOrder = commonOrderDao.selectById(orderCode) ;
			if(commonOrder == null){
				LOGGER.error("commonOrder订单不存在:::{}",orderCode);
			}else{
				result = true ;
			}
		}
		return result;
	}
	

	@Override
	@Transactional(value = "ztravel-txManager",isolation=Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor=RuntimeException.class)
	public Boolean updateOrderToRefundFailedStatus(String orderCode)throws Exception {
		Boolean result = false;
		LOGGER.info("开始将订单状态更新为退款失败,订单编号[{}]",orderCode);
		List<Order> orderList = orderDao.selectByOrderCode(orderCode);
		if(!CollectionUtils.isEmpty(orderList)){
			Order order = orderList.get(0);
			if(!order.getBackState().equals(ZtOrderStatus.REFUNDING.name())){
				LOGGER.error("订单[{}]当前状态为[{}],非[{}]状态", orderCode,order.getBackState(),ZtOrderStatus.REFUNDING.getCode());
				return false;
			}
			Map<String,Object> paramsMap = new HashMap<String,Object>();
			paramsMap.put("backState",ZtOrderStatus.REFUNDFAILED.getCode());
			paramsMap.put("updateDate", new Date());
			paramsMap.put("operator", "AUTO");
			if(StringUtils.isNotBlank(order.getOperateRecord())){
				JSONObject operateRecord = (JSONObject) JSONObject.parse(order.getOperateRecord());
				operateRecord.put(OrderOperate.REFUNDFAILED.getCode(), DateTimeUtil.date10());
				paramsMap.put("operateRecord",operateRecord.toString());
			}
			paramsMap.put("orderId",orderCode);
			orderDao.refundUpdate(paramsMap);
			orderLogReuseService.save(order.getOrderId(), order.getCreator(), OrderOperate.REFUNDFAILED.getDesc(), "");
			LOGGER.info("订单[{}]已更新为退款失败",orderCode);
			result = true;
		}else{
			LOGGER.info("订单[{}]不存在",orderCode);
		}
		return result;
	}

	@Override
	public void releaseProductStock(String orderId) throws Exception{
		OrderProduct orderProduct = orderProductDao.selectByOrderId(orderId);
		ProductBookVo productBook = JSON.parseObject(orderProduct.getProductSnapshot(), ProductBookVo.class);
		Integer usedStock = productBook.getAdultNum() + productBook.getChildNum();
		productClientServiceImpl.updateAndModifyStock(productBook.getProductId(), productBook.getDepartDay(), -usedStock);//释放库存
	}
	
	@Override
	public ProductType orderProductType(String orderCode) throws SQLException {
		List<Order> orderList = orderDao.selectByOrderCode(orderCode);
		if(!CollectionUtils.isEmpty(orderList)){
			return ProductType.TRAVEL ;
		}else{
			return ProductType.VOUCHER ;
		}
	}

}
