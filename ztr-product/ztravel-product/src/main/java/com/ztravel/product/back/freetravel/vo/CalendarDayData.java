package com.ztravel.product.back.freetravel.vo;

import com.ztravel.product.back.freetravel.enums.SaleStatus;

/**
 * @author wanhaofan
 * 日历数据VO
 * */
public class CalendarDayData {
	private Integer day ;

	private Integer soldNum ;

	private Integer totalNum ;
	
	private Double adultPrice ;
	
	private Double childPrice ;
	
	private Boolean flightFlag ;

	private Boolean hotelFlag ;

	private SaleStatus saleStatus;

	private Boolean hasSale ;

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getSoldNum() {
		return soldNum;
	}

	public void setSoldNum(Integer soldNum) {
		this.soldNum = soldNum;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Boolean getFlightFlag() {
		return flightFlag;
	}

	public void setFlightFlag(Boolean flightFlag) {
		this.flightFlag = flightFlag;
	}

	public Boolean getHotelFlag() {
		return hotelFlag;
	}

	public void setHotelFlag(Boolean hotelFlag) {
		this.hotelFlag = hotelFlag;
	}

	public Boolean getHasSale() {
		return hasSale;
	}

	public void setHasSale(Boolean hasSale) {
		this.hasSale = hasSale;
	}

	public SaleStatus getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(SaleStatus saleStatus) {
		this.saleStatus = saleStatus;
	}

	public Double getAdultPrice() {
		return adultPrice;
	}

	public void setAdultPrice(Double adultPrice) {
		this.adultPrice = adultPrice;
	}

	public Double getChildPrice() {
		return childPrice;
	}

	public void setChildPrice(Double childPrice) {
		this.childPrice = childPrice;
	}

}
