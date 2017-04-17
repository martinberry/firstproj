package com.ztravel.reuse.order.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ztravel.order.dao.IOrderPassengerDao;
import com.ztravel.order.po.OrderPassenger;
import com.ztravel.reuse.order.service.IOrderPassengerReuseService;


@Service
public class OrderPassengerReuseService implements IOrderPassengerReuseService {

	@Resource
	IOrderPassengerDao orderPassengerDaoImpl;
	
	@Override
	public void updateOrderPassenger(List<OrderPassenger> passengers) throws SQLException{
		if(passengers != null){
			for(OrderPassenger passenger : passengers){
				orderPassengerDaoImpl.update(passenger);
			}
		}

	}

	@Override
	public List<OrderPassenger> getOrderPassenger(String orderId) throws SQLException{
		return orderPassengerDaoImpl.selectByOrderId(orderId);
	}

}
