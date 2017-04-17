package com.ztravel.order.wx.vo;

import java.util.List;

import com.ztravel.order.po.OrderPassenger;


public class PassengerUpdateVo {

	private List<OrderPassenger> passengers;

	Boolean isDomestic;

	String orderId;

	public List<OrderPassenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<OrderPassenger> passengers) {
		this.passengers = passengers;
	}

	public Boolean getIsDomestic() {
		return isDomestic;
	}

	public void setIsDomestic(Boolean isDomestic) {
		this.isDomestic = isDomestic;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
