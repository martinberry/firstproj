package com.ztravel.reuse.order.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.core.time.DateTimeUtil;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.constants.OrderConstans;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.order.dao.IOrderDao;
import com.ztravel.order.po.Order;
import com.ztravel.reuse.order.service.IOrderReuseService;


@Service
public class OrderReuseService implements IOrderReuseService {
	
	private static Logger LOGGER = RequestIdentityLogger.getLogger(OrderReuseService.class);
	
	@Resource
	IOrderDao orderDaoImpl;


	@Override
	public Order getOrderById(String orderId) throws SQLException{
		return orderDaoImpl.selectById(orderId);
	}


	@Override
	public String getCreatorByOrderId(String orderId) throws Exception {
		Order order = orderDaoImpl.selectById(orderId);
		if( order != null ){
			return order.getCreator();
		}else{
			LOGGER.error("订单" + orderId + "不存在");
			throw ZtrBizException.instance(OrderConstans.ORDER_ORDER_NOT_EXIST_CODE, OrderConstans.ORDER_ORDER_NOT_EXIST_MSG);
		}
	}

	@Override
	public List<Order> getOrderByMemberId(String mid) throws SQLException{

		List<Order> orders = new ArrayList<>();

		if(StringUtils.isNotBlank(mid)){
			orders = orderDaoImpl.selectByCreator(mid);
		}

		return orders;

	}


	@Override
	public void updateOrder(Order order) throws SQLException {
		orderDaoImpl.update(order);
	}


	@Override
	public void updateOrder(String bedPrefer, String orderId) throws SQLException{

		Order order = new Order();
		order.setOrderId(orderId);
		order.setBedPrefer(bedPrefer);

		orderDaoImpl.update(order);

	}


	@Override
	public Boolean updateStatus(String frontStatus, String backStatus,String orderId) throws SQLException {
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("orderId", orderId);
		paramMap.put("frontState", frontStatus);
		paramMap.put("backState", backStatus);
		return orderDaoImpl.updateStatus(paramMap);
	}
	
	@Override
	public void updateOperateRecord(String orderId, String orderOperateCode) throws Exception {
		Order order = orderDaoImpl.selectById(orderId);
		if( StringUtils.isNotBlank(order.getOperateRecord()) ){
			JSONObject operateRecord = (JSONObject) JSONObject.parse(order.getOperateRecord());
			operateRecord.put(orderOperateCode, DateTimeUtil.date10());
			order.setOperateRecord(operateRecord.toString());
		}
		Boolean updateResult = orderDaoImpl.updateOrder(order);
		if( !updateResult ){
			LOGGER.debug("修改订单operate_record失败,订单id: {}", orderId);
		}
	}


	@Override
	public Order getOrderByNo(String orderNo) {
		List<Order> orders = null;
		try {
			orders = orderDaoImpl.selectByOrderCode(orderNo);
		} catch (SQLException e) {
			LOGGER.error("getOrderByNo:::{}", orderNo, e);
		}
		if(orders!=null && orders.size()>0){
			return orders.get(0) ;
		}else{
			return null ;
		}
	}
	
	@Override
	public String getOrderIdByOrderNo(String orderNum)  {
		String orderId="";
		try{
			Order order = getOrderByNo(orderNum);
			if(null!=order){
				orderId = order.getOrderId();
			}
		}catch(Exception e){
			LOGGER.debug("更新订单编号为{}订单操作信息失败:" +e.getMessage(),orderNum);
		}
		return orderId;
	}


	@Override
	public Order selectByMap(Map<String, String> params) throws Exception {
		List<Order> orderList = orderDaoImpl.select(params);
		LOGGER.info("开始查询微信端订单详情,参数[{}]",TZBeanUtils.describe(params));
		if(!CollectionUtils.isEmpty(orderList)){
			LOGGER.info("查询微信端订单详情结束");
			return orderList.get(0);
		}
		LOGGER.info("订单不存在!!!");
		return null;
	}
	
	
}
