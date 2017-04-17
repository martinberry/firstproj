package com.ztravel.operator.basicdata.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.OperatorConstants;
import com.ztravel.common.constants.RedisKeyConst;

/**
 * 基础数据 --> 产品属性(产品主题,产品标签)
 * @author MH
 */
@Controller
@RequestMapping("/operation/basicData")
public class ProductPropertyController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductPropertyController.class);

	private static final RedisClient redisClient =  RedisClient.getInstance();


	@RequestMapping("/productProperty")
	public String gotoProductPropertyPage(Model model){
		String themes = "";
		String tags = "";

		List<String> themeList = redisClient.get(RedisKeyConst.PRODUCT_THEME_KEY, List.class);
		if( themeList != null ){
			for(String theme : themeList){
				themes += theme + "\n";
			}
			model.addAttribute("themes", themes);
		}else{
			LOGGER.error("redis中不存在产品主题");
		}

		List<String> tagList = redisClient.get(RedisKeyConst.PRODUCT_TAG_KEY, List.class);
		if( tagList != null ){
			for(String tag : tagList){
				tags += tag + "\n";
			}
			model.addAttribute("tags", tags);
		}else{
			LOGGER.error("redis中不存在产品标签");
		}

		return "operator/basicData/productProperty";
	}


	@RequestMapping(value="/saveProductTheme", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse saveProductTheme(@RequestBody String themes){
		AjaxResponse ajaxResponse = null;
		List<String> themeList = new ArrayList<String>();
		try{
			String[] themesArray = themes.split("\n");
			for(String theme : themesArray){
				if( StringUtils.isNotBlank(theme) ){
					themeList.add(theme.trim());
				}
			}
			redisClient.set(RedisKeyConst.PRODUCT_THEME_KEY, themeList);
			redisClient.persist(RedisKeyConst.PRODUCT_THEME_KEY);
			ajaxResponse = AjaxResponse.instance(OperatorConstants.OPER_BASICDATA_SAVE_PRODUCT_THEME_SUCCESS_CODE, OperatorConstants.OPER_BASICDATA_SAVE_PRODUCT_THEME_SUCCESS_MSG);
		} catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			ajaxResponse = AjaxResponse.instance(OperatorConstants.OPER_BASICDATA_SAVE_PRODUCT_THEME_FAILED_CODE, OperatorConstants.OPER_BASICDATA_SAVE_PRODUCT_THEME_FAILED_MSG);
		}
		return ajaxResponse;
	}


	@RequestMapping(value="/saveProductTag", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse saveProductTag(@RequestBody String tags){
		AjaxResponse ajaxResponse = null;
		List<String> tagList = new ArrayList<String>();
		try{
			String[] tagsArray = tags.split("\n");
			for(String tag : tagsArray){
				if( StringUtils.isNotBlank(tag) ){
					tagList.add(tag.trim());
				}
			}
			redisClient.set(RedisKeyConst.PRODUCT_TAG_KEY, tagList);
			redisClient.persist(RedisKeyConst.PRODUCT_TAG_KEY);
			ajaxResponse = AjaxResponse.instance(OperatorConstants.OPER_BASICDATA_SAVE_PRODUCT_TAG_SUCCESS_CODE, OperatorConstants.OPER_BASICDATA_SAVE_PRODUCT_TAG_SUCCESS_MSG);
		} catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			ajaxResponse = AjaxResponse.instance(OperatorConstants.OPER_BASICDATA_SAVE_PRODUCT_TAG_FAILED_CODE, OperatorConstants.OPER_BASICDATA_SAVE_PRODUCT_TAG_FAILED_MSG);
		}
		return ajaxResponse;
	}

}
