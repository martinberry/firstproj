package com.ztravel.weixin.constant;

import java.util.HashMap;
import java.util.Map;

import com.ztravel.common.util.WebEnv;

public class ViewState {
	private static final String WXSERVER = WebEnv.get("server.path.wxServer", "")  ;
//	private static String VIEW_URL_11 = WXSERVER + "/product/weixin/detail/Z10014";
//	private static String VIEW_URL_12 = WXSERVER + "/product/weixin/detail/Z10001";
//	private static String VIEW_URL_13 = WXSERVER + "/product/weixin/detail/Z10002";
//	private static String VIEW_URL_14 = WXSERVER + "/product/weixin/detail/Z10016";
//	private static String VIEW_URL_15 = WXSERVER + "/product/weixin/detail/Z10004";

	private static String VIEW_URL_11 = WXSERVER + "/product/weixin/detail/Z10256";
	private static String VIEW_URL_12 = WXSERVER + "/product/weixin/detail/Z10285";
	private static String VIEW_URL_13 = WXSERVER + "/product/weixin/detail/Z10271";
	private static String VIEW_URL_14 = WXSERVER + "/product/weixin/detail/Z10278";
	private static String VIEW_URL_15 = WXSERVER + "/product/weixin/detail/Z10268";

	private static String VIEW_URL_21 = WXSERVER + "/order/weixin/list";
	private static String VIEW_URL_22 = WXSERVER + "/accountBalance/index";

	@SuppressWarnings("serial")
	public final static Map<String, String> viewUrlMap = new HashMap<String, String>() {{

		put("111", VIEW_URL_11);
		put("112", VIEW_URL_12);
		put("113", VIEW_URL_13);
		put("114", VIEW_URL_14);
		put("115", VIEW_URL_15);
		put("121", VIEW_URL_21);
		put("122", VIEW_URL_22);

	}};


}
