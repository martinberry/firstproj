package com.ztravel.sso.dict;

import com.ztravel.common.util.WebEnv;

public class SSOConstants {

	public static final String SSO_SERVER = WebEnv.get("server.host.ssoServer", "") ;

	public static final String MEMBER_SERVER = WebEnv.get("server.path.memberServer", "") ;
	
	public static final String WX_SERVER = WebEnv.get("server.path.wxServer", "") ;

	public static final String MEMBER_LOGIN_PAGE = MEMBER_SERVER + "/home" ;
	
	public static final String WXMEMBER_LOGIN_PAGE = WX_SERVER + "/rl/torl" ;
}
