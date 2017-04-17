package com.ztravel.order.po;

public class OrderOpenId {

	/**
	 * 订单Id
	 * */
	private String orderId;

	/**
	 * 微信端用户openId
	 * */
	private String openId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

}
