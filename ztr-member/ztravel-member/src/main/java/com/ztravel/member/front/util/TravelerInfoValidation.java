package com.ztravel.member.front.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.constants.ResponseConstants;

/**
 * @author bzhou
 * */
public class TravelerInfoValidation {

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(TravelerInfoValidation.class);

	private static final String FIRSTNAME_RULE = "^[a-zA-Z|\u4e00-\u9fa5]{1,20}$" ;

	private static final String LASTNAME_RULE = "^[a-zA-Z|\u4e00-\u9fa5]{1,20}$" ;

	private static final String WHOLECHNAME_RULE = "^[a-zA-Z|\u4e00-\u9fa5]{1,40}$";

	private static final String WHOLEENNAME_RULE = "[A-Za-z0-9]{1,60}";

	private static final String PASSPORT_RULE="[A-Za-z0-9]{0,20}";

	private static final String GANGAOPASS_RULE="^[a-zA-Z\u4e00-\u9fa5][0-9]{8}$";

	private static final String  CREDIT_RULE= "(^\\d{15}$)|(^\\d{17}(\\d|X|x)$)";

//	private static final String  CREDIT_RULE="^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$";

	private static final String MOBILE_RULE = "^[1][3,4,5,7,8][0-9]{9}$" ;
	private static final String EMAIL_RULE = "^((\\w-*\\.*)+@(\\w-?)+(\\.\\w{2,})+){0,50}$" ;

	private static final String ILLEGER_STR = "!@#$%^&*()" ;

	public static final String MOBILE = "MOBILE" ;
	public static final String EMAIL = "EMAIL" ;

	public static final String FIRSTNAME = "FIRSTNAME" ;

	public static final String LASTNAME = "LASTNAME" ;

	public static final String WHOLEENAME = "WHOLEENAME" ;

	public static final String WHOLECHNAME = "WHOLECHNAME" ;

	public static final String ENNAME = "ENNAME" ;

	public static final String CREDIT = "CREDIT" ;

	public static final String PASSPORT = "PASSPORT" ;

	public static final String GANGAOPASS = "GANGAOPASS" ;

	public static final String CREDIT_CN = "身份证" ;

	public static final String PASSPORT_CN = "护照" ;

	public static final String GANGAOPASS_CN = "通行证" ;


	public static String validate(String str, String type) throws Exception {
		if(str == null || str.length() == 0) str = ILLEGER_STR ;
		Pattern p = null ;
		Matcher m = null ;
		String error_code = null ;
		String error_msg = null ;
		switch(type){
		case MOBILE :
			p = Pattern.compile(MOBILE_RULE);
			error_code = ResponseConstants.TRAVELERINFO_MOBILE_ILLEGE_CODE;
			error_msg = ResponseConstants.TRAVELERINFO_MOBILE_ILLEGE_MSG ;
			break ;
		case EMAIL :
			p = Pattern.compile(EMAIL_RULE);
			error_code = ResponseConstants.TRAVELERINFO_EMAIL_ILLEGE_CODE ;
			error_msg = ResponseConstants.TRAVELERINFO_EMAIL_ILLEGE_MSG ;
			break ;
		case FIRSTNAME :
			p = Pattern.compile(FIRSTNAME_RULE);
			error_code = ResponseConstants.TRAVELERINFO_FIRSTNAME_ILLEGE_CODE;
			error_msg = ResponseConstants.TRAVELERINFO_LASTNAME_ILLEGE_MSG ;
			break ;
		case LASTNAME :
			p = Pattern.compile(LASTNAME_RULE);
			error_code = ResponseConstants.TRAVELERINFO_LASTNAME_ILLEGE_CODE;
			error_msg = ResponseConstants.TRAVELERINFO_LASTNAME_ILLEGE_MSG ;
			break ;
//		case WHOLECHNAME:
//			p = Pattern.compile(WHOLECHNAME_RULE);
//			error_code = ResponseConstants.TRAVELERINFO_WHOLECHNAME_ILLEGE_CODE;
//			error_msg = ResponseConstants.TRAVELERINFO_WHOLECHNAME_ILLEGE_MSG ;
//			break ;
//		case WHOLEENAME:
//			p = Pattern.compile(WHOLEENNAME_RULE);
//			error_code = ResponseConstants.TRAVELERINFO_WHOLEENNAME_ILLEGE_CODE;
//			error_msg = ResponseConstants.TRAVELERINFO_WHOLEENNAME_ILLEGE_MSG ;
//			break ;
		case PASSPORT :
			p = Pattern.compile(PASSPORT_RULE);
			error_code = ResponseConstants.TRAVELERINFO_PASSPORT_ILLEGE_CODE ;
			error_msg = ResponseConstants.TRAVELERINFO_PASSPORT_ILLEGE_MSG ;
			break ;
		case GANGAOPASS :
			p = Pattern.compile( GANGAOPASS_RULE);
			error_code = ResponseConstants.TRAVELERINFO_GANGAOPASS_ILLEGE_CODE ;
			error_msg = ResponseConstants.TRAVELERINFO_GANGAOPASS_ILLEGE_MSG ;
			break ;
		case CREDIT :
			p = Pattern.compile( CREDIT_RULE);
			error_code = ResponseConstants.TRAVELERINFO_CREDIT_ILLEGE_CODE ;
			error_msg = ResponseConstants.TRAVELERINFO_CREDIT_ILLEGE_MSG ;
			break ;
//			if(!ValidationUtils.validateID(str)){
//				error_code = ResponseConstants.TRAVELERINFO_CREDIT_ILLEGE_CODE ;
//				error_msg = ResponseConstants.TRAVELERINFO_CREDIT_ILLEGE_MSG ;
//				Exception exception = new Exception(error_code) ;
//	        	LOGGER.error(error_msg, exception);
//	        	throw exception ;
//			}
//			break ;
		default : break ;
		}
		if(null!=p){
			m = p.matcher(str) ;
	        if(!m.matches()){
	        	Exception exception = new Exception(error_code) ;
	        	LOGGER.error(error_msg, exception);
	        	throw exception ;
	        }
		}
		return str.trim();

	}

}
