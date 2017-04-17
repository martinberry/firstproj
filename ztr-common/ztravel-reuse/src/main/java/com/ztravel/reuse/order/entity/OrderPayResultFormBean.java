package com.ztravel.reuse.order.entity;



public class OrderPayResultFormBean {
	/**
	 * 客户端ID
	 */
	private	 String	clientId;
	/**
	 * 支付平台
	 */
	private 	String	gateType;
	/**
	 * 支付平台
	 */
	private	String	retCode;
	/**
	 * 支付平台
	 */
	private	String	retMsg;
	/**
	 * 订单号
	 */
	private String orderNum;//
	/**
	 * 支付平台
	 */
	private String payState;
	/**
	 *  银行确认的支付时间yyyy-MM-dd HH:mm:ss
	 */
	private String bankPaymentTime;
	/**
	 * 流水号
	 */
	private String traceNum;
	/**
	 * 支付金额
	 */
	private long amount;

	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getGateType() {
		return gateType;
	}
	public void setGateType(String gateType) {
		this.gateType = gateType;
	}
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public String getRetMsg() {
		return retMsg;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getBankPaymentTime() {
		return bankPaymentTime;
	}
	public void setBankPaymentTime(String bankPaymentTime) {
		this.bankPaymentTime = bankPaymentTime;
	}
	public String getTraceNum() {
		return traceNum;
	}
	public void setTraceNum(String traceNum) {
		this.traceNum = traceNum;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public String getPayState() {
		return payState;
	}
	public void setPayState(String payState) {
		this.payState = payState;
	}

}
