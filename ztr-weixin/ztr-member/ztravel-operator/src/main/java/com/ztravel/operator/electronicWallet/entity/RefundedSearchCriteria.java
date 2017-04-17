package com.ztravel.operator.electronicWallet.entity;

import com.ztravel.common.entity.PaginationEntity;


public class RefundedSearchCriteria extends PaginationEntity{
	private String voucherCode;
	private String memberId;
	private String voucherOrderId;
	private String payTimeFrom;
	private String payTimeTo;
	private String fundTimeFrom;
	private String fundTimeTo;
	private String couponId;
	public void setPayTimeTo(String payTimeTo) {
		this.payTimeTo = payTimeTo;
	}
	public String getFundTimeFrom() {
		return fundTimeFrom;
	}
	public void setFundTimeFrom(String fundTimeFrom) {
		this.fundTimeFrom = fundTimeFrom;
	}
	public String getFundTimeTo() {
		return fundTimeTo;
	}
	public void setFundTimeTo(String fundTimeTo) {
		this.fundTimeTo = fundTimeTo;
	}


	public String getVoucherCode() {
		return voucherCode;
	}
	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getVoucherOrderId() {
		return voucherOrderId;
	}
	public void setVoucherOrderId(String voucherOrderId) {
		this.voucherOrderId = voucherOrderId;
	}
	public String getPayTimeFrom() {
		return payTimeFrom;
	}
	public void setPayTimeFrom(String payTimeFrom) {
		this.payTimeFrom = payTimeFrom;
	}
	public String getPayTimeTo() {
		return payTimeTo;
	}
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

}
