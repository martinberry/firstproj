package com.ztravel.order.dao;

import java.util.List;
import java.util.Map;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.order.po.VoucherCombineOrder;
import com.ztravel.order.po.VoucherOrder;

public interface IVoucherOrderDao extends BaseDao<VoucherOrder>{

	public int updateByMap(Map params) ;

	int applyRefundByCouoponItemId(String couponItemId);

	List<VoucherOrder> selectByCombineOrderId(String combineOrderId);

	int updateCombineOrderByMap(Map params);

	VoucherOrder selectForUpdate(String voucherOrderId);

	List<VoucherCombineOrder> selectVoucherCombineOrders(Map params);

	int insertVoucherCombineOrder(VoucherCombineOrder voucherCombineOrder);

    VoucherCombineOrder selectCombineOrderByCombineOrderId(String combineOrderId);

}
