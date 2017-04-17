package com.ztravel.order.dao;

import java.util.List;
import java.util.Map;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.order.po.OrderProduct;

public interface IOrderProductDao extends BaseDao<OrderProduct>{

	/**
	 * 根据订单id查询订单产品信息
	 * @param orderId
	 * @return
	 */
	OrderProduct  selectByOrderId(String orderId);


	List<OrderProduct> selectEQ(Map params);

	/**
	 *根据参数联合查询订单产品信息
	 * @param params
	 * @return
	 */
	List<OrderProduct> selectUnion(Map params);
	/**
	 *
	 * @param params{bookDate,frontState,backState}
	 * @return
	 */
	List<OrderProduct> selectBookRange(Map params);

}
