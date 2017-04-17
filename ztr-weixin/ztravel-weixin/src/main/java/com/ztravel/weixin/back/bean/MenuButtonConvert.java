package com.ztravel.weixin.back.bean;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.joda.time.DateTime;

import com.site.lookup.util.StringUtils;
import com.ztravel.common.holder.WechatAccountHolder;
import com.ztravel.common.util.WebEnv;
import com.ztravel.weixin.back.constant.WxMenuConst;
import com.ztravel.weixin.back.entity.MenuButton;
import com.ztravel.weixin.menu.pojo.CommonButton;

public class MenuButtonConvert {
	private static final String DOMAIN = WebEnv.get("server.path.wxServer", "") ;

	public static CommonButton menuButton2Button(MenuButton menuButton) throws Exception{
		CommonButton button = new CommonButton();
		button.setName(menuButton.getName());
		String type = menuButton.getType();
		if(type != null){
			button.setType(type);
			if(type.equals("click")){
				button.setKey(menuButton.getMediaId());
			}else if(type.equals("view")){
				button.setUrl(menuButton.getUrl());
			}
		}
		return button;
	}

	private static String buildUrlByState(String state) throws UnsupportedEncodingException {
		return "https://open.weixin.qq.com/connect/oauth2/authorize?"
				+ "appid=" + WechatAccountHolder.APP_ID
				+ "&redirect_uri="+URLEncoder.encode(DOMAIN, "utf-8")+ "%2FweixinOpenIdServlet"
				+ "&response_type=code"
				+ "&scope=snsapi_base"
				+ "&state="+state+"#wechat_redirect";
	}

	public static MenuButtonVo menuButton2MenuButtonVo(MenuButton s) throws Exception{
		MenuButtonVo t = new MenuButtonVo();
		t.setButtonLevel(s.getButtonLevel());
		t.setKey(s.getKey());
		t.setName(s.getName());
		t.setMediaId(s.getMediaId());
		t.setPriority(s.getPriority());
		t.setState(s.getState());
		t.setType(s.getType());
		t.setButtonLevel(s.getButtonLevel());
		t.setUrl(s.getUrl());
		return t;
	}

	public static ComplexMenuButton menuButton2ComplexMenuButton(MenuButton s) throws Exception{
		ComplexMenuButton t = new ComplexMenuButton();
		t.setButtonLevel(s.getButtonLevel());
		t.setKey(s.getKey());
		t.setName(s.getName());
		t.setMediaId(s.getMediaId());
		t.setPriority(s.getPriority());
		t.setState(s.getState());
		t.setType(s.getType());
		t.setButtonLevel(s.getButtonLevel());
		t.setUrl(s.getUrl());
		return t;
	}

	public static MenuButton buildMenuButtonByMenuButtonVo(
			MenuButton t, MenuButtonVo s) throws UnsupportedEncodingException {
		t.setKey(s.getKey());
		t.setName(s.getName());
		t.setMediaId(s.getMediaId());
		t.setPriority(s.getPriority());
		t.setState(s.getState());
		String type = s.getType();
		t.setType(type);
		if(StringUtils.isNotEmpty(type) && type.equals("view")){
			if(StringUtils.isNotEmpty(s.getUrl())){
				t.setUrl(getMenuUrl(s, t));
			}
		}
		t.setViewStateId(s.getViewState());
		t.setCreateTime(DateTime.now());
		return t;
	}

	private static String getMenuUrl(MenuButtonVo menuButtonVo, MenuButton menuButton) throws UnsupportedEncodingException{
		String url = menuButtonVo.getUrl();
		if(menuButtonVo.getUrl().endsWith(WxMenuConst.ZHENLX_SUFFIX)){
			url = buildMenuUrl(menuButtonVo);
			menuButton.setWxUrlFlag(WxMenuConst.ONE_STR);
		}
		return url;
	}

	private static String buildMenuUrl(MenuButtonVo menuButtonVo) throws UnsupportedEncodingException {
		String url = menuButtonVo.getUrl();
		String viewState =  menuButtonVo.getViewState();
		if(StringUtils.isNotEmpty(viewState)){
			url = buildUrlByState(viewState);
		}
		return url;
	}
}
