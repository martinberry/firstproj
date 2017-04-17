package com.ztravel.order.front.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.order.po.OrderContactor;
import com.ztravel.order.po.OrderPassenger;

public class OrderValidation {

	private static Logger logger = RequestIdentityLogger.getLogger(OrderValidation.class);

	private static final String contactorReg = "^([a-zA-Z0-9|\\u4e00-\\u9fa5]){1,40}$";
	private static final String phoneReg = "^(?:13\\d|14\\d|15\\d|17\\d|18\\d)\\d{5}(\\d{3}|\\*{3})$";
	private static final String emailReg = "^((\\w-*\\.*)+@(\\w-?)+(\\.\\w{2,})+){0,50}$";
	private static final String addressReg = "^[a-zA-Z0-9\\u4e00-\\u9fa5|\\/|\\\\|\\.|）|（|:|,|;|：|\\_|；|，|、|\\“|\\”|’|＂|＂|——|。|-]{1,100}$";
	private static final String idCardReg = "(^\\d{15}$)|(^\\d{17}(\\d|X|x)$)";
	private static final String passPortReg = "^[A-Za-z0-9]{1,20}$";
	private static final String hkPassReg = "^[a-zA-Z]{1}[0-9]{8}$";
	private static final String enNameReg = "^[a-zA-z]{1,30}$";
	private static final String chNameReg = "^[\\u4E00-\\u9FA5]{1,20}$";

	public static boolean validateOrderContactor(OrderContactor contactor) {

		String id= contactor.getId();
		if (StringUtils.isBlank(id)) {
			logger.error("主键id不能为空");
			return false;
			}

		String name= contactor.getContactor();
		if (StringUtils.isBlank(name) || !name.matches(contactorReg)) {
			logger.error("请填写正确姓名");
			return false;
			}
		String phone= contactor.getPhone();
		if (StringUtils.isBlank(phone) || !phone.matches(phoneReg)) {
			logger.error("请填写正确手机号");
			return false;
			}
		String email= contactor.getEmail();
		if (StringUtils.isBlank(email) || !email.matches(emailReg) ||email.length() > 50) {
			logger.error("邮箱格式有误");
			return false;
			}
		String province= contactor.getProvince();
		if (StringUtils.isBlank(province)) {
			logger.error("省/直辖市必填");
			return false;
			}
		String city= contactor.getCity();
		if (StringUtils.isBlank(city)) {
			logger.error("市必填");
			return false;
			}
		if (StringUtils.isBlank(province)) {
			logger.error("省/直辖市必填");
			return false;
			}
		String county= contactor.getCounty();
		if (StringUtils.isBlank(county)) {
			logger.error("县区必填");
			return false;
			}
		String address= contactor.getAddress();
		if (StringUtils.isBlank(address) || !address.matches(addressReg)) {
			logger.error("请填写100个以内字符");
			return false;
			}

		return true;

	}

	public static boolean validateOrderPassenger(OrderPassenger passenger) {

		String id= passenger.getId();
		if (StringUtils.isBlank(id)) {
			logger.error("主键id不能为空");
			return false;
			}

		String firstName= passenger.getFirstName();
		String lastName= passenger.getLastName();
		if (StringUtils.isBlank(firstName) ||StringUtils.isBlank(lastName)) {
			logger.error("有效证件上的姓名不能为空");
			return false;
			}else if(firstName.matches(enNameReg) && lastName.matches(enNameReg)){
				passenger.setName(firstName + "/" + lastName);
			}else if(firstName.matches(chNameReg) && lastName.matches(chNameReg)){
				passenger.setName(firstName + lastName);
			}else{
				logger.error("有效证件上的姓名填写有误");
				return false;
			}

		String firstEnName= passenger.getFirstEnName();
		String lastEnName= passenger.getLastEnName();
		if (StringUtils.isBlank(firstEnName) ||StringUtils.isBlank(lastEnName)) {
			logger.error("有效证件上的英文/拼音名不能为空");
			return false;
			}else if(firstEnName.matches(enNameReg) && lastEnName.matches(enNameReg)){
				passenger.setEnName(firstEnName + "/" + lastEnName);
			}else{
				logger.error("有效证件上的英文/拼音名填写有误");
				return false;
			}

//		String country= passenger.getCountry();
//		if (StringUtils.isBlank(country)) {
//			logger.error("请输入正确国籍");
//			return false;
//			}
//		String credentialDeadLine= passenger.getCredentialDeadLine();
//		if (StringUtils.isBlank(credentialDeadLine)) {
//			logger.error("证件有效期必填");
//			return false;
//			}

		String credentialType= passenger.getCredentialType();
		String credentialNum= passenger.getCredentialNum();
		if (StringUtils.isBlank(credentialType) || StringUtils.isBlank(credentialNum)) {
			logger.error("证件类型与证件号必填");
			return false;
			}
		if (credentialType.equalsIgnoreCase("IDCARD")) {
    		if (!credentialNum.matches(idCardReg)) {
    			logger.error("身份证格式填写有误");
				return false;
    		}
    	}else if (credentialType.equalsIgnoreCase("PASSPORT")) {
    		if (!credentialNum.matches(passPortReg)) {
    			logger.error("护照格式填写有误");
				return false;
    		}
    	}else if (credentialType.equalsIgnoreCase("GANGAOPASS")) {
    		if (!credentialNum.matches(hkPassReg)) {
    			logger.error("港澳通行证格式填写有误");
				return false;
    		}
    	}

		String birthday= passenger.getBirthday();
		if (StringUtils.isBlank(birthday)) {
			logger.error("出生日期必填");
			return false;
			}

		String gender= passenger.getGender();
		if (StringUtils.isBlank(gender)) {
			logger.error("性别必填");
			return false;
			}

		return true;
	}

	public static boolean isContainsChinese(String str) {

		Pattern pat = Pattern.compile("[\\u4e00-\\u9fa5]");
		Matcher matcher = pat.matcher(str);
		if (matcher.find())    {
			return true;
		}
		return false;
	}

}
