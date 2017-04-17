package com.ztravel.common.holder;

import com.travelzen.framework.config.tops.TopsConfReader;
import com.travelzen.framework.config.tops.TopsConfEnum.ConfScope;

public class WechatAccountHolder {
	
	public static final String SECRET = TopsConfReader.getConfContent("properties/wx-pay.properties", "secret", ConfScope.R) ;
	
	public static final String API_KEY = TopsConfReader.getConfContent("properties/wx-pay.properties", "api_key", ConfScope.R) ;
	
	public static final String APP_ID = TopsConfReader.getConfContent("properties/wx-pay.properties", "appid", ConfScope.R) ;
	
	public static final String MCH_ID = TopsConfReader.getConfContent("properties/wx-pay.properties", "mch_id", ConfScope.R) ;
	
	public static final String NOTIFY_URL = TopsConfReader.getConfContent("properties/wx-pay.properties", "notify_url", ConfScope.R) ;
	
}
