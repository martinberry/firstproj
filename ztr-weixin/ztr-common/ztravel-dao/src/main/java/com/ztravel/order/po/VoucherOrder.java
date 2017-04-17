package com.ztravel.order.po;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.ztravel.common.enums.PaymentType;
import com.ztravel.common.enums.VoucherOrderStatus;

/**
 * 代金券订单
 * @author pengfei.zhao
 *
 */
public class VoucherOrder {
	private String voucherOrderId ;
	private String voucherCode ;
	private String couponId ;
	private String couponCode ;
	private String couponSnapshot ;
	private String orderFrom ;
	private long totalPrice ;
	private long payAmount ;
	private PaymentType payType ;
	private String combineOrderId ;
	private String paySerialNum ;
	private VoucherOrderStatus status ;
	private String createdBy ;
	private String updatedBy ;
	private DateTime created ;
	private DateTime updated ;


	private DateTime payTime;
	private DateTime applyRefundTime;
	private DateTime refundedTime;


	public DateTime getPayTime() {
		return payTime == null ? null : payTime.toDateTime(DateTimeZone.forOffsetHours(8));
	}
	public void setPayTime(DateTime payTime) {
		this.payTime = payTime;
	}
	public DateTime getApplyRefundTime() {
		return applyRefundTime == null ? null : applyRefundTime.toDateTime(DateTimeZone.forOffsetHours(8));
	}
	public void setApplyRefundTime(DateTime applyRefundTime) {
		this.applyRefundTime = applyRefundTime;
	}
	public DateTime getRefundedTime() {
		return refundedTime == null ? refundedTime : refundedTime.toDateTime(DateTimeZone.forOffsetHours(8));
	}

	public void setRefundedTime(DateTime refundedTime) {
		this.refundedTime = refundedTime;
	}

	public String getVoucherOrderId() {
		return voucherOrderId;
	}
	public void setVoucherOrderId(String voucherOrderId) {
		this.voucherOrderId = voucherOrderId;
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
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public String getCouponSnapshot() {
		return couponSnapshot;
	}
	public void setCouponSnapshot(String couponSnapshot) {
		this.couponSnapshot = couponSnapshot;
	}
	public String getOrderFrom() {
		return orderFrom;
	}
	public void setOrderFrom(String orderFrom) {
		this.orderFrom = orderFrom;
	}
	public long getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(long totalPrice) {
		this.totalPrice = totalPrice;
	}
	public long getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(long payAmount) {
		this.payAmount = payAmount;
	}
	public PaymentType getPayType() {
		return payType;
	}
	public void setPayType(PaymentType payType) {
		this.payType = payType;
	}
	public String getPaySerialNum() {
		return paySerialNum;
	}
	public void setPaySerialNum(String paySerialNum) {
		this.paySerialNum = paySerialNum;
	}
	public VoucherOrderStatus getStatus() {
		return status;
	}
	public void setStatus(VoucherOrderStatus status) {
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
		return created == null ? created : created.toDateTime(DateTimeZone.forOffsetHours(8)) ;
	}
	public void setCreated(DateTime created) {
		this.created = created;
	}
	public DateTime getUpdated() {
		return updated == null ? updated : updated.toDateTime(DateTimeZone.forOffsetHours(8)) ;
	}
	public void setUpdated(DateTime updated) {
		this.updated = updated;
	}

	@Override
	public String toString() {
		return "VoucherOrder [voucherOrderId=" + voucherOrderId
				+ ", voucherCode=" + voucherCode + ", couponId=" + couponId
				+ ", orderFrom=" + orderFrom + ", totalPrice=" + totalPrice
				+ ", payAmount=" + payAmount + ", payType=" + payType + ", combineOrderId=" + combineOrderId
				+ ", paySerialNum=" + paySerialNum + ", status=" + status
				+ ", payTime=" + payTime == null ? null : payTime.toDateTime(DateTimeZone.forOffsetHours(8)) + ", applyRefundTime=" + applyRefundTime == null ? null : applyRefundTime.toDateTime(DateTimeZone.forOffsetHours(8))
				+ ", refundedTime=" + refundedTime == null ? null : refundedTime.toDateTime(DateTimeZone.forOffsetHours(8))
				+ ", createdBy=" + createdBy + ", updatedBy=" + updatedBy
				+ ", created=" + created == null ? null : created.toDateTime(DateTimeZone.forOffsetHours(8)) + ", updated=" + updated == null ? null : updated.toDateTime(DateTimeZone.forOffsetHours(8)) + "]";
	}

	public String getCombineOrderId() {
		return combineOrderId;
	}
	public void setCombineOrderId(String combineOrderId) {
		this.combineOrderId = combineOrderId;
	}

}