package com.ztravel.paygate.web.dto.middlebean;

import com.ztravel.paygate.core.enums.RefundState;

public class RefundResponseConfirmBean {
	private String entityId;
	private String clientId;
	private String partner;
	private String payType;// 备用
	private String refundNum;
	private String orderNum;
	private String traceNum;// 流水号
	private String ackContent;
	private RefundState refundState;
	private long refundAmount;
	// 是否已经被正确处理过
	private boolean isProcessed = false;
	private String refundShareProfits;
	private String unfreezeDetails;

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getAckContent() {
		return ackContent;
	}

	public void setAckContent(String ackContent) {
		this.ackContent = ackContent;
	}

	public boolean isProcessed() {
		return isProcessed;
	}

	public void setProcessed(boolean isProcessed) {
		this.isProcessed = isProcessed;
	}

	public String getRefundNum() {
		return refundNum;
	}

	public void setRefundNum(String refundNum) {
		this.refundNum = refundNum;
	}

	public String getTraceNum() {
		return traceNum;
	}

	public void setTraceNum(String traceNum) {
		this.traceNum = traceNum;
	}

	public RefundState getRefundState() {
		return refundState;
	}

	public void setRefundState(RefundState refundState) {
		this.refundState = refundState;
	}

	public long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(long refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRefundShareProfits() {
		return refundShareProfits;
	}

	public void setRefundShareProfits(String refundShareProfits) {
		this.refundShareProfits = refundShareProfits;
	}

	public String getUnfreezeDetails() {
		return unfreezeDetails;
	}

	public void setUnfreezeDetails(String unfreezeDetails) {
		this.unfreezeDetails = unfreezeDetails;
	}

}
