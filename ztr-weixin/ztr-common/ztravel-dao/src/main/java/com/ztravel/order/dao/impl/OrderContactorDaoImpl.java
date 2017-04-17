package com.ztravel.order.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.order.dao.IOrderContactorDao;
import com.ztravel.order.po.OrderContactor;
import com.ztravel.order.po.OrderContactorPoMaterial;

@Repository
public class OrderContactorDaoImpl extends BaseDaoImpl<OrderContactor> implements IOrderContactorDao{

	private static final String SELECT_CONTACTOR_BY_ORDER_ID=".selectContactorById";	
	private static final String SELECT_CONTACTOR_PO_BY_ORDER_ID=".selectContactorPoById";
	
	
	@Override
	public OrderContactorPoMaterial selectContactorPoById(String orderId){
		Map params = new HashMap<String, String>();
		params.put("orderId", orderId);
		return session.selectOne(nameSpace + SELECT_CONTACTOR_PO_BY_ORDER_ID, params) ;
	}
	
	
	@Override
	public OrderContactor selectContactorByOrderId(String orderId){
		Map params = new HashMap<String, String>();
		params.put("orderId", orderId);
		OrderContactor orderContactor=session.selectOne(nameSpace + SELECT_CONTACTOR_BY_ORDER_ID, params);
		return orderContactor;
	}
	

}
