package com.ztravel.reuse.order.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ztravel.order.dao.IOrderProductStockDao;
import com.ztravel.order.po.OrderProductStock;
import com.ztravel.reuse.order.service.IOrderProductStockReuseService;

@Service
public class OrderProductStockReuseService implements IOrderProductStockReuseService{
	
	@Resource
	private IOrderProductStockDao orderProductStockDaoImpl;
	
	@Override
	public void insertLock(String lock) {
		orderProductStockDaoImpl.insert(getProductStockByIdAndBookDate(lock));
	}

	public OrderProductStock getProductStockByIdAndBookDate(String lockFlag){
		OrderProductStock productStock = new OrderProductStock();
		productStock.setLockFlag(lockFlag);
		return productStock;
	}
}
