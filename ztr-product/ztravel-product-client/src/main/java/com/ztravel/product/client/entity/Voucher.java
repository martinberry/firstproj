package com.ztravel.product.client.entity;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.ztravel.common.enums.VoucherStatus;
import com.ztravel.common.enums.VoucherType;

public class Voucher {
	/**
	 * 券ID
	 * */
	private String voucherId;

	/**
	 * 券码
	 * */
	private String voucherCode;

	/**
	 * 活动ID
	 * */
	private String activityId;
	/**
	 * 代金券ID
	 * */
	private String couponId;
	/**
	 * 代金券ID
	 * */
	private String couponCode;

	private String couponItemId ;
	/**
	 * 售价
	 * */
	private long price;

	private VoucherType voucherType ;

	private VoucherStatus status ;

	private String createdBy ;

	private String updatedBy ;

	private DateTime created ;

	private DateTime updated ;

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

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public VoucherType getVoucherType() {
		return voucherType;
	}

	public void setVoucherType(VoucherType voucherType) {
		this.voucherType = voucherType;
	}

	public VoucherStatus getStatus() {
		return status;
	}

	public void setStatus(VoucherStatus status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public DateTime getCreated() {
		return created == null ? null : created.toDateTime(DateTimeZone.forOffsetHours(8));
	}

	public void setCreated(DateTime created) {
		this.created = created;
	}

	public DateTime getUpdated() {
		return updated == null ? null : updated.toDateTime(DateTimeZone.forOffsetHours(8));
	}

	public void setUpdated(DateTime updated) {
		this.updated = updated;
	}

	@Override
	public String toString() {
		return "Voucher [voucherId=" + voucherId + ", voucherCode=" + voucherCode + ", activityId=" + activityId + ", couponId=" + couponId
				+ ", couponCode=" + couponCode + ", price=" + price
				+ ", voucherType=" + voucherType + ", status=" + status
				+ ", createdBy=" + createdBy + ", updatedBy=" + updatedBy
				+ ", created=" + created == null ? null : created.toDateTime(DateTimeZone.forOffsetHours(8)) + ", updated=" + updated == null ? null : updated.toDateTime(DateTimeZone.forOffsetHours(8)) + "]";
	}

	public String getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}

	public String getCouponItemId() {
		return couponItemId;
	}

	public void setCouponItemId(String couponItemId) {
		this.couponItemId = couponItemId;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}



}
