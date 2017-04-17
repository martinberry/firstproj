package com.ztravel.product.back.hotel.service;

import com.ztravel.product.back.hotel.wo.HotelEntityWo;

public interface HotelEditService {

	/**
	 * 保存酒店图片
	 * @param imageNameOrId
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	public String saveHotelPicture(String imageNameOrId, byte[] bytes) throws Exception ;

	/**
	 * 获取酒店资源
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public HotelEntityWo getHotelById(String id) throws Exception ;

	/**
	 * 添加酒店资源
	 * @param hotelEntityWo
	 * @return
	 * @throws Exception
	 */
	public String addHotel(HotelEntityWo hotelEntityWo) throws Exception ;

	/**
	 * 更新酒店资源
	 * @param hotelEntityWo
	 * @throws Exception
	 */
	public void updateHotel(HotelEntityWo hotelEntityWo) throws Exception ;

}
