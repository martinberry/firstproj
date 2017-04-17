package com.ztravel.reuse.order.service;

import java.sql.SQLException;

import com.ztravel.order.po.OrderContactor;
import com.ztravel.order.po.OrderContactorPoMaterial;

public interface IOrderContactorReuseService {
	
	OrderContactor getOrderContactor(String orderId) throws SQLException;
	
	void updateOrderContactor(OrderContactor contactor,String memberId) throws Exception;

	
	public OrderContactorPoMaterial getOrderContactorsById(String orderId) throws SQLException;
}
