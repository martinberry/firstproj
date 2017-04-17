/**
 *
 */
package com.ztravel.product.back.hotel.convertor;

import java.util.ArrayList;
import java.util.List;

import com.ztravel.product.back.hotel.entity.HotelEntity;
import com.ztravel.product.back.hotel.vo.HotelVO;

/**
 * @author
 * 转换HotelEntity(数据库) --> HotelVO(页面展示)
 */
public class HotelEntityConvertor {

	public static HotelVO convertEntityToVO(HotelEntity hotelEntity){
		HotelVO hotel = new HotelVO();
		hotel.setId(hotelEntity.getId().toString());
		hotel.setHotelId(hotelEntity.getShowId());
		hotel.setHotelNameCn(hotelEntity.getHotelNameCn());
		hotel.setCountry(hotelEntity.getNation());
		hotel.setCityOrAttraction(hotelEntity.getCity());
		if( hotelEntity.getType() != null ){
			hotel.setHotelType(hotelEntity.getType().getDesc());
		}
		if( hotelEntity.getRating() != null ){
			hotel.setRating(hotelEntity.getRating().getDesc());
		}
		hotel.setHighLights(hotelEntity.getAdvantage());
		hotel.setPhoneNumber(hotelEntity.getPhone());
		hotel.setAddress(hotelEntity.getAddress());
		if( hotelEntity.getIsComplete() == true ){
			hotel.setStatus("完成");
		}else if( hotelEntity.getIsComplete() == false ){
			hotel.setStatus("草稿");
		}
		return hotel;
	}

	public static List<HotelVO> convertEntityListToVOList(List<HotelEntity> hotelEntityList){
		List<HotelVO> hotelList = new ArrayList<HotelVO>();
		for(HotelEntity hotelEntity : hotelEntityList){
			HotelVO hotel = convertEntityToVO(hotelEntity);
			hotelList.add(hotel);
		}
		return hotelList;
	}

}
