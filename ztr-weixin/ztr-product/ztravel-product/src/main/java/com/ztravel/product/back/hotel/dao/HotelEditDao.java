package com.ztravel.product.back.hotel.dao;

import com.ztravel.product.back.hotel.entity.HotelEntity;
import com.ztravel.product.back.hotel.wo.HotelEntityWo;

public interface HotelEditDao {

	/**
	 * 获取酒店资源
	 * @param id
	 * @return
	 */
	public HotelEntity getHotelById(String id);

	/**
	 * 更新酒店资源
	 * @param hotelEntity
	 */
	public void updateHotel(HotelEntityWo hotelEntityWo);

	/**
	 * 添加酒店资源
	 * @param hotelEntity
	 * @return
	 */
	public String addHotel(HotelEntity hotelEntity);

}
