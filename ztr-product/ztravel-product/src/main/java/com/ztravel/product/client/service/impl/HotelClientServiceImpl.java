package com.ztravel.product.client.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ztravel.product.dao.IHotelDao;
import com.ztravel.product.back.hotel.entity.HotelEntity;
import com.ztravel.product.client.entity.HotelClientEntity;
import com.ztravel.product.client.service.IHotelClientService;

@Service
public class HotelClientServiceImpl implements IHotelClientService{

	@Resource
	IHotelDao hotelDao ;

	@Override
	public HotelClientEntity getClientEntity(String id) {
		HotelEntity entity = hotelDao.getHotelById(id) ;
		HotelClientEntity retVal = new HotelClientEntity() ;
		retVal.setId(id);
		retVal.setHotelNameCN(entity.getHotelNameCn());
		retVal.setTypeCN(entity.getType().getDesc());
		return retVal ;
	}

}
