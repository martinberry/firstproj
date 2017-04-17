package com.ztravel.reuse.product.entity;

/**
 * 产品酒店信息
 * @author liuzhuo
 *
 */
public class ProductHotelInfo {

	/**
	 * 入住日期
	 */
	private String checkInDate;

	/**
	 * 离店日期
	 */
	private String checkOutDate;

	/**
	 * 酒店名称
	 */
	private String hotelName;
	/**
	 * 房型名称
	 * */
	private String roomType;

	/**
	 * 酒店类型
	 */
	private String hotelType;

	private Integer tripNights;

	public String getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}

	public String getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getHotelType() {
		return hotelType;
	}

	public void setHotelType(String hotelType) {
		this.hotelType = hotelType;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public Integer getTripNights() {
		return tripNights;
	}

	public void setTripNights(Integer tripNights) {
		this.tripNights = tripNights;
	}

}
