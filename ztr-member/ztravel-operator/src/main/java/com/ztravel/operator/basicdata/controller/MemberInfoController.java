package com.ztravel.operator.basicdata.controller;

import java.util.LinkedList;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.OperatorConstants;
import com.ztravel.operator.basicdata.util.ExcelUtil;
import com.ztravel.operator.basicdata.service.ICountryService;
import com.ztravel.operator.basicdata.service.INicknameLibService;

/**
 * 基础数据 --> 用户信息数据(昵称AB库,国籍)
 * @author MH
 */
@Controller
@RequestMapping("/operation/basicData")
public class MemberInfoController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MemberInfoController.class);

	@Resource
	private INicknameLibService nicknameLibService;

	@Resource
	private ICountryService countryService;


	@RequestMapping("/memberInfo")
	public String gotoMemberInfoPage(){
		return "operator/basicData/memberInfo";
	}

	@RequestMapping(value="/uploadNicknameABLib", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse uploadNicknameABLibFile(@RequestParam("nickNameABLibFile") MultipartFile file){
		AjaxResponse ajaxResponse = null;
		try{
			nicknameLibService.deleteNicknameLib();
			nicknameLibService.saveNicknameLib(file);
			ajaxResponse = AjaxResponse.instance(OperatorConstants.OPER_BASICDATA_UPLOAD_NICKNAME_ABLIB_FILE_SUCCESS_CODE, OperatorConstants.OPER_BASICDATA_UPLOAD_NICKNAME_ABLIB_FILE_SUCCESS_MSG);
		} catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			ajaxResponse = AjaxResponse.instance(OperatorConstants.OPER_BASICDATA_UPLOAD_NICKNAME_ABLIB_FILE_FAILED_CODE, OperatorConstants.OPER_BASICDATA_UPLOAD_NICKNAME_ABLIB_FILE_FAILED_MSG);
		}
		return ajaxResponse;
	}

	@RequestMapping(value="/uploadCountryFile", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse uploadCountryFile(@RequestParam("countryFile") MultipartFile file){
		AjaxResponse ajaxResponse = null;
		try{
			countryService.deleteCountryData();
			countryService.saveCountryData(file);
			ajaxResponse = AjaxResponse.instance(OperatorConstants.OPER_BASICDATA_UPLOAD_COUNTRY_FILE_SUCCESS_CODE, OperatorConstants.OPER_BASICDATA_UPLOAD_COUNTRY_FILE_SUCCESS_MSG);
		} catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			ajaxResponse = AjaxResponse.instance(OperatorConstants.OPER_BASICDATA_UPLOAD_COUNTRY_FILE_FAILED_CODE, OperatorConstants.OPER_BASICDATA_UPLOAD_COUNTRY_FILE_FAILED_MSG);
		}
		return ajaxResponse;
	}

}
