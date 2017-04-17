package com.ztravel.common.valid;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ztravel.common.constants.ResponseConstants;

public class CommonValidateUtil {

	private static final String MOBILE_RULE = "^[1][3,4,5,7,8][0-9]{9}$" ;

	public static String validateMobile(String mobile) throws Exception {
		if(mobile == null || mobile.trim().length() == 0);
		Pattern p = null ;
		Matcher m = null ;
		String error_code = null ;
		p = Pattern.compile(MOBILE_RULE);
		error_code = ResponseConstants.MEMB_MOBILE_ILLEGE_CODE ;
		m = p.matcher(mobile) ;
        if(!m.matches()){
        	Exception exception = new Exception(error_code) ;
        	throw exception ;
        }else{
        	return mobile.trim();
        }
	}

}
