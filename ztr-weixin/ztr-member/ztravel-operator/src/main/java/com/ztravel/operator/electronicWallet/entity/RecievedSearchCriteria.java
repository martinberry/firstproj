package com.ztravel.operator.electronicWallet.entity;

import java.util.List;

import com.ztravel.common.entity.PaginationEntity;
import com.ztravel.common.enums.VoucherStatus;
import com.ztravel.common.enums.VoucherType;

public class RecievedSearchCriteria extends PaginationEntity{
	private String voucherCode;
	private String memberId;
	private List<VoucherStatus> statuslist ;
	private String orderId;
	private String ordermemberId;
	private VoucherType voucherType ;
	private String voucherOrderId;
	private String payTimeFrom;
	private String payTimeTo;
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
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrdermemberId() {
		return ordermemberId;
	}
	public void setOrdermemberId(String ordermemberId) {
		this.ordermemberId = ordermemberId;
	}
	public VoucherType getVoucherType() {
		return voucherType;
	}
	public void setVoucherType(VoucherType voucherType) {
		this.voucherType = voucherType;
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
	public List<VoucherStatus> getStatuslist() {
		return statuslist;
	}
	public void setStatuslist(List<VoucherStatus> statuslist) {
		this.statuslist = statuslist;
	}





}
