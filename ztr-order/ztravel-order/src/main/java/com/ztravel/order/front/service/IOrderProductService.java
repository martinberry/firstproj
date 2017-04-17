package com.ztravel.order.front.service;

import java.sql.SQLException;

import com.ztravel.order.po.OrderProduct;


public interface IOrderProductService {

	OrderProduct selectOrderProductByOrderId(String orderId) throws SQLException;

}
