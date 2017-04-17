package com.ztravel.operator.electronicWallet.entity;

import com.ztravel.common.entity.PaginationEntity;

public class CouponSearchCriteria extends PaginationEntity{

	private String mobile;
	
	private String mid;

	private String useTimeFrom;

	private String useTimeTo;

	private String couponCode;


	public String getUseTimeFrom() {
		return useTimeFrom;
	}

	public void setUseTimeFrom(String useTimeFrom) {
		this.useTimeFrom = useTimeFrom;
	}

	public String getUseTimeTo() {
		return useTimeTo;
	}

	public void setUseTimeTo(String useTimeTo) {
		this.useTimeTo = useTimeTo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

}
