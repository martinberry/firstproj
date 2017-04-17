package com.ztravel.order.dao;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.order.po.MaterialContactor;

public interface IOrderMaterialDao extends BaseDao<MaterialContactor>{
	public MaterialContactor selectContactorByOrderId(String orderId);
}
