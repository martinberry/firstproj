package com.ztravel.reuse.order.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.order.dao.IOrderProductDao;
import com.ztravel.order.po.OrderProduct;
import com.ztravel.reuse.order.service.IOrderProductReuseService;

@Service
public class OrderProductReuseService implements IOrderProductReuseService {
	
	private static final Logger LOGGER = RequestIdentityLogger.getLogger(OrderProductReuseService.class);

	@Resource
	IOrderProductDao orderProductDaoImpl;
	
	@Override
	public OrderProduct getOrderProductByOrderId(String orderId) throws Exception {
		LOGGER.info("微信端查询订单[{}]的订单产品信息", orderId);
		OrderProduct orderProduct = orderProductDaoImpl.selectByOrderId(orderId);
		LOGGER.info("订单[{}]产品信息:[{}]", orderId,TZBeanUtils.describe(orderProduct));
		return orderProduct;
	}
}
