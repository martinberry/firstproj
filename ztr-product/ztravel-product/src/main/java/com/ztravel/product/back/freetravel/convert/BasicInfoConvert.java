package com.ztravel.product.back.freetravel.convert;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import com.ztravel.common.enums.DestinationType;
import com.ztravel.common.enums.Nature;
import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.back.freetravel.enums.Content;
import com.ztravel.product.back.freetravel.vo.BasicInfoVo;

public class BasicInfoConvert {
	public static BasicInfoVo entity2Vo(Product product){
		BasicInfoVo vo = new BasicInfoVo();
		if(product.getId() != null){
			vo.setId(product.getId().toString());
		}
		if(StringUtils.isNotBlank(product.getPid())){
			vo.setPid(product.getPid());
		}
		if(StringUtils.isNotBlank(product.getpName())){
			vo.setProductName(product.getpName());
		}
		if(StringUtils.isNotBlank(product.getSubName())){
			vo.setProductSubName(product.getSubName());
		}
		if(StringUtils.isNotBlank(product.getRecommendWords())){
			vo.setRecommendWords(product.getRecommendWords());
		}
		if(StringUtils.isNotBlank(product.getTheme())){
			vo.setTheme(product.getTheme());
		}
		if(product.getTripDays() != null){
			vo.setTripDays(product.getTripDays());
		}
		if(product.getTripNights() != null){
			vo.setTripNights(product.getTripNights());
		}
		if(CollectionUtils.isNotEmpty(product.getTags())){
			vo.setTags(product.getTags());
		}
		if(StringUtils.isNotBlank(product.getFrom())){
			vo.setFrom(product.getFrom());
		}
		if(CollectionUtils.isNotEmpty(product.getTo())){
			vo.setTo(product.getTo());
		}
		if(CollectionUtils.isNotEmpty(product.getToContinent())){
			vo.setToContinent(product.getToContinent());
		}
		if(CollectionUtils.isNotEmpty(product.getToCountry())){
			vo.setToCountry(product.getToCountry());
		}
		if(product.getDestinationType() != null){
			vo.setDestinationType(product.getDestinationType().toString());
		}
		if(product.getNature() != null){
			vo.setNature(product.getNature().toString());
		}
		if(CollectionUtils.isNotEmpty(product.getContents())){
			List<String> contents = new LinkedList<String>();
			for(Content c: product.getContents()){
				contents.add(c.toString());
			}
			vo.setContents(contents);
		}
		if(CollectionUtils.isNotEmpty(product.getHighLights())){
			vo.setHighLights(product.getHighLights());
		}
		vo.setHighLightTitles(product.getHighLightTitles());
		if(StringUtils.isNotBlank(product.getLightColor())){
			vo.setLightColor(product.getLightColor());
		}
		if(CollectionUtils.isNotEmpty(product.getImages())){
			vo.setImages(product.getImages());
		}
		if(CollectionUtils.isNotEmpty(product.getTitleImages())){
			vo.setTitleImages(product.getTitleImages());
		}
		if(CollectionUtils.isNotEmpty(product.getDetailTitleImages())){
			vo.setDetailTitleImages(product.getDetailTitleImages());
		}
		if(product.getProgress() != null){
			vo.setProgress(product.getProgress());
		}
		if(product.getStatus() != null){
			vo.setStatus(product.getStatus().toString());
		}
		return vo;
	}

	public static Product vo2Entity(BasicInfoVo basicInfo){
		Product p = new Product();
		if(StringUtils.isNotBlank(basicInfo.getId())){
			p.setId(new ObjectId(basicInfo.getId()));
		}
		if(StringUtils.isNotBlank(basicInfo.getPid())){
			p.setPid(basicInfo.getPid());
		}
		if(StringUtils.isNotBlank(basicInfo.getProductName())){
			p.setpName(basicInfo.getProductName());
		}
		p.setSubName(basicInfo.getProductSubName());
		p.setRecommendWords(basicInfo.getRecommendWords());
		if(StringUtils.isNotBlank(basicInfo.getTheme())){
			p.setTheme(basicInfo.getTheme());
		}
		if(basicInfo.getTripDays() != null){
			p.setTripDays(basicInfo.getTripDays());
		}
		if(basicInfo.getTripNights() != null){
			p.setTripNights(basicInfo.getTripNights());
		}
		p.setTags(basicInfo.getTags());
		if(StringUtils.isNotBlank(basicInfo.getFrom())){
			p.setFrom(basicInfo.getFrom());
		}
		p.setTo(basicInfo.getTo());
		p.setToContinent(basicInfo.getToContinent());
		p.setToCountry(basicInfo.getToCountry());
		if(StringUtils.isNotBlank(basicInfo.getDestinationType())){
			p.setDestinationType(DestinationType.valueOf(basicInfo.getDestinationType()));
		}
		if(StringUtils.isNotBlank(basicInfo.getNature())){
			p.setNature(Nature.valueOf(basicInfo.getNature()));
		}
		if(CollectionUtils.isNotEmpty(basicInfo.getContents())){
			List<Content> contents = new LinkedList<Content>();
			for(String c: basicInfo.getContents()){
				contents.add(Content.valueOf(c));
			}
			p.setContents(contents);
		}else{
			p.setContents(new ArrayList<Content>());//清空
		}
		p.setHighLights(basicInfo.getHighLights());
		p.setHighLightTitles(basicInfo.getHighLightTitles());
		p.setLightColor(basicInfo.getLightColor());
		p.setImages(basicInfo.getImages());
		p.setTitleImages(basicInfo.getTitleImages());
		p.setDetailTitleImages(basicInfo.getDetailTitleImages());
		if(basicInfo.getProgress() != null){
			p.setProgress(basicInfo.getProgress());
		}
//		if(StringUtils.isNoneBlank(basicInfo.getStatus())){
//			p.setStatus(Status.valueOf(basicInfo.getStatus()));
//		}
		return p;
	}
}
