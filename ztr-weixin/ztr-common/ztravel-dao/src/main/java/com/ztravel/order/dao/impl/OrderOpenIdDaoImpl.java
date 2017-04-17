package com.ztravel.order.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.order.dao.IOrderOpenIdDao;
import com.ztravel.order.po.OrderOpenId;

@Repository
public class OrderOpenIdDaoImpl extends BaseDaoImpl<OrderOpenId> implements IOrderOpenIdDao {

	private static final String SELECT_OPENID_BY_ORDERID = ".selectOpenIdByOrderId" ;
	private static final String SELECT_ORDER_OPENID_BY_OPENID = ".selectOrderOpenIdByOpenId" ;

	@Override
	public OrderOpenId getOpenIdByOrderId(String orderId) throws SQLException {
		return session.selectOne(nameSpace + SELECT_OPENID_BY_ORDERID, orderId);
	}

	@Override
	public List<OrderOpenId> getOrderListByOpenId(String openId) throws SQLException {
		return session.selectList(nameSpace + SELECT_ORDER_OPENID_BY_OPENID, openId);
	}

}
