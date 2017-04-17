package com.ztravel.operator.basicdata.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.OperatorConstants;
import com.ztravel.common.constants.RedisKeyConst;
import com.ztravel.mediaserver.client.MediaClientUtil;
import com.ztravel.mediaserver.client.MediaClientUtil.MediaType;

/**
 * 基础数据 --> 头像维护(默认头像)
 * @author MH
 */
@Controller
@RequestMapping("/operation/basicData")
public class HeadImageController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HeadImageController.class);

	private static final RedisClient redisClient =  RedisClient.getInstance();


	@RequestMapping("/headImage")
	public String gotoHeadImageMaintainPage(Model model){
		List<String> headImgIdList = new ArrayList<String>();

		String headImgIds = redisClient.get(RedisKeyConst.DEFAULT_HEAD_IMG_KEY);
		if( StringUtils.isNotBlank(headImgIds) ){
			String[] headImgIdArray = headImgIds.split(",");
			for(String headImgId : headImgIdArray){
				headImgIdList.add(headImgId);
			}
			model.addAttribute("headImgIdList", headImgIdList);
		}
		return "operator/basicData/headImage";
	}


	@RequestMapping(value="/uploadDefaultHeadImage", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse uploadHeadImage(@RequestParam("headImgFile") MultipartFile file){
		AjaxResponse ajaxResponse = null;
		try{
			String imageId = MediaClientUtil.upload(file.getBytes(),MediaType.IMAGE);
			String headImgIds = redisClient.get(RedisKeyConst.DEFAULT_HEAD_IMG_KEY);
			if( StringUtils.isNotBlank(headImgIds) ){
				headImgIds += "," + imageId;
			}else{
				headImgIds = imageId;
			}
			redisClient.set(RedisKeyConst.DEFAULT_HEAD_IMG_KEY, headImgIds, 60*1);  //redis存字符串,不转json
			redisClient.persist(RedisKeyConst.DEFAULT_HEAD_IMG_KEY);
			ajaxResponse = AjaxResponse.instance(OperatorConstants.OPER_BASICDATA_UPLOAD_DEFAULT_HEAD_IMAGE_SUCCESS_CODE, OperatorConstants.OPER_BASICDATA_UPLOAD_DEFAULT_HEAD_IMAGE_SUCCESS_MSG);

		} catch(IOException e) {
			LOGGER.error("上传头像图片到媒体服务器IO异常");
			ajaxResponse = AjaxResponse.instance(OperatorConstants.OPER_BASICDATA_UPLOAD_DEFAULT_HEAD_IMAGE_FAILED_CODE, OperatorConstants.OPER_BASICDATA_UPLOAD_DEFAULT_HEAD_IMAGE_FAILED_MSG);
		} catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			ajaxResponse = AjaxResponse.instance(OperatorConstants.OPER_BASICDATA_UPLOAD_DEFAULT_HEAD_IMAGE_FAILED_CODE, OperatorConstants.OPER_BASICDATA_UPLOAD_DEFAULT_HEAD_IMAGE_FAILED_MSG);
		}
		return ajaxResponse;
	}


	@RequestMapping(value="/deleteDefaultHeadImage", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse deleteHeadImage(@RequestBody String delHeadImgId){
		AjaxResponse ajaxResponse = null;
		String newHeadImgIds = "";
		try{
			String oldHeadImgIds = redisClient.get(RedisKeyConst.DEFAULT_HEAD_IMG_KEY);
			String[] headImgArray = oldHeadImgIds.split(",");
			for(String headImg : headImgArray){
				if( !headImg.equals(delHeadImgId) )
					newHeadImgIds += headImg + ",";
				else
					continue;
			}
			newHeadImgIds = newHeadImgIds.substring(0, newHeadImgIds.length()-1);  //remove last ","
			redisClient.set(RedisKeyConst.DEFAULT_HEAD_IMG_KEY, newHeadImgIds, 60*1);  //redis存字符串,不转json
			redisClient.persist(RedisKeyConst.DEFAULT_HEAD_IMG_KEY);
			ajaxResponse = AjaxResponse.instance(OperatorConstants.OPER_BASICDATA_DELETE_DEFAULT_HEAD_IMAGE_SUCCESS_CODE, OperatorConstants.OPER_BASICDATA_DELETE_DEFAULT_HEAD_IMAGE_SUCCESS_MSG);

		} catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			ajaxResponse = AjaxResponse.instance(OperatorConstants.OPER_BASICDATA_DELETE_DEFAULT_HEAD_IMAGE_FAILED_CODE, OperatorConstants.OPER_BASICDATA_DELETE_DEFAULT_HEAD_IMAGE_FAILED_MSG);
		}
		return ajaxResponse;
	}

}
