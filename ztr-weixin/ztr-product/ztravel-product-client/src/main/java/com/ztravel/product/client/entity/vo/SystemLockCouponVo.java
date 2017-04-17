package com.ztravel.product.client.entity.vo;

import com.ztravel.common.enums.VoucherType;

public class SystemLockCouponVo {
	private String voucherCode;
	private VoucherType voucherType ;
	public String getVoucherCode() {
		return voucherCode;
	}
	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}
	public VoucherType getVoucherType() {
		return voucherType;
	}
	public void setVoucherType(VoucherType voucherType) {
		this.voucherType = voucherType;
	}


}
