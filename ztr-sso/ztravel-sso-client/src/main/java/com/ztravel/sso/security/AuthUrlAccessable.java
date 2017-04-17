package com.ztravel.sso.security;

import java.util.Properties;

import com.travelzen.framework.config.tops.TopsConfReader;
import com.travelzen.framework.config.tops.TopsConfEnum.ConfScope;

public class AuthUrlAccessable {
	public static final String[] NEED_LOGIN ;
	public static final String[] NEED_LOGOUT ;
	
	public static final String[] WXNEED_LOGIN ;
	public static final String[] WXNEED_LOGOUT ;
	static{
		Properties props = TopsConfReader.getConfProperties("properties/auth.properties", ConfScope.R) ;
		NEED_LOGIN = props.getProperty("needLogin").split(",") ;
		NEED_LOGOUT = props.getProperty("needLogout").split(",") ;
		
		Properties wxprops = TopsConfReader.getConfProperties("properties/wxauth.properties", ConfScope.R) ;
		WXNEED_LOGIN = wxprops.getProperty("needLogin").split(",") ;
		WXNEED_LOGOUT = wxprops.getProperty("needLogout").split(",") ;
	}
}
