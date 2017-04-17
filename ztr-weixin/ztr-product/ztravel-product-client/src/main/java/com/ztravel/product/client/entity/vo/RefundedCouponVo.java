package com.ztravel.product.client.entity.vo;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.ztravel.common.enums.PaymentType;

public class RefundedCouponVo {
	private String voucherCode;
	private String voucherOrderId;
	private String memberId;
	private PaymentType payType ;
	private Long payAmount ;
	private DateTime payTime;
	private DateTime refundTime;

	private String payTimeStr;
	private String refundTimeStr;
	private PaymentType repayType;
	private Long repayAmount;

	public PaymentType getRepayType() {
		return repayType;
	}
	public void setRepayType(PaymentType repayType) {
		this.repayType = repayType;
	}
	public Long getRepayAmount() {
		return repayAmount;
	}
	public void setRepayAmount(Long repayAmount) {
		this.repayAmount = repayAmount;
	}


	public String getVoucherCode() {
		return voucherCode;
	}
	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}
	public String getVoucherOrderId() {
		return voucherOrderId;
	}
	public void setVoucherOrderId(String voucherOrderId) {
		this.voucherOrderId = voucherOrderId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public PaymentType getPayType() {
		return payType;
	}
	public void setPayType(PaymentType payType) {
		this.payType = payType;
	}
	public Long getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}
	public DateTime getPayTime() {
		return payTime.toDateTime(DateTimeZone.forOffsetHours(8));
	}
	public void setPayTime(DateTime payTime) {
		this.payTime = payTime;
	}
	public DateTime getRefundTime() {
		return refundTime;
	}
	public void setRefundTime(DateTime refundTime) {
		this.refundTime = refundTime;
	}
	public String getPayTimeStr() {
		return payTimeStr;
	}
	public void setPayTimeStr(String payTimeStr) {
		this.payTimeStr = payTimeStr;
	}
	public String getRefundTimeStr() {
		return refundTimeStr;
	}
	public void setRefundTimeStr(String refundTimeStr) {
		this.refundTimeStr = refundTimeStr;
	}

}
