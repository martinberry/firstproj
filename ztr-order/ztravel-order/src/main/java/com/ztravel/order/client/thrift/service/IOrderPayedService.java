package com.ztravel.order.client.thrift.service;

import java.sql.SQLException;

import com.ztravel.common.enums.ProductType;
import com.ztravel.common.order.OrderPaidBean;

public interface IOrderPayedService {

	Boolean updateOrderStatus(OrderPaidBean orderPayedBean) throws Exception;

	Boolean updateOrderToRefundStatus(String orderCode) throws Exception;

	Boolean updateOrderToCancleStatus(String orderCode) throws Exception;
	
	Boolean updateOrderToRefundFailedStatus(String orderCode) throws Exception;
	
	void releaseProductStock(String orderId) throws Exception;

	ProductType orderProductType(String orderCode) throws SQLException ;

}
