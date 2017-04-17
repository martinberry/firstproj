package com.ztravel.paygate.web.dto.middlebean;

import java.util.Date;

import com.ztravel.paygate.core.enums.PayState;

public class ResponseConfirmBean {
	private String entityId;
	private String clientId;
	private String partner;
	private boolean fgNotify = true;
	private String payType;
	private String orderNum;
	private String comment;
	private String fgNotifyUrl;
	private String ackContent;
	private PayState payState;
	private Date bankPaymentTime;
	private long amount;
	// 是否已经被正确处理过
	private boolean isProcessed = false;

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public boolean isFgNotify() {
		return fgNotify;
	}

	public void setFgNotify(boolean fgNotify) {
		this.fgNotify = fgNotify;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getFgNotifyUrl() {
		return fgNotifyUrl;
	}

	public void setFgNotifyUrl(String fgNotifyUrl) {
		this.fgNotifyUrl = fgNotifyUrl;
	}

	public String getAckContent() {
		return ackContent;
	}

	public void setAckContent(String ackContent) {
		this.ackContent = ackContent;
	}

	public PayState getPayState() {
		return payState;
	}

	public void setPayState(PayState payState) {
		this.payState = payState;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public boolean isProcessed() {
		return isProcessed;
	}

	public void setProcessed(boolean isProcessed) {
		this.isProcessed = isProcessed;
	}

	public Date getBankPaymentTime() {
		return bankPaymentTime;
	}

	public void setBankPaymentTime(Date bankPaymentTime) {
		this.bankPaymentTime = bankPaymentTime;
	}

}
