package com.ztravel.product.client.service;

import java.util.List;
import java.util.Map;

import com.ztravel.common.enums.VoucherStatus;
import com.ztravel.common.payment.DNCouponGrantBean;
import com.ztravel.product.client.entity.Voucher;


public interface IVoucherClientService {


	/**
	 * 根据兑换码ID获取代金券
	 * @param voucherId
	 * @return
	 * @throws Exception
	 */
	public Voucher getVoucherById(String voucherId) throws Exception;

	/**
	 * 根据兑换码获取代金券
	 * @param voucherCode
	 * @return
	 * @throws Exception
	 */
	public Voucher getVoucherByCode(String voucherCode) throws Exception;

	int countByCouponItemId(String couponItemId) ;

	boolean updateVoucher4Share(String newCouponItemId, String couponItemId) ;

	boolean updateVoucher4Grant(String couponItemId, DNCouponGrantBean bean) ;

	public List<Voucher> getVoucherListByParams(Map<String, Object> params) throws Exception;

	boolean updateVoucher4ApplyRefund(String couponItemId) ;

	boolean updateVoucher4Paid(String voucherCode) ;

	boolean updateVoucher4Cancel(String couponItemId) ;

	boolean updateVoucher4VoucherOrderExpired(String voucherCode);

	boolean updateVoucher4BackDoor(List<String> newCodes, String couponId)
			throws Exception;

	boolean updateVoucher4TravelPaid(String couponItemId);

	int countByMap(Map<String, Object> map) throws Exception;

	Voucher getVoucherByCouponItemId(String couponItemId) ;

    boolean updateVoucher4ApplyLock(List<String> voucherIdList) throws Exception;
    /**
     * 根据兑换码ID获取代金券
     * @param voucherId
     * @return
     * @throws Exception
     */
    Integer countBuyAvailableByCouponId(String couponId);

    List<Voucher> selectBuyAvailableByCouponId(String couponId);

    List<Voucher> selectActivityAvailableByCouponId(String couponId);

    List<Voucher> selectBuyAvailableByCouponIdAndNum(String couponId, Integer num);

    void selectVoucherLockForUpdateByCouponId(String couponId);

    boolean updateVoucherStatus(String voucherCode, VoucherStatus voucherStatus) throws Exception;

    List<Map> selectVoucherStockByCouponIds(List<String> couponIds);

}
