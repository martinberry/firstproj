package com.ztravel.reuse.member.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wanhaofan
 * */
public class MemberLoginValidation {

	private static final String LOGIN_RIGHT_RULE = "^[a-zA-Z0-9_\\-@\\.]+$" ;


	public static Boolean validate(String str) {
		Pattern p = Pattern.compile(LOGIN_RIGHT_RULE) ;
		Matcher m = p.matcher(str);
        return m.matches() ;
	}

//	public static void main(String args[]){
//		Pattern p = Pattern.compile(LOGIN_RIGHT_RULE) ;
//		Matcher m = p.matcher("haofan_wan@163.com");
//		System.out.println(m.matches()) ;
//	}

}
