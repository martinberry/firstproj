package com.ztravel.member.opera.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.plexus.util.StringUtils;

import com.ztravel.member.opera.wo.TravellerRequestInfo;
import com.ztravel.member.opera.wo.TravellerSearchCriteria;


public class TravellerUtils {//

	public static boolean validateTravellerRequest(TravellerRequestInfo searchCriteria, TravellerSearchCriteria criteria) {

		String travelerNameCn = searchCriteria.getTravelerNameCn().trim() ;
		String travelerNameEn = searchCriteria.getTravelerNameEn().trim().replaceAll(" ", "/") ;
		
		if( StringUtils.isNotBlank(travelerNameCn)){
			if(validateName(travelerNameCn)){
				criteria.setTravelerNameCn(travelerNameCn);
			}else{
				return false;
			}
//			else if(isEnglish(travelerName)){
//				criteria.setTravelerNameEn(travelerName);
//				handleTravelerNameEn(criteria);
//			}else{
//				return false;
//			}
		}
		if( StringUtils.isNotBlank(travelerNameEn)){
			if(isEnglish(travelerNameEn)){
				criteria.setTravelerNameEn(travelerNameEn);
			}else{
				return false;
			}
		}

//		criteria.setTravelerNameCn(travelerName);
//		criteria.setTravelerNameEn(travelerNameEn);

		String phoneNum = searchCriteria.getPhoneNum();
		if( StringUtils.isNotBlank(phoneNum)){
			if(isPhone(phoneNum)){
				criteria.setPhoneNum(phoneNum);
			}else{
				return false;
			}
		}

		return true;

	}

	public static void handleTravelerNameEn(TravellerSearchCriteria criteria) {

		String travelerName = criteria.getTravelerNameEn();

		if(StringUtils.isNotBlank(travelerName)){
			String[] str = travelerName.split(" ");
			if(str.length == 2){
				criteria.setFirstEnName(str[0]);
				criteria.setLastEnName(str[1]);
			}
		}

	}

	public static boolean validateName(String str) {

		  return str.matches("^[\\u4E00-\\u9FA5a-zA-Z| · | .|/]{1,20}$");

		 }

	public static boolean isEnglish(String str) {

		  return str.matches("^[a-zA-Z | · | .|\\/]{1,40}$");

		 }

	public static boolean isPhone(String str) {

		Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^(?:13\\d|14\\d|15\\d|18\\d)\\d{5}(\\d{3}|\\*{3})$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;

		 }

}
