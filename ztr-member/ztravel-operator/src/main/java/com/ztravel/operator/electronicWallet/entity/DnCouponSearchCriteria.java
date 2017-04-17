package com.ztravel.operator.electronicWallet.entity;

import java.util.List;

import com.ztravel.common.entity.PaginationEntity;
import com.ztravel.common.enums.VoucherStatus;
import com.ztravel.common.enums.VoucherType;

public class DnCouponSearchCriteria extends PaginationEntity{
	private String voucherCode;
	private String memberId;
	private String voucherOrderId;
	private String payTimeFrom;
	private String payTimeTo;
	private String prefundTimeFrom;
	private String prefundTimeTo;
	private String couponId;
	private VoucherType voucherType;
	private String activityId;

	private List<VoucherStatus> statusList ;
	private String orderId;
	private String ordermemberId;

	private String refundTimeFrom;
	private String refundTimeTo;

	private String searchTab;

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
	public VoucherType getVoucherType() {
		return voucherType;
	}
	public void setVoucherType(VoucherType voucherType) {
		this.voucherType = voucherType;
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
	public String getRefundTimeFrom() {
		return refundTimeFrom;
	}
	public void setRefundTimeFrom(String refundTimeFrom) {
		this.refundTimeFrom = refundTimeFrom;
	}
	public String getRefundTimeTo() {
		return refundTimeTo;
	}
	public void setRefundTimeTo(String refundTimeTo) {
		this.refundTimeTo = refundTimeTo;
	}
	public String getSearchTab() {
		return searchTab;
	}
	public void setSearchTab(String searchTab) {
		this.searchTab = searchTab;
	}
	public List<VoucherStatus> getStatusList() {
		return statusList;
	}
	public void setStatusList(List<VoucherStatus> statusList) {
		this.statusList = statusList;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}



}
