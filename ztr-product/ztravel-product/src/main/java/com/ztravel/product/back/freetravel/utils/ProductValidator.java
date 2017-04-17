package com.ztravel.product.back.freetravel.utils;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.util.CollectionUtils;

import com.ztravel.product.back.freetravel.entity.Trip;
import com.ztravel.product.back.freetravel.vo.AdditionalInfoVo;
import com.ztravel.product.back.freetravel.vo.BasicInfoVo;
import com.ztravel.product.back.freetravel.vo.RecommendTripVo;

public class ProductValidator {
	public static void AssertBasicInfo(BasicInfoVo vo)throws IllegalArgumentException{
		if(StringUtils.isBlank(vo.getProductName())){
			throw new IllegalArgumentException("主标题不能为空");
		}else if(CharCounter.getLength(vo.getProductName()) > 60 || CharCounter.getLength(vo.getProductName())<4){
			throw new IllegalArgumentException("主标题长度限制4-60个字符（2-30个中文）");
		}
		if(StringUtils.isBlank(vo.getProductSubName())){
			throw new IllegalArgumentException("副标题不能为空");
		}else if(CharCounter.getLength(vo.getProductName()) > 60){
			throw new IllegalArgumentException("副标题不得超过60个字符");
		}
		if(StringUtils.isBlank(vo.getRecommendWords())){
			throw new IllegalArgumentException("推荐语不能为空");
		}else if(CharCounter.getLength(vo.getRecommendWords()) > 60){
			throw new IllegalArgumentException("推荐语不得超过60个字符");
		}
		if(StringUtils.isBlank(vo.getTheme())){
			throw new IllegalArgumentException("主题必选");
		}
		if(vo.getTripDays() == null){
			throw new IllegalArgumentException("行程天数必填");
		}
		if(vo.getTripNights() == null){
			throw new IllegalArgumentException("行程晚数必填");
		}
		if(!CollectionUtils.isEmpty(vo.getTags())){
			for(int i = 0; i < vo.getTags().size(); i++){
				String tag = vo.getTags().get(i);
				for(int j = 0; j < vo.getTags().size(); j++){
					if(i != j){
						String tag2 = vo.getTags().get(j);
						if(tag.equals(tag2))throw new IllegalArgumentException("["+tag+"]标签重复了");
					}
				}
			}
		}
		if(StringUtils.isBlank(vo.getFrom())){
			throw new IllegalArgumentException("出发地必填");
		}
		if(CollectionUtils.isEmpty(vo.getTo())){
			throw new IllegalArgumentException("目的地必填");
		}
		if(StringUtils.isBlank(vo.getNature())){
			throw new IllegalArgumentException("产品性质必填");
		}
		if(CollectionUtils.isEmpty(vo.getContents())){
			throw new IllegalArgumentException("产品内容必填");
		}
		if(CollectionUtils.isEmpty(vo.getHighLights()) || vo.getHighLights().size() <4){
			throw new IllegalArgumentException("体验亮点四条必填");
		}else{
			for(String hl: vo.getHighLights()){
				if(StringUtils.isBlank(hl)){
					throw new IllegalArgumentException("体验亮点内容不能为空白字符");
				}else if(CharCounter.getLength(hl) < 2 || CharCounter.getLength(hl) > 100){
					throw new IllegalArgumentException("体验亮点字符限制2-100个字符（1-50个中文）");
				}
			}
		}
		if(CollectionUtils.isEmpty(vo.getImages())){
			throw new IllegalArgumentException("至少上传一张高清大图");
		}else if(vo.getImages().size() > 4){
			throw new IllegalArgumentException("最多上传四张高清大图");
		}
		if(CollectionUtils.isEmpty(vo.getTitleImages())){
			throw new IllegalArgumentException("至少上传一张标题图层");
		}else if(vo.getImages().size() > 4){
			throw new IllegalArgumentException("最多上传四张标题图层");
		}
	}

	public static void AssertRecommendTrip(RecommendTripVo vo)throws IllegalArgumentException{
		if(StringUtils.isBlank(vo.getId())){
			throw new IllegalArgumentException("产品ID丢失");
		}else if(!ObjectId.isValid(vo.getId())){
			throw new IllegalArgumentException("产品ID不合法");
		}
		if(vo.getProgress() == null || vo.getProgress()==0 ){
			throw new IllegalArgumentException("产品编辑进度错误");
		}
		if(vo.getRecommendTrips() != null){
			for(Trip t: vo.getRecommendTrips()){
				int day = t.getIndex();
				if(day == 0)continue;
				if(StringUtils.isBlank(t.getTitle())){
					throw new IllegalArgumentException("第"+day+"天的推荐标题为空");
				}
				if(StringUtils.isBlank(t.getContent())){
					throw new IllegalArgumentException("第"+day+"天的推荐行程内容为空");
				}
			}
		}
	}

	public static void AssertAdditionalInfo(AdditionalInfoVo vo)throws IllegalArgumentException{
		if(StringUtils.isBlank(vo.getId())){
			throw new IllegalArgumentException("产品ID丢失");
		}else if(!ObjectId.isValid(vo.getId())){
			throw new IllegalArgumentException("产品ID不合法");
		}
		if(vo.getProgress() == null || vo.getProgress()==0 ){
			throw new IllegalArgumentException("产品编辑进度错误");
		}
	}
}
