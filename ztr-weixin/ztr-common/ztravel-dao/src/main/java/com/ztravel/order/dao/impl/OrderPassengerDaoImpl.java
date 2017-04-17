package com.ztravel.order.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.order.dao.IOrderPassengerDao;
import com.ztravel.order.po.OrderPassenger;

@Repository
public class OrderPassengerDaoImpl extends  BaseDaoImpl<OrderPassenger> implements IOrderPassengerDao{

	private static final String BATCH_INSERT = ".batchInsert";

	@Override
	public void batchInsert(List<OrderPassenger> passengerList)throws SQLException {
		session.insert(nameSpace + BATCH_INSERT,passengerList);
	}

	@Override
	public  List<OrderPassenger>  selectByOrderId(String orderId) {
		Map params = new HashMap<>();
		params.put("orderId", orderId);
		return session.selectList(nameSpace + ".selectByOrderId", params) ;
	}

}
