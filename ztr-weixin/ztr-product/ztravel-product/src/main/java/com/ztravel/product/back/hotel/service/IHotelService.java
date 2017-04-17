/**
 *
 */
package com.ztravel.product.back.hotel.service;

import java.util.List;

import com.ztravel.product.back.hotel.entity.HotelEntity;
import com.ztravel.product.back.hotel.entity.searchcriteria.HotelSearchCriteria;
import com.ztravel.product.back.hotel.vo.HotelVO;

/**
 * @author
 *
 */
public interface IHotelService {
	public List<HotelVO> searchHotels(HotelSearchCriteria criteria) throws Exception;
	public int countHotels(HotelSearchCriteria criteria) throws Exception;
	public void deleteHotelById(String id) throws Exception;

	//获取酒店by id
	HotelEntity getHotelById(String hotelId) ;

}
