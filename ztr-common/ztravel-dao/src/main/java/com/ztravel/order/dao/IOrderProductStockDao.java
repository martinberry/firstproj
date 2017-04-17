package com.ztravel.order.dao;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.order.po.OrderProductStock;

public interface IOrderProductStockDao extends BaseDao<OrderProductStock>{

	/**
	 * 根据产品ID以及预订日期去查询产品库存锁表记录
	 *@param params : 由产品ID与预订日期组成，格式为 XXXXXX:2015-05-22
	 * */
	Integer forUpdateByIdAndBookDate(String params);

}