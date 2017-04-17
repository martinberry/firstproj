package com.ztravel.product.dao;

import java.util.List;
import java.util.Map;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.product.client.entity.Voucher;
import com.ztravel.product.client.entity.vo.RecievedCouponVo;
import com.ztravel.product.client.entity.vo.RefundedCouponVo;
import com.ztravel.product.client.entity.vo.UnRefundedCouponVo;

public interface IVoucherDao extends BaseDao<Voucher>{

	Voucher selectByCode(String voucherCode) ;

	int countByCouponItemId(String couponItemId) ;

	int countBuyAvailableByCouponId(String couponId);

    List<Voucher> selectBuyAvailableByCouponId(String couponId);

    List<Voucher> selectBuyAvailableByCouponIdAndNum(String couponId, Integer num);

	int updateByMap(Map params) ;

	 int updateAll(Map params);

     List<RecievedCouponVo> selectReceived(Map map);

	 List<UnRefundedCouponVo> selectPrefund(Map map);

	 List<RefundedCouponVo> selectRefund(Map map);

	Integer countreceived(Map params);

	Integer countprefund(Map params);

	Integer countrefund(Map params);

	List<Map> selectVoucherStockByCouponIds(List<String> couponIds);

	List<Voucher> selectActivityAvailableByCouponId(String couponId);

}
