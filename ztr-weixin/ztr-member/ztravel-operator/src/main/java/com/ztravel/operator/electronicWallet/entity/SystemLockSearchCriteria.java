package com.ztravel.operator.electronicWallet.entity;

import com.ztravel.common.entity.PaginationEntity;
import com.ztravel.common.enums.VoucherType;

public class SystemLockSearchCriteria extends PaginationEntity{
	private String voucherCode;

	private VoucherType voucherType;

	private String couponId;

	public VoucherType getVoucherType() {
		return voucherType;
	}
	public void setVoucherType(VoucherType voucherType) {
		this.voucherType = voucherType;
	}
	public String getVoucherCode() {
		return voucherCode;
	}
	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}



}



