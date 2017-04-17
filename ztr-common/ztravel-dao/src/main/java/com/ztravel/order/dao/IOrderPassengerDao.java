package com.ztravel.order.dao;

import java.sql.SQLException;
import java.util.List;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.order.po.OrderPassenger;

public interface IOrderPassengerDao extends BaseDao<OrderPassenger>{

	void batchInsert(List<OrderPassenger> passengerList) throws SQLException;

	/**
	 * 根据订单id查询对应的乘客信息
	 * @param orderId
	 * @return
	 */
	public  List<OrderPassenger>  selectByOrderId(String orderId);

}
