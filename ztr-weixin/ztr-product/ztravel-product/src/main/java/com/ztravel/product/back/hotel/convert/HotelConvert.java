package com.ztravel.product.back.hotel.convert;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.github.jmkgreen.morphia.query.UpdateOperations;
import com.ztravel.product.back.hotel.entity.HotelEntity;
import com.ztravel.product.back.hotel.enums.HotelRate;
import com.ztravel.product.back.hotel.enums.HotelType;
import com.ztravel.product.back.hotel.wo.HotelEntityWo;

public class HotelConvert {

	public static HotelEntityWo convertFromEntity(HotelEntity entity) {
		HotelEntityWo entityWo = new HotelEntityWo();
		entityWo.setId(entity.getId().toString());
		entityWo.setHotelNameCn(entity.getHotelNameCn());
		entityWo.setHotelNameEn(entity.getHotelNameEn());
		entityWo.setPhone(entity.getPhone());
		entityWo.setActivityFacilities(entity.getActivityFacilities());
		entityWo.setAddress(entity.getAddress());
		entityWo.setAdvantage(entity.getAdvantage());
		entityWo.setCateringFacilities(entity.getCateringFacilities());
		entityWo.setCity(entity.getCity());
		entityWo.setCompFacilities(entity.getCompFacilities());
		entityWo.setContinent(entity.getContinent());
		entityWo.setDescribe(entity.getDescribe());
		entityWo.setLat(entity.getLat());
		entityWo.setLon(entity.getLon());
		entityWo.setNation(entity.getNation());
		entityWo.setNetworkFacilities(entity.getNetworkFacilities());
		entityWo.setNotes(entity.getNotes());
		entityWo.setServiceFacilities(entity.getServiceFacilities());

		if(entity.getRating() != null){
			entityWo.setRating(entity.getRating().toString());
		}
		if(entity.getType() != null){
			entityWo.setType(entity.getType().toString());
		}

		if(entity.getPictureIds() != null){
			entityWo.setPictureIds(entity.getPictureIds());
		}
//		entityWo.setShowId(entity.getShowId());
		entityWo.setIsComplete(entity.getIsComplete());
//		entityWo.setCreateTime(entity.getCreateTime());
//		entityWo.setUpdateTime(entity.getUpdateTime());
		return entityWo;

	}

	public static HotelEntity convertToEntity(HotelEntityWo entityWo) {
		HotelEntity entity = new HotelEntity();
		entity.setIsComplete(entityWo.getIsComplete());
		entity.setActivityFacilities(entityWo.getActivityFacilities());
		entity.setAddress(entityWo.getAddress());
		entity.setAdvantage(entityWo.getAdvantage());
		entity.setCateringFacilities(entityWo.getCateringFacilities());
		entity.setCity(entityWo.getCity());
		entity.setCompFacilities(entityWo.getCompFacilities());
		entity.setContinent(entityWo.getContinent());
		entity.setDescribe(entityWo.getDescribe());
		entity.setHotelNameCn(entityWo.getHotelNameCn());
		entity.setHotelNameEn(entityWo.getHotelNameEn());
		entity.setLat(entityWo.getLat());
		entity.setLon(entityWo.getLon());
		entity.setNation(entityWo.getNation());
		entity.setNetworkFacilities(entityWo.getNetworkFacilities());
		entity.setNotes(entityWo.getNotes());
		entity.setPhone(entityWo.getPhone());
		if(entityWo.getPictureIds() != null){
			entity.setPictureIds(entityWo.getPictureIds());
		}
		if(StringUtils.isNotBlank(entityWo.getRating())){
			entity.setRating(HotelRate.valueOf(entityWo.getRating()));
		}
		if(StringUtils.isNotBlank(entityWo.getType())){
			entity.setType(HotelType.valueOf(entityWo.getType()));
		}

		entity.setServiceFacilities(entityWo.getServiceFacilities());

		return entity;

	}

	public static void convertToUpdateOperations(HotelEntityWo entityWo, UpdateOperations<HotelEntity> ops) {

		ops.set("isComplete",entityWo.getIsComplete());
		ops.set("activityFacilities",entityWo.getActivityFacilities());
		ops.set("advantage",entityWo.getAdvantage());
		ops.set("address",entityWo.getAddress());
		ops.set("cateringFacilities",entityWo.getCateringFacilities());
		ops.set("city",entityWo.getCity());
		ops.set("compFacilities",entityWo.getCompFacilities());
		ops.set("continent",entityWo.getContinent());
		ops.set("describe",entityWo.getDescribe());
		ops.set("hotelNameCn",entityWo.getHotelNameCn());
		ops.set("hotelNameEn",entityWo.getHotelNameEn());
		ops.set("lat",entityWo.getLat());
		ops.set("lon",entityWo.getLon());
		ops.set("nation",entityWo.getNation());
		ops.set("networkFacilities",entityWo.getNetworkFacilities());
		ops.set("notes",entityWo.getNotes());
		ops.set("phone",entityWo.getPhone());
		ops.set("serviceFacilities",entityWo.getServiceFacilities());
		ops.set("pictureIds",entityWo.getPictureIds());
		ops.set("rating",HotelRate.valueOf(entityWo.getRating()));
		ops.set("type",HotelType.valueOf(entityWo.getType()));

	}

	public static Map<String, String> convertHotelRateToMap() {
		Map<String, String> map = new LinkedHashMap<>();
		for(HotelRate rate : HotelRate.values()){
			map.put(rate.toString(), rate.getDesc());
		}
		return map ;
	}

	public static Map<String, String> convertHotelTypeToMap() {
		Map<String, String> map = new LinkedHashMap<>();
		for(HotelType rate : HotelType.values()){
			map.put(rate.toString(), rate.getDesc());
		}
		return map ;
	}

}
