package com.ztravel.order.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.order.dao.IVoucherOrderDao;
import com.ztravel.order.po.VoucherCombineOrder;
import com.ztravel.order.po.VoucherOrder;


@Repository
public class VoucherOrderDaoImpl extends BaseDaoImpl<VoucherOrder> implements IVoucherOrderDao{

	private static final String UPD_BY_MAP = ".updateByMap" ;
	private static final String APPLY_REFUND_BY_COUPON_ITEM_ID = ".applyRefundByCouoponItemId" ;
	private static final String SELECT_BY_COMBINE_ORDER_ID = ".selectByCombineOrderId" ;
	private static final String UPD_COMBINE_BY_MAP = ".updateCombineByMap" ;
	private static final String SELECT_FOR_UPDATE = ".selectForUpdate" ;
	private static final String SELECT_VOUCHER_COMBINE_ORDERS = ".selectVoucherCombineOrders" ;
	private static final String INSERT_VOUCHER_COMBINE_ORDER = ".insertVoucherCombineOrder";
	private static final String SELECT_COMBINE_ORDER_BY_COMBINE_ORDER_ID = ".selectVoucherCombineOrderByCombineOrderId";

	@Override
	public int updateByMap(Map params) {
		return session.update(nameSpace + UPD_BY_MAP, params);
	}

	@Override
	public int applyRefundByCouoponItemId(String couponItemId) {
		return session.update(nameSpace + APPLY_REFUND_BY_COUPON_ITEM_ID, couponItemId);
	}

	@Override
	public List<VoucherOrder> selectByCombineOrderId(String combineOrderId) {
		return session.selectList(nameSpace + SELECT_BY_COMBINE_ORDER_ID, combineOrderId);
	}

	@Override
	public int updateCombineOrderByMap(Map params){
		return session.update(nameSpace + UPD_COMBINE_BY_MAP, params) ;
	}

	@Override
	public VoucherOrder selectForUpdate(String voucherOrderId){
		return session.selectOne(nameSpace + SELECT_FOR_UPDATE, voucherOrderId) ;
	}

	@Override
	public List<VoucherCombineOrder> selectVoucherCombineOrders(Map params){
		return session.selectList(nameSpace + SELECT_VOUCHER_COMBINE_ORDERS, params) ;
	}

	@Override
	public int insertVoucherCombineOrder(VoucherCombineOrder voucherCombineOrder) {
        return session.insert(nameSpace + INSERT_VOUCHER_COMBINE_ORDER, voucherCombineOrder);
    }

    @Override
    public VoucherCombineOrder selectCombineOrderByCombineOrderId(String combineOrderId) {
        return session.selectOne(nameSpace + SELECT_COMBINE_ORDER_BY_COMBINE_ORDER_ID, combineOrderId) ;
    }

}
