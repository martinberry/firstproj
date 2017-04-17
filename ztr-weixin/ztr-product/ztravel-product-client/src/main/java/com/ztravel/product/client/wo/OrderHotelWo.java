package com.ztravel.product.client.wo;

import java.util.List;

public class OrderHotelWo {

	//酒店资源ID
	private String id ;

	//酒店名称
	private String name ;

	//酒店中文名称
	private String hotelNameCn ;

	//酒店英文名称
	private String hotelNameEn ;

	//入住偏移时间
	private List<Integer> checkinDays ;

	 //入住日期
	private String checkInDate;

	//离店日期
	private String checkOutDate;

	//房型名称
	private String roomType ;

	//床型名称
	private String bedType ;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHotelNameCn() {
		return hotelNameCn;
	}

	public void setHotelNameCn(String hotelNameCn) {
		this.hotelNameCn = hotelNameCn;
	}

	public String getHotelNameEn() {
		return hotelNameEn;
	}

	public void setHotelNameEn(String hotelNameEn) {
		this.hotelNameEn = hotelNameEn;
	}

	public List<Integer> getCheckinDays() {
		return checkinDays;
	}

	public void setCheckinDays(List<Integer> checkinDays) {
		this.checkinDays = checkinDays;
	}

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

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getBedType() {
		return bedType;
	}

	public void setBedType(String bedType) {
		this.bedType = bedType;
	}

}
