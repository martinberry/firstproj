/**
 *
 */
package com.ztravel.product.dao;

import java.util.List;
import java.util.Map;

import com.ztravel.product.back.hotel.entity.HotelEntity;

/**
 * @author
 * 酒店列表查询
 */
public interface IHotelDao {
	public List<HotelEntity> searchHotelsWithPaging(Map params);
	public int countHotels(Map params);
	public int deleteById(String id);    //return the number of documents affected

	HotelEntity getHotelById(String id) ;

}
