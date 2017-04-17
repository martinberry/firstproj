package com.ztravel.product.back.freetravel.convert;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import com.ztravel.common.enums.AdditionalRule;
import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.back.freetravel.enums.Status;
import com.ztravel.product.back.freetravel.enums.TravelTipEnum;
import com.ztravel.product.back.freetravel.vo.AdditionalInfoVo;

public class AdditionalInfoConvert {
	public static Product vo2Entity(AdditionalInfoVo vo){
		Product p = new Product();
		if(StringUtils.isNotBlank(vo.getId())){
			p.setId(new ObjectId(vo.getId()));
		}
		if(StringUtils.isNotBlank(vo.getPid())){
			p.setPid(vo.getPid());
		}
		if(vo.getProgress() != null){
			p.setProgress(vo.getProgress());
		}
		if(StringUtils.isNotBlank(vo.getStatus())){
			p.setStatus(Status.valueOf(vo.getStatus()));
		}
		if(vo.getAdditionalInfos() != null && !vo.getAdditionalInfos().isEmpty()){
			Map<AdditionalRule, String> map = new HashMap<AdditionalRule, String>();
			for(String key: vo.getAdditionalInfos().keySet()){
				map.put(AdditionalRule.valueOf(key), vo.getAdditionalInfos().get(key));
			}
			p.setAdditionalInfos(map);
		}
		if(vo.getTravelTips() != null && !vo.getTravelTips().isEmpty()){
			Map<TravelTipEnum, String> tipMap = new HashMap<TravelTipEnum, String>();
			for(String key: vo.getTravelTips().keySet()){
				if(StringUtils.isNoneBlank(vo.getTravelTips().get(key))){
					tipMap.put(TravelTipEnum.valueOf(key), vo.getTravelTips().get(key));
				}
			}
			p.setTravelTips(tipMap);
		}
		return p;
	}

	public static AdditionalInfoVo entity2Vo(Product p){
		AdditionalInfoVo vo = new AdditionalInfoVo();
		if(p.getId() != null){
			vo.setId(p.getId().toString());
		}
		if(StringUtils.isNotBlank(p.getPid())){
			vo.setPid(p.getPid());
		}
		if(p.getProgress() != null ){
			vo.setProgress(p.getProgress());
		}
		if(p.getStatus() != null){
			vo.setStatus(p.getStatus().toString());;
		}
		if(p.getAdditionalInfos() != null && !p.getAdditionalInfos().isEmpty()){
			Map<String, String> map = new HashMap<String, String>();
			for(AdditionalRule key: p.getAdditionalInfos().keySet()){
				map.put(key.toString(), p.getAdditionalInfos().get(key));
			}
			vo.setAdditionalInfos(map);
		}
		if(p.getTravelTips() != null && !p.getTravelTips().isEmpty()){
			Map<String, String> tipMap = new HashMap<String, String>();
			for(TravelTipEnum key: p.getTravelTips().keySet()){
				tipMap.put(key.toString(), p.getTravelTips().get(key));
			}
			vo.setTravelTips(tipMap);
		}
		return vo;
	}
}
