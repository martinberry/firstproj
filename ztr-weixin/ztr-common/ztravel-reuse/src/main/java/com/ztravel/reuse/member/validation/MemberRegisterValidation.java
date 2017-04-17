package com.ztravel.reuse.member.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.constants.ResponseConstants;

/**
 * @author wanhaofan
 * */
public class MemberRegisterValidation {

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(MemberRegisterValidation.class);

	private static final String MOBILE_RULE = "^[1][3,4,5,7,8][0-9]{9}$" ;

	private static final String EMAIL_RULE = "^((\\w-*\\.*)+@(\\w-?)+(\\.\\w{2,})+){0,50}$" ;

	private static final String NICKNAME_RULE = "^([0-9a-zA-Z\u4E00-\u9FA5\u002A]+)$" ;

	private static final String PASSWORD_RULE = "^([[a-zA-Z0-9~!@#$%^&*]]{8,28})$" ;

	private static final String VERIFYCODE_RULE = "^([0-9]{6})$" ;

	private static final String ILLEGER_STR = "!@#$%^&*()" ;

	public static final String MOBILE = "MOBILE" ;
	public static final String EMAIL = "EMAIL" ;
	public static final String NICKNAME = "NICKNAME" ;

	public static String validate(String str, String type) throws Exception {
		if(str == null || str.trim().length() == 0) str = ILLEGER_STR ;
		Pattern p = null ;
		Matcher m = null ;
		String error_code = null ;
		String error_msg = null ;
		switch(type){
		case MOBILE :
			p = Pattern.compile(MOBILE_RULE);
			error_code = ResponseConstants.MEMB_MOBILE_ILLEGE_CODE ;
			error_msg = ResponseConstants.MEMB_MOBILE_ILLEGE_MSG ;
			break ;
		case EMAIL :
			p = Pattern.compile(EMAIL_RULE);
			error_code = ResponseConstants.MEMB_EMAIL_ILLEGE_CODE ;
			error_msg = ResponseConstants.MEMB_EMAIL_ILLEGE_MSG ;
			break ;
		case NICKNAME :
			p = Pattern.compile(NICKNAME_RULE);
			error_code = ResponseConstants.MEMB_NICKNAME_ILLEGE_CODE ;
			error_msg = ResponseConstants.MEMB_NICKNAME_ILLEGE_MSG ;
			break ;
		default : break ;
		}
		m = p.matcher(str) ;
        if(!m.matches()){
        	Exception exception = new Exception(error_code) ;
        	LOGGER.error(error_msg, exception);
        	throw exception ;
        }else{
        	return str.trim();
        }
	}

	public static void validatePassword(String password) throws Exception{
		Pattern p = Pattern.compile(PASSWORD_RULE);
		Matcher m = p.matcher(password);
		if(!m.matches()){
			Exception exception = new Exception(ResponseConstants.MEMB_PASSWORD_ERROR_CODE) ;
        	LOGGER.error(ResponseConstants.MEMB_PASSWORD_ERROR_MSG, exception);
        	throw exception ;
		}
	}

	public static void validateVerifyCode(String verifyCode) throws Exception{
		Pattern p = Pattern.compile(VERIFYCODE_RULE);
		Matcher m = p.matcher(verifyCode);
		if(!m.matches()){
			Exception exception = new Exception(ResponseConstants.MEMB_VERIFYCODE_VERIFY_FAIL_CODE) ;
        	LOGGER.error(ResponseConstants.MEMB_VERIFYCODE_VERIFY_FAIL_MSG, exception);
        	throw exception ;
		}
	}

//	public static void main(String args[]){
//		Pattern p = Pattern.compile(EMAIL_RULE) ;
//		Matcher m = p.matcher("123_12.312-12@163.com");
//		System.out.println(m.matches()) ;
//	}

}
