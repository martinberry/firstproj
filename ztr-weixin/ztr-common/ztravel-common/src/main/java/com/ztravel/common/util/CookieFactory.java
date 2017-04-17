package com.ztravel.common.util;

import java.util.Properties;

import javax.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.travelzen.framework.config.tops.TopsConfEnum.ConfScope;
import com.travelzen.framework.config.tops.TopsConfReader;

/**
 * @author wanhaofan
 * */
public class CookieFactory {

	private static final Logger LOG = LoggerFactory.getLogger(CookieFactory.class);

	private static Properties pptConf = null;

	static {
		String path = null;
		try {
			path = "properties/cookies.properties";
			pptConf = TopsConfReader.getConfProperties(path, ConfScope.R);
		} catch (Exception e) {
			LOG.error("load properties R:properties/cookies.properties failed." ,e);
		}
	}

	public static Cookie buildToken(String value){
		Cookie cookie = new Cookie("token",value);
		cookie.setHttpOnly(Boolean.valueOf(pptConf.getProperty("cookie.token.httpOnly")));
		cookie.setSecure(Boolean.valueOf(pptConf.getProperty("cookie.token.secure")));
		cookie.setDomain(pptConf.getProperty("cookie.token.domain"));
		cookie.setPath(pptConf.getProperty("cookie.token.path"));
		return cookie ;
	}

	public static Cookie buildRememberMe(String value, int maxAge){
		Cookie cookie = new Cookie("account",value);
		cookie.setMaxAge(maxAge);
		cookie.setHttpOnly(Boolean.valueOf(pptConf.getProperty("cookie.account.httpOnly")));
		cookie.setSecure(Boolean.valueOf(pptConf.getProperty("cookie.account.secure")));
		cookie.setDomain(pptConf.getProperty("cookie.account.domain"));
		cookie.setPath(pptConf.getProperty("cookie.account.path"));
		return cookie ;
	}

	public static Cookie buildOperSID(String value){
		Cookie cookie = new Cookie("SID",value);
		cookie.setHttpOnly(Boolean.valueOf(pptConf.getProperty("cookie.opersid.httpOnly")));
		cookie.setSecure(Boolean.valueOf(pptConf.getProperty("cookie.opersid.secure")));
		cookie.setDomain(pptConf.getProperty("cookie.opersid.domain"));
		cookie.setPath(pptConf.getProperty("cookie.opersid.path"));
		return cookie ;
	}

	public static Cookie buildOperRememberMe(String cookieName, String value, int maxAge){
		Cookie cookie = new Cookie(cookieName, value);
		cookie.setMaxAge(maxAge);
		cookie.setHttpOnly(Boolean.valueOf(pptConf.getProperty("cookie.rememberMe.httpOnly")));
		cookie.setSecure(Boolean.valueOf(pptConf.getProperty("cookie.rememberMe.secure")));
		cookie.setDomain(pptConf.getProperty("cookie.rememberMe.domain"));
		cookie.setPath(pptConf.getProperty("cookie.rememberMe.path"));
		return cookie ;
	}
	
	public static Cookie buildWxToken(String value){
		Cookie cookie = new Cookie("token",value);
		cookie.setHttpOnly(Boolean.valueOf(pptConf.getProperty("cookie.wx.token.httpOnly")));
		cookie.setSecure(Boolean.valueOf(pptConf.getProperty("cookie.wx.token.secure")));
		cookie.setDomain(pptConf.getProperty("cookie.wx.token.domain"));
		cookie.setPath(pptConf.getProperty("cookie.wx.token.path"));
		return cookie ;
	}
}
