package com.ztravel.order.wx.vo;

import java.util.List;

import com.ztravel.reuse.product.entity.ProductFlightInfo;


public class FlightInfoVo {
	/**
	 * 	去程航班
	 * */
	private List<ProductFlightInfo> goFlightList;

	/***
	 * 返程航班
	 * */
	private List<ProductFlightInfo> backFlightList;

	/**
	 * 中间航班
	 * */
	private List<ProductFlightInfo> midlFlightList;

	public List<ProductFlightInfo> getGoFlightList() {
		return goFlightList;
	}

	public void setGoFlightList(List<ProductFlightInfo> goFlightList) {
		this.goFlightList = goFlightList;
	}

	public List<ProductFlightInfo> getBackFlightList() {
		return backFlightList;
	}

	public void setBackFlightList(List<ProductFlightInfo> backFlightList) {
		this.backFlightList = backFlightList;
	}

	public List<ProductFlightInfo> getMidlFlightList() {
		return midlFlightList;
	}

	public void setMidlFlightList(List<ProductFlightInfo> midlFlightList) {
		this.midlFlightList = midlFlightList;
	}
}
