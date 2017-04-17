package com.ztravel.payment.paygate.model;


/**
 * @author zuoning.shen
 *
 */
public class PayResultBean extends ResponseBean {
	private static final long serialVersionUID = -4313756402634441165L;
	public static String SERVICE_ORDER = "order_pay";
	public static String SERVICE_AGENT = "agent_pay";
	public static String SERVICE_MOBILE = "mobile_pay";
	public static String SERVICE_TELPAY = "telpay";
	public static String SERVICE_POSPAY = "pos_pay";
	private String clientId;// 客户端ID
	private String gateType;// 支付平台
	private String orderNum;// 订单号
	private String payState;// 支付状态,SUCCESS-成功,FAIL-失败
	private String bankPaymentTime;// 银行确认的支付时间:yyyy-MM-dd HH:mm:ss
	private String traceNum;// 流水号
	private long amount;
	private String service;//服务类型，order_pay-订单支付,agent_pay-代扣,mobile_pay-手机支付,telpay-电话支付
	
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

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getPayState() {
		return payState;
	}

	public void setPayState(String payState) {
		this.payState = payState;
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

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}
}
