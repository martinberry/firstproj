package com.ztravel.order.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.order.dao.IOrderMaterialDao;
import com.ztravel.order.po.MaterialContactor;


@Repository
public class OrderMaterialDaoImpl extends BaseDaoImpl <MaterialContactor>implements IOrderMaterialDao{
	private static final String SELECT_BY_ORDER_ID=".selectByOrderId";
	@Override
	public MaterialContactor selectContactorByOrderId(String orderId){
		Map map=new HashMap();
		map.put("orderId", orderId);
		return session.selectOne(nameSpace + SELECT_BY_ORDER_ID,map);
	}

}
