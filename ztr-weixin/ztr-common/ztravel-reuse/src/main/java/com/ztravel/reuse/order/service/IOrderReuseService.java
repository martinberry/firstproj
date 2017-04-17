package com.ztravel.reuse.order.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ztravel.order.po.Order;

public interface IOrderReuseService {
	
	Order getOrderById(String orderId) throws SQLException;
	
	Order getOrderByNo(String orderNo) ;
	
	String getCreatorByOrderId(String orderId) throws Exception;
	
	List<Order> getOrderByMemberId(String mid) throws SQLException;
	
	void updateOrder(Order order) throws SQLException;
	
	void updateOrder(String bedPrefer, String orderId) throws SQLException;
	
	void updateOperateRecord(String orderId, String orderOperateCode) throws Exception;
	
	Boolean updateStatus(String frontStatus,String backStatus,String orderId) throws SQLException;
	
	String getOrderIdByOrderNo(String orderId);
	
	Order selectByMap(Map<String, String> params) throws Exception;
}
