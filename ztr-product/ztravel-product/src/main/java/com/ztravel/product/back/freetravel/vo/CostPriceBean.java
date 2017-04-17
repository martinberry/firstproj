package com.ztravel.product.back.freetravel.vo;

public class CostPriceBean {
	private String id ;

	private Double adultPrice ;

	private Double childPrice ;

	private Double[] roomPrice ;

	private String start ;

	private String end ;

	private String weekDays ;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getWeekDays() {
		return weekDays;
	}

	public void setWeekDays(String weekDays) {
		this.weekDays = weekDays;
	}

	public Double[] getRoomPrice() {
		return roomPrice;
	}

	public void setRoomPrice(Double[] roomPrice) {
		this.roomPrice = roomPrice;
	}


}
