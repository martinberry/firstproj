package com.ztravel.order.dao;

import java.sql.SQLException;
import java.util.List;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.order.po.OrderOpenId;

public interface IOrderOpenIdDao extends BaseDao<OrderOpenId>{

	OrderOpenId getOpenIdByOrderId(String orderId) throws SQLException;

	List<OrderOpenId> getOrderListByOpenId(String openId) throws SQLException;

}
