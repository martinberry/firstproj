package com.ztravel.order.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.order.dao.IOrderProductDao;
import com.ztravel.order.po.OrderProduct;


@Repository
public class OrderProductDaoImpl extends BaseDaoImpl<OrderProduct>implements IOrderProductDao {

	private static final String SELECT_BY_ORDER_ID = ".selectByOrderId" ;
	private static final String SELECT_UNION = ".selectUnion" ;
	private static final String SELECT_BOOK_RANGE = ".selectBookRange" ;
	private static final String SELECT_EQ = ".selectEQ" ;
	@Override
	public  OrderProduct  selectByOrderId(String orderId) {
		return session.selectOne(nameSpace + SELECT_BY_ORDER_ID, orderId) ;
	}

	@Override
	public  List<OrderProduct>  selectUnion(Map params) {
		return session.selectList(nameSpace + SELECT_UNION, params);
	}

	@Override
	public List<OrderProduct> selectBookRange(Map params) {
		return session.selectList(nameSpace + SELECT_BOOK_RANGE, params);
	}

	@Override
	public List<OrderProduct> selectEQ(Map params) {
		return session.selectList(nameSpace + SELECT_EQ, params);
	}





}
