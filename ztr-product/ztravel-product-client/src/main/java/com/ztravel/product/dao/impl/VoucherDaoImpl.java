package com.ztravel.product.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.product.client.entity.Voucher;
import com.ztravel.product.client.entity.vo.RecievedCouponVo;
import com.ztravel.product.client.entity.vo.RefundedCouponVo;
import com.ztravel.product.client.entity.vo.UnRefundedCouponVo;
import com.ztravel.product.dao.IVoucherDao;

@Repository
public class VoucherDaoImpl extends BaseDaoImpl<Voucher> implements IVoucherDao{

	private static final String SELECT_BY_CODE = ".selectByCode" ;
	private static final String COUNT_BY_COUPON_ITEM_ID = ".countByCouponItemId" ;
	private static final String COUNT_BUY_AVAILABLE_BY_COUPON_ID = ".countBuyAvailableByCouponId";
    private static final String SELECT_BUY_AVAILABLE_BY_COUPON_ID = ".selectBuyAvailableByCouponId";
    private static final String SELECT_ACTIVITY_AVAILABLE_BY_COUPON_ID = ".selectActivityAvailableByCouponId";
    private static final String SELECT_BUY_AVAILABLE_BY_COUPON_ID_AND_NUM = ".selectBuyAvailableByCouponIdAndNum";
	private static final String UPD_BY_MAP = ".updateByMap" ;
	private static final String UPDATE_ALL=".updateAll";
	private static final String SELECT_RECEIVED=".selectdnrecieved";
	private static final String SELECT_PREFUND=".selectdnprefund";
	private static final String SELECT_REFUND=".selectdnrefund";
	private static final String COUNT_RECEIVED=".countreceived";
	private static final String COUNT_PREFUND=".countprefund";
	private static final String COUNT_REFUND=".countrefund";
	private static final String SELECT_VOUCHER_STOCK_BY_COUPONIDS=".selectVoucherStockByCouponIds";


	@Override
	public Voucher selectByCode(String voucherCode) {
		return session.selectOne(nameSpace + SELECT_BY_CODE, voucherCode);
	}

	@Override
	public int countByCouponItemId(String couponItemId) {
		return session.selectOne(nameSpace + COUNT_BY_COUPON_ITEM_ID, couponItemId) ;
	}

    @Override
    public int countBuyAvailableByCouponId(String couponId) {
        return session.selectOne(nameSpace + COUNT_BUY_AVAILABLE_BY_COUPON_ID, couponId) ;
    }

	@Override
	public int updateByMap(Map params){
		return session.update(nameSpace + UPD_BY_MAP, params) ;
	}

	@Override
	public int updateAll(Map params){
		return session.update(nameSpace + UPDATE_ALL, params) ;
	}

	@Override
	public List<RecievedCouponVo> selectReceived(Map map){
return session.selectList(nameSpace+SELECT_RECEIVED, map);
	}

	@Override
	public List<UnRefundedCouponVo> selectPrefund(Map map){
return session.selectList(nameSpace+SELECT_PREFUND, map);
	}

	@Override
public List<RefundedCouponVo> selectRefund(Map map){
return session.selectList(nameSpace+SELECT_REFUND, map);
	}

	@Override
    public Integer countreceived(Map params) {
        return session.selectOne(nameSpace + COUNT_RECEIVED, params);
    }

	@Override
    public Integer countprefund(Map params) {
        return session.selectOne(nameSpace + COUNT_PREFUND, params);
    }

	@Override
    public Integer countrefund(Map params) {
        return session.selectOne(nameSpace + COUNT_REFUND, params);
	}

    @Override
    public List<Voucher> selectBuyAvailableByCouponId(String couponId) {
        return session.selectList(nameSpace + SELECT_BUY_AVAILABLE_BY_COUPON_ID, couponId) ;
    }

    @Override
    public List<Voucher> selectActivityAvailableByCouponId(String couponId) {
        return session.selectList(nameSpace + SELECT_ACTIVITY_AVAILABLE_BY_COUPON_ID, couponId) ;
    }

    @Override
    public List<Voucher> selectBuyAvailableByCouponIdAndNum(String couponId, Integer num) {
        Map params= new HashMap();
        params.put("couponId", couponId);
        params.put("limit", num);
        return session.selectList(nameSpace + SELECT_BUY_AVAILABLE_BY_COUPON_ID_AND_NUM, params) ;
    }

    @Override
    public List<Map> selectVoucherStockByCouponIds(List<String> couponIds) {
        return session.selectList(nameSpace + SELECT_VOUCHER_STOCK_BY_COUPONIDS, couponIds) ;
    }

}
