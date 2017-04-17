package com.ztravel.paygate.web.dto.result;


/**
 * 请求支付的返回结果
 * 
 * @author dingguangxian
 * 
 */
public class RequestPayResult extends AbstractResultBean {
	private static final long serialVersionUID = 6076860006418570341L;
	private String clientId;// 客户端ID
	private String gateType;// 支付平台
	private String orderNum;// 订单号
	private String payUrl;// 支付URL
	private String amount;

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

	public String getPayUrl() {
		return payUrl;
	}

	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

}
