package com.ztravel.paygate.web.dto.middlebean;

public class QueryProcessBean {
	private String payId;
	
	private String partner;

	/**
	 * 终端标识
	 */
	private String clientId;

	/**
	 * 订单号
	 */
	private String orderNum;

	/**
	 * 交易金额
	 */
	private Long transAmount;
	/**
	 * 交易日期
	 */
	private String transDate;

	/**
	 * 交易时间
	 */
	private String transTime;

	/**
	 * 流水号
	 */
	private String traceNum;
	/**
	 * success-成功,fail-失败
	 */
	private String payState;
	// 网关类型
	private String gateType;
	// 签名
	private String sign;

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getGateType() {
		return gateType;
	}

	public void setGateType(String gateType) {
		this.gateType = gateType;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public Long getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(Long transAmount) {
		this.transAmount = transAmount;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public String getTraceNum() {
		return traceNum;
	}

	public void setTraceNum(String traceNum) {
		this.traceNum = traceNum;
	}

	public String getPayState() {
		return payState;
	}

	public void setPayState(String payState) {
		this.payState = payState;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

}
