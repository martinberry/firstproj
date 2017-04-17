package com.ztravel.product.weixin.wo;

import com.ztravel.product.back.freetravel.enums.SaleStatus;


public class WxDayWo implements Comparable<WxDayWo>{
	private Double adultPrice ;
	private Double childPrice ;
	private Double marketPrice ;

	private String week;

	private String dateStr;

	private String dateWithYear;

    private String year;

    private Integer day;

	private Integer month;

	private SaleStatus saleStatus ;

	private Integer availableStock;

	private Double singleRoomPrice ;

	private Boolean containsPackage;

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


	public Double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}


	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	@Override
	public int compareTo(WxDayWo o) {
		 return this.getDateWithYear().compareTo(o.getDateWithYear());
	}

	public String getDateWithYear() {
		return dateWithYear;
	}

	public void setDateWithYear(String dateWithYear) {
		this.dateWithYear = dateWithYear;
	}

	public SaleStatus getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(SaleStatus saleStatus) {
		this.saleStatus = saleStatus;
	}

	public Integer getAvailableStock() {
		return availableStock;
	}

	public void setAvailableStock(Integer availableStock) {
		this.availableStock = availableStock;
	}

	public Double getSingleRoomPrice() {
		return singleRoomPrice;
	}

	public void setSingleRoomPrice(Double singleRoomPrice) {
		this.singleRoomPrice = singleRoomPrice;
	}

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Boolean getContainsPackage() {
        return containsPackage;
    }

    public void setContainsPackage(Boolean containsPackage) {
        this.containsPackage = containsPackage;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

}
