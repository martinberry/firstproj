package com.ztravel.order.dao.impl;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.order.dao.IOrderProductStockDao;
import com.ztravel.order.po.OrderProductStock;

@Repository
public class OrderProductStockDaoImpl extends BaseDaoImpl<OrderProductStock> implements IOrderProductStockDao {

	private static final String FOR_UPDATE_BY_ID_AND_BOOK_DATE = ".forUpdateByIdAndBookDate";

	@Override
	public Integer forUpdateByIdAndBookDate(String params) {
		return session.selectOne(nameSpace + FOR_UPDATE_BY_ID_AND_BOOK_DATE,params) == null ? 0 : 1;
	}


}
