package com.ztravel.order.back.vo;

import java.util.List;

import com.ztravel.common.entity.AdditionalProduct;
import com.ztravel.common.entity.PassengerInfo;
import com.ztravel.reuse.product.entity.ProductFlightInfo;
import com.ztravel.reuse.product.entity.ProductHotelInfo;

/**
 * 
 * @author wanhaofan
 *
 */
public class OpConfirmPDFEntity {
	
	private String pName ;
	
	private String orderNo ;
	
	private String orderAmount ;
	
	private String orderDate ;
	
	private String playDate ;
	
	private List<PassengerInfo> passengers ;
	
	private List<ProductFlightInfo> flights ;
	
	private List<ProductHotelInfo> hotels ;
	
	private List<AdditionalProduct> additionalProducts ;
	
	private String feesContain ;
	
	private String feesNotContain ;
	
	private String freeItem ;
	
	private String refundPolicy ;

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getPlayDate() {
		return playDate;
	}

	public void setPlayDate(String playDate) {
		this.playDate = playDate;
	}

	public List<PassengerInfo> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<PassengerInfo> passengers) {
		this.passengers = passengers;
	}

	public List<ProductFlightInfo> getFlights() {
		return flights;
	}

	public void setFlights(List<ProductFlightInfo> flights) {
		this.flights = flights;
	}

	public List<ProductHotelInfo> getHotels() {
		return hotels;
	}

	public void setHotels(List<ProductHotelInfo> hotels) {
		this.hotels = hotels;
	}

	public List<AdditionalProduct> getAdditionalProducts() {
		return additionalProducts;
	}

	public void setAdditionalProducts(List<AdditionalProduct> additionalProducts) {
		this.additionalProducts = additionalProducts;
	}

	public String getFeesContain() {
		return feesContain;
	}

	public void setFeesContain(String feesContain) {
		this.feesContain = feesContain;
	}

	public String getFeesNotContain() {
		return feesNotContain;
	}

	public void setFeesNotContain(String feesNotContain) {
		this.feesNotContain = feesNotContain;
	}

	public String getFreeItem() {
		return freeItem;
	}

	public void setFreeItem(String freeItem) {
		this.freeItem = freeItem;
	}

	public String getRefundPolicy() {
		return refundPolicy;
	}

	public void setRefundPolicy(String refundPolicy) {
		this.refundPolicy = refundPolicy;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	
	
	
}
