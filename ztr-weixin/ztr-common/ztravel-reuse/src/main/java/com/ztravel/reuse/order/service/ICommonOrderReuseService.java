package com.ztravel.reuse.order.service;

import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.order.po.CommonOrder;
import com.ztravel.order.po.Order;


public interface ICommonOrderReuseService {
	
	CommonOrder getRepairCommonOrderByOrderId(String orderId) throws Exception;
	
	CommonOrder selectByOrderId(String orderId);
	
	CommonResponse createCommonOrder(Order order, long price, String productName) throws Exception;
	
}
