package com.ztravel.order.dao;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.order.po.OrderContactor;
import com.ztravel.order.po.OrderContactorPoMaterial;

public interface IOrderContactorDao extends BaseDao<OrderContactor>{

	/**
	 * 根据订单id查询对应的订单联系人信息
	 * @param orderId
	 * @return
	 */
	public OrderContactor selectContactorByOrderId(String orderId);
	public OrderContactorPoMaterial selectContactorPoById(String orderId);

}
