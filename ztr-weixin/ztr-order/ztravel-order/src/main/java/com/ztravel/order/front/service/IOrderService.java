package com.ztravel.order.front.service;

import java.sql.SQLException;
import java.util.List;

import com.ztravel.order.front.vo.OrderListWo;
import com.ztravel.order.po.Order;
import com.ztravel.reuse.order.entity.OrderDetailWo;

public interface IOrderService {

	/**
	 *查询某个会员mid的某个订单id详情
	 * @param orderId
	 * @param mid
	 * @return
	 * @throws Exception
	 */
	public OrderDetailWo selectOrderById(String orderId, String mid) throws Exception;

	/**
	 *查询订单产品信息
	 * @param orders
	 * @return
	 * @throws SQLException
	 */
	public List<OrderListWo> getProductByOrderId(List<Order> orders) throws SQLException;


	public void setCommonOrderInfo(OrderListWo order) throws Exception;


}
