package com.ztravel.order.dao;

import java.util.List;
import java.util.Map;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.order.po.CommonOrder;
import com.ztravel.order.po.CommonOrderPo;

public interface ICommonOrderDao extends BaseDao<CommonOrder>{
	public CommonOrder selectByorderVice(String orderNoVice);
	
	int updateByMap(Map<String, Object> parameter) throws Exception;

	CommonOrder select4UpdateByOriginOrderNo(String originOrderNo);
	CommonOrder selectByOriginOrderNo(String orderNo);

	public CommonOrder selectRepairByOriginOrderId(String orderId) throws Exception;
	public List<CommonOrderPo> selectCombineOrder(Map params);
	
}
