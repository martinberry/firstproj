package com.ztravel.payment.paygate.model;

public class VoucherOrderRefundRequest {
	private String voucherOrderId ; 
	private String combineVoucherOrderId ; 
	private String operator ;
	private String paymentType ;
	private long refundAmount ;
	
	public String getVoucherOrderId() {
		return voucherOrderId;
	}
	public void setVoucherOrderId(String voucherOrderId) {
		this.voucherOrderId = voucherOrderId;
	}
	public String getCombineVoucherOrderId() {
		return combineVoucherOrderId;
	}
	public void setCombineVoucherOrderId(String combineVoucherOrderId) {
		this.combineVoucherOrderId = combineVoucherOrderId;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public long getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(long refundAmount) {
		this.refundAmount = refundAmount;
	}
	
	
}
