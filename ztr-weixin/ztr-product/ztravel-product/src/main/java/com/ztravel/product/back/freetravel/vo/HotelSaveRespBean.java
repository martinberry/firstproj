package com.ztravel.product.back.freetravel.vo;

import java.util.List;


public class HotelSaveRespBean {
	private List<CostHotelVo> hotels ;

	private Boolean flag ;


	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public List<CostHotelVo> getHotels() {
		return hotels;
	}

	public void setHotels(List<CostHotelVo> hotels) {
		this.hotels = hotels;
	}


}
