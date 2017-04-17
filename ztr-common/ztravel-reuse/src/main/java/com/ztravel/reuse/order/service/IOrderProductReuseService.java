package com.ztravel.reuse.order.service;

import com.ztravel.order.po.OrderProduct;

public interface IOrderProductReuseService {
	
	OrderProduct getOrderProductByOrderId(String orderId) throws Exception;
	
}
