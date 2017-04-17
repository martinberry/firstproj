package com.ztravel.order.front.vo;

import java.util.List;

import com.ztravel.order.po.OrderPassenger;


public class PassengerRequestWo {

	private List<OrderPassenger> passengers;

	public List<OrderPassenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<OrderPassenger> passengers) {
		this.passengers = passengers;
	}

}
