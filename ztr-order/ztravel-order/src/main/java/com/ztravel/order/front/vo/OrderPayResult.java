package com.ztravel.order.front.vo;

public class OrderPayResult {

	private String payAmount;

	private String orderId;

	private String orderStatus;

	private String realOrderId;
	
	private String productNature;

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getRealOrderId() {
		return realOrderId;
	}

	public void setRealOrderId(String realOrderId) {
		this.realOrderId = realOrderId;
	}

	public String getProductNature() {
		return productNature;
	}

	public void setProductNature(String productNature) {
		this.productNature = productNature;
	}

}
