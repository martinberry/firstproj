package com.ztravel.reuse.order.service;

import java.sql.SQLException;
import java.util.List;

import com.ztravel.order.po.OrderPassenger;

public interface IOrderPassengerReuseService {
	
	void updateOrderPassenger(List<OrderPassenger> passengers) throws SQLException;
	
	List<OrderPassenger> getOrderPassenger(String orderId) throws SQLException;
}
