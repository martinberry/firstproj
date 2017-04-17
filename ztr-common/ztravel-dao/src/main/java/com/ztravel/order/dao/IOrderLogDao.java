package com.ztravel.order.dao;

import java.util.List;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.order.po.OrderLog;

public interface IOrderLogDao extends BaseDao<OrderLog>{
	List<OrderLog> selectByOrderId(String orderId);
	int countByOrderId(String orderId);
	List<OrderLog> selectByOrderIdWithRow(String orderId, int offset, int limit);
}
