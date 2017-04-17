package com.ztravel.order.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.order.dao.IOrderLogDao;
import com.ztravel.order.po.OrderLog;

@Repository
public class OrderLogDaoImpl extends BaseDaoImpl<OrderLog> implements IOrderLogDao {

	private static final String SELECT_BY_ORDERID = ".selectByOrderId";
	private static final String SELECT_BY_ORDERID_WITHROW = ".selectByOrderIdWithRow";
	private static final String COUNT_BY_ORDERID = ".countByOrderId";

	@Override
	public List<OrderLog> selectByOrderId(String orderId) {
		return session.selectList(nameSpace + SELECT_BY_ORDERID, orderId);
	}

	@Override
	public List<OrderLog> selectByOrderIdWithRow(String orderId, int offset, int limit) {
		return session.selectList(nameSpace + SELECT_BY_ORDERID_WITHROW, orderId,new RowBounds(offset, limit));
	}

	@Override
	public int countByOrderId(String orderId) {
		return session.selectOne(nameSpace + COUNT_BY_ORDERID, orderId);
	}

}
