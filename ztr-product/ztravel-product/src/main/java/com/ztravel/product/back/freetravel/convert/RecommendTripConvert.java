package com.ztravel.product.back.freetravel.convert;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.back.freetravel.vo.RecommendTripVo;

public class RecommendTripConvert {
	public static Product vo2Entity(RecommendTripVo vo){
		Product p = new Product();
		if(StringUtils.isNotBlank(vo.getId())){
			p.setId(new ObjectId(vo.getId()));
		}
		if(CollectionUtils.isNotEmpty(vo.getRecommendTrips())){
			p.setRecommendTrips(vo.getRecommendTrips());
		}
		if(vo.getProgress() != null){
			p.setProgress(vo.getProgress());
		}
//		if(StringUtils.isNoneBlank(vo.getStatus())){
//			p.setStatus(Status.valueOf(vo.getStatus()));
//		}
		return p;
	}

	public static RecommendTripVo entiry2Vo(Product product){
		RecommendTripVo  rt = new RecommendTripVo();
		if(product.getId() != null){
			rt.setId(product.getId().toString());
		}
		if(StringUtils.isNotBlank(product.getPid())){
			rt.setPid(product.getPid());
		}
		if(StringUtils.isNotBlank(product.getpName())){
			rt.setProductName(product.getpName());
		}
		if(product.getTripDays() != null){
			rt.setTripDays(product.getTripDays());
		}
		if(product.getTripNights() != null){
			rt.setTripNights(product.getTripNights());
		}
		if(CollectionUtils.isNotEmpty(product.getRecommendTrips())){
			rt.setRecommendTrips(product.getRecommendTrips());
		}
		if(product.getStatus() != null){
			rt.setStatus(product.getStatus().toString());
		}
		if(product.getProgress() != null){
			rt.setProgress(product.getProgress());
		}
		return rt;
	}
}
