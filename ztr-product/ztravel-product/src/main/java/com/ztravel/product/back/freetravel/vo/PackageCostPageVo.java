package com.ztravel.product.back.freetravel.vo;

import java.util.List;

/**
 * @author wanhaofan
 * 打包成本
 * */
public class PackageCostPageVo {

	private String from ;

	private Integer tripNights ;

	private CostFlightVo flight ;

	private List<CostHotelVo> hotels ;

	private String costs ;

	private Boolean isContainFlight ;

	private Boolean isContainHotel ;

	private Boolean isFlightAlreadyHave ;

	private Boolean isHotelAlreadyHave ;

	private CostSupplierVo packageSupplier ;
	private CostSupplierVo shuttleSupplier ;
	private CostSupplierVo wifiSupplier ;
	private CostSupplierVo otherSupplier ;
	private CostSupplierVo zenbookSupplier ;
	
	private Boolean isContainShuttle ;

	private Boolean isContainWifi ;
	
	private Boolean isContainOther ;

	private Boolean isContainZenbook ;
	
	private List<CostSupplierVo> allSuppliers ;

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

	public CostSupplierVo getPackageSupplier() {
		return packageSupplier;
	}

	public void setPackageSupplier(CostSupplierVo packageSupplier) {
		this.packageSupplier = packageSupplier;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getCosts() {
		return costs;
	}

	public void setCosts(String costs) {
		this.costs = costs;
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

}
