package com.ztravel.operator.basicdata.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

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

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.OperatorConstants;
import com.ztravel.common.constants.RedisKeyConst;
import com.ztravel.operator.basicdata.util.ExcelUtil;
import com.ztravel.operator.basicdata.vo.DestinationVO;
import com.ztravel.operator.basicdata.entity.Destination;
import com.ztravel.operator.basicdata.service.IDestinationService;

/**
 * 基础数据 --> POI信息(出发地,目的地)
 * @author MH
 */
@Controller
@RequestMapping("/operation/basicData")
public class POIController {

	private static final Logger LOGGER = LoggerFactory.getLogger(POIController.class);

	private static final RedisClient redisClient =  RedisClient.getInstance();

	@Resource
	private IDestinationService destinationService;


	@RequestMapping("/poi")
	public String gotoPOIInfoPage(Model model){
		String departurePlaces = "";

		List<String> departPlaceList = redisClient.get(RedisKeyConst.DEPARTURE_PLACE_KEY, List.class);
		if( departPlaceList != null ){
			for(String departPlace : departPlaceList){
				departurePlaces += departPlace + "\n";
			}
			model.addAttribute("departurePlaces", departurePlaces);
		}else{
			LOGGER.error("redis中不存在出发地信息");
		}

		try{
			DestinationVO dest = destinationService.getDestinationInfoFromMongo();
			model.addAttribute("destinationInfo", dest);
		} catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return "operator/basicData/poiInfo";
	}


	@RequestMapping(value="/saveDeparturePlace", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse saveDeparturePlaces(@RequestBody String departPlaces){
		AjaxResponse ajaxResponse = null;
		List<String> departPlaceList = new ArrayList<String>();
		try{
			String[] departPlaceArray = departPlaces.split("\n");
			for(String departPlace : departPlaceArray){
				if( StringUtils.isNotBlank(departPlace) ){
					departPlaceList.add(departPlace.trim());
				}
			}
			redisClient.set(RedisKeyConst.DEPARTURE_PLACE_KEY, departPlaceList);
			redisClient.persist(RedisKeyConst.DEPARTURE_PLACE_KEY);
			ajaxResponse = AjaxResponse.instance(OperatorConstants.OPER_BASICDATA_SAVE_DEPARTURE_PLACE_SUCCESS_CODE, OperatorConstants.OPER_BASICDATA_SAVE_DEPARTURE_PLACE_SUCCESS_MSG);

		} catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			ajaxResponse = AjaxResponse.instance(OperatorConstants.OPER_BASICDATA_SAVE_DEPARTURE_PLACE_FAILED_CODE, OperatorConstants.OPER_BASICDATA_SAVE_DEPARTURE_PLACE_FAILED_MSG);
		}
		return ajaxResponse;
	}


	@RequestMapping(value="/uploadDestination", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse uploadDestinationFile(@RequestParam("destinationFile") MultipartFile file){
		AjaxResponse ajaxResponse = null;
		try{
			List<JSONObject> destJsonList = ExcelUtil.resolveDestinationExcel(file.getInputStream());

			destinationService.deleteDestinationsFromMongo();
			destinationService.saveDestinationsToMongo(destJsonList);
			destinationService.saveDestinationsToRedis(destJsonList);
			//重新上传目的地后,没有设置默认目的地,清空redis中的默认目的地
			String defaultDest = "";
			redisClient.set(RedisKeyConst.DEFAULT_DESTINATION_KEY, defaultDest, 1800);
			redisClient.persist(RedisKeyConst.DEFAULT_DESTINATION_KEY);

			ajaxResponse = AjaxResponse.instance(OperatorConstants.OPER_BASICDATA_UPLOAD_DESTINATION_FILE_SUCCESS_CODE, OperatorConstants.OPER_BASICDATA_UPLOAD_DESTINATION_FILE_SUCCESS_MSG);

		} catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			ajaxResponse = AjaxResponse.instance(OperatorConstants.OPER_BASICDATA_UPLOAD_DESTINATION_FILE_FAILED_CODE, OperatorConstants.OPER_BASICDATA_UPLOAD_DESTINATION_FILE_FAILED_MSG);
		}
		return ajaxResponse;
	}


	@RequestMapping(value="/setDefaultDestination", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse setDefaultDestination(@RequestBody String defaultDest){
		AjaxResponse ajaxResponse = null;
		try{
			destinationService.setDefaultDestinationMongo(defaultDest);
			redisClient.set(RedisKeyConst.DEFAULT_DESTINATION_KEY, defaultDest, 1800);  //redis存字符串,不转json
			redisClient.persist(RedisKeyConst.DEFAULT_DESTINATION_KEY);
			ajaxResponse = AjaxResponse.instance(OperatorConstants.OPER_BASICDATA_SET_DEFAULT_DESTINATION_SUCCESS_CODE, OperatorConstants.OPER_BASICDATA_SET_DEFAULT_DESTINATION_SUCCESS_MSG);

		} catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			ajaxResponse = AjaxResponse.instance(OperatorConstants.OPER_BASICDATA_SET_DEFAULT_DESTINATION_FAILED_CODE, OperatorConstants.OPER_BASICDATA_SET_DEFAULT_DESTINATION_FAILED_MSG);
		}
		return ajaxResponse;
	}


}
