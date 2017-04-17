package com.ztravel.operator.electronicWallet.entity;

import com.ztravel.common.entity.PaginationEntity;
import com.ztravel.common.enums.VoucherStatus;


public class UnRefundedSearchCriteria extends PaginationEntity{
	private String voucherCode;
	private String memberId;
	private String voucherOrderId;
	private String payTimeFrom;
	private String payTimeTo;
	private String prefundTimeFrom;
	private String prefundTimeTo;
	private String couponId;
	public String getPayTimeFrom() {
		return payTimeFrom;
	}
	public void setPayTimeFrom(String payTimeFrom) {
		this.payTimeFrom = payTimeFrom;
	}
	public String getPayTimeTo() {
		return payTimeTo;
	}
	public void setPayTimeTo(String payTimeTo) {
		this.payTimeTo = payTimeTo;
	}
	public String getPrefundTimeFrom() {
		return prefundTimeFrom;
	}
	public void setPrefundTimeFrom(String prefundTimeFrom) {
		this.prefundTimeFrom = prefundTimeFrom;
	}
	public String getPrefundTimeTo() {
		return prefundTimeTo;
	}
	public void setPrefundTimeTo(String prefundTimeTo) {
		this.prefundTimeTo = prefundTimeTo;
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
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}




}
