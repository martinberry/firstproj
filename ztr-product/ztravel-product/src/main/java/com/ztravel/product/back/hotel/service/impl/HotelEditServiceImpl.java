package com.ztravel.product.back.hotel.service.impl;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.ztravel.mediaserver.client.CompressType;
import com.ztravel.mediaserver.client.MediaClientUtil;
import com.ztravel.mediaserver.client.MediaClientUtil.MediaType;
import com.ztravel.product.back.hotel.convert.HotelConvert;
import com.ztravel.product.back.hotel.dao.HotelEditDao;
import com.ztravel.product.back.hotel.entity.HotelEntity;
import com.ztravel.product.back.hotel.service.HotelEditService;
import com.ztravel.product.back.hotel.wo.HotelEntityWo;
import com.ztravel.product.utils.MongoSequenceUtil;

@Service
public class HotelEditServiceImpl implements HotelEditService {

	@Resource
	private HotelEditDao hotelEditDaoImpl ;

	@Override
	public String saveHotelPicture(String imageNameOrId, byte[] bytes) throws Exception {
		String mediaId = "" ;
//		mediaId = MediaClientUtil.upload(bytes, MediaType.IMAGE) ;
		mediaId = MediaClientUtil.uploadAndCompress(bytes, MediaType.IMAGE,imageNameOrId,CompressType.Normal);
		return mediaId ;
	}

	@Override
	public HotelEntityWo getHotelById(String id) throws Exception {

		HotelEntity hotelEntity = hotelEditDaoImpl.getHotelById(id);

		HotelEntityWo hotelEntityWo = HotelConvert.convertFromEntity(hotelEntity);

		return hotelEntityWo;
	}

	@Override
	public String addHotel(HotelEntityWo hotelEntityWo) throws Exception {

		HotelEntity hotelEntity = HotelConvert.convertToEntity(hotelEntityWo);

		//格式为J+5位数字
		hotelEntity.setShowId(MongoSequenceUtil.generateHotelEntityId());

		DateTime now = new DateTime() ;
		hotelEntity.setCreateTime(now);
		hotelEntity.setUpdateTime(now);

		return hotelEditDaoImpl.addHotel(hotelEntity);
	}

	@Override
	public void updateHotel(HotelEntityWo hotelEntityWo) throws Exception {

		hotelEditDaoImpl.updateHotel(hotelEntityWo);
	}


}
