package com.ztravel.payment.paygate.model;

/**
 * @author zuoning.shen
 *
 */
public class PayQueryRequestBean extends RequestBean {
	/**
	 * 订单号, required
	 */
	private String orderNum;

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

}
