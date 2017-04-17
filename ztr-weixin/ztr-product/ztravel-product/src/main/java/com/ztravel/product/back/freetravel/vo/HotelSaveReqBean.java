package com.ztravel.product.back.freetravel.vo;

import java.util.List;

import com.ztravel.product.back.freetravel.entity.Hotel;


/**
 * @author wanhaofan
 * 酒店信息保存bean
 * */
public class HotelSaveReqBean {

	private String id ;

	private List<Hotel> hotels ;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Hotel> getHotels() {
		return hotels;
	}

	public void setHotels(List<Hotel> hotels) {
		this.hotels = hotels;
	}




}
