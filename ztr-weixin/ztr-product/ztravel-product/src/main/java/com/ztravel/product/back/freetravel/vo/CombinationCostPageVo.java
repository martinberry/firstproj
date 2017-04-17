package com.ztravel.product.back.freetravel.vo;

import java.util.List;

/**
 * @author wanhaofan
 * 打包成本
 * */
public class CombinationCostPageVo {

	private String from ;

	private Integer tripNights ;

	private CostFlightVo flight ;

	private List<CostHotelVo> hotels ;
	
	private CostSupplierVo packageSupplier ;
	private CostSupplierVo shuttleSupplier ;
	private CostSupplierVo wifiSupplier ;
	private CostSupplierVo otherSupplier ;
	private CostSupplierVo zenbookSupplier ;
	
	private Boolean isContainShuttle ;

	private Boolean isContainWifi ;
	
	private Boolean isContainOther ;

	private Boolean isContainZenbook ;

	public Boolean getIsContainShuttle() {
		return isContainShuttle;
	}

	public void setIsContainShuttle(Boolean isContainShuttle) {
		this.isContainShuttle = isContainShuttle;
	}

	public Boolean getIsContainWifi() {
		return isContainWifi;
	}

	public void setIsContainWifi(Boolean isContainWifi) {
		this.isContainWifi = isContainWifi;
	}

	public Boolean getIsContainOther() {
		return isContainOther;
	}

	public void setIsContainOther(Boolean isContainOther) {
		this.isContainOther = isContainOther;
	}

	public Boolean getIsContainZenbook() {
		return isContainZenbook;
	}

	public void setIsContainZenbook(Boolean isContainZenbook) {
		this.isContainZenbook = isContainZenbook;
	}

	public CostSupplierVo getPackageSupplier() {
		return packageSupplier;
	}

	public void setPackageSupplier(CostSupplierVo packageSupplier) {
		this.packageSupplier = packageSupplier;
	}

	public CostSupplierVo getShuttleSupplier() {
		return shuttleSupplier;
	}

	public void setShuttleSupplier(CostSupplierVo shuttleSupplier) {
		this.shuttleSupplier = shuttleSupplier;
	}

	public CostSupplierVo getWifiSupplier() {
		return wifiSupplier;
	}

	public void setWifiSupplier(CostSupplierVo wifiSupplier) {
		this.wifiSupplier = wifiSupplier;
	}

	public CostSupplierVo getOtherSupplier() {
		return otherSupplier;
	}

	public void setOtherSupplier(CostSupplierVo otherSupplier) {
		this.otherSupplier = otherSupplier;
	}

	public CostSupplierVo getZenbookSupplier() {
		return zenbookSupplier;
	}

	public void setZenbookSupplier(CostSupplierVo zenbookSupplier) {
		this.zenbookSupplier = zenbookSupplier;
	}

	private String hotelCosts ;

	private String flightCosts ;

	private Boolean isContainFlight ;

	private Boolean isFlightAlreadyHave ;

	private Boolean isContainHotel ;

	private Boolean isHotelAlreadyHave ;

	private CostSupplierVo flightSupplier ;

	private List<CostSupplierVo> allSuppliers ;

	public String getHotelCosts() {
		return hotelCosts;
	}

	public void setHotelCosts(String hotelCosts) {
		this.hotelCosts = hotelCosts;
	}

	public String getFlightCosts() {
		return flightCosts;
	}

	public void setFlightCosts(String flightCosts) {
		this.flightCosts = flightCosts;
	}

	public CostSupplierVo getFlightSupplier() {
		return flightSupplier;
	}

	public void setFlightSupplier(CostSupplierVo flightSupplier) {
		this.flightSupplier = flightSupplier;
	}

	public CostFlightVo getFlight() {
		return flight;
	}

	public void setFlight(CostFlightVo flight) {
		this.flight = flight;
	}

	public List<CostHotelVo> getHotels() {
		return hotels;
	}

	public void setHotels(List<CostHotelVo> hotels) {
		this.hotels = hotels;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public Boolean getIsContainFlight() {
		return isContainFlight;
	}

	public void setIsContainFlight(Boolean isContainFlight) {
		this.isContainFlight = isContainFlight;
	}

	public Boolean getIsContainHotel() {
		return isContainHotel;
	}

	public void setIsContainHotel(Boolean isContainHotel) {
		this.isContainHotel = isContainHotel;
	}

	public Integer getTripNights() {
		return tripNights;
	}

	public void setTripNights(Integer tripNights) {
		this.tripNights = tripNights;
	}

	public Boolean getIsFlightAlreadyHave() {
		return isFlightAlreadyHave;
	}

	public void setIsFlightAlreadyHave(Boolean isFlightAlreadyHave) {
		this.isFlightAlreadyHave = isFlightAlreadyHave;
	}

	public Boolean getIsHotelAlreadyHave() {
		return isHotelAlreadyHave;
	}

	public void setIsHotelAlreadyHave(Boolean isHotelAlreadyHave) {
		this.isHotelAlreadyHave = isHotelAlreadyHave;
	}

	public List<CostSupplierVo> getAllSuppliers() {
		return allSuppliers;
	}

	public void setAllSuppliers(List<CostSupplierVo> allSuppliers) {
		this.allSuppliers = allSuppliers;
	}

}
