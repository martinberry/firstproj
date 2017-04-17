/**
 *
 */
package com.ztravel.common.payment;

import com.ztravel.common.enums.VoucherType;


/**
 * @author haofan.wan
 *
 */
public class DNCouponGrantBean extends CouponGrantBean{

	private String voucherCode ;

	private final String actualClazz = this.getClass().getName() ;

	private VoucherType voucherType ;

	public String getVoucherCode() {
		return voucherCode;
	}

	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}

	public String getActualClazz() {
		return actualClazz;
	}

	public VoucherType getVoucherType() {
		return voucherType;
	}

	public void setVoucherType(VoucherType voucherType) {
		this.voucherType = voucherType;
	}

}
