package com.ztravel.order.front.service.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ztravel.order.dao.IOrderProductDao;
import com.ztravel.order.front.service.IOrderProductService;
import com.ztravel.order.po.OrderProduct;

@Service
public class OrderProductServiceImpl implements IOrderProductService {

	@Resource
	IOrderProductDao orderProductDao;

	@Override
	public OrderProduct selectOrderProductByOrderId(String orderId) throws SQLException{
		OrderProduct orderProduct = new OrderProduct() ;
//		if(!StringUtils.isEmpty(orderId)){
//			List<OrderProduct>  orderProductList= orderProductDao.selectByOrderId(orderId);
//			if(null!=orderProductList && orderProductList.size()>0){
//				productOrder=orderProductList.get(0);
//			}
//		}
		orderProduct=orderProductDao.selectByOrderId(orderId);
		return orderProduct;
	}

}
