package com.ztravel.payment.paygate.model;

/**
 * 
 * @author wanhaofan
 *
 */
public class CommonOrderRefundRequest {
	private String commonOrderId ; 
	private String originOrderId ; 
	private String originOrderNo ; 
	private String operator ;
	private String paymentType ;
	private long refundAmount ;
	
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
	public String getCommonOrderId() {
		return commonOrderId;
	}
	public void setCommonOrderId(String commonOrderId) {
		this.commonOrderId = commonOrderId;
	}
	public String getOriginOrderId() {
		return originOrderId;
	}
	public void setOriginOrderId(String originOrderId) {
		this.originOrderId = originOrderId;
	}
	public String getOriginOrderNo() {
		return originOrderNo;
	}
	public void setOriginOrderNo(String originOrderNo) {
		this.originOrderNo = originOrderNo;
	}
	
	
}
