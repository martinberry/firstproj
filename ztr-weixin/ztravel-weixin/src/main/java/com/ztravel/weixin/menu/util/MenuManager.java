package com.ztravel.weixin.menu.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.ztravel.common.holder.WechatAccountHolder;
import com.ztravel.common.util.WebEnv;
import com.ztravel.weixin.menu.pojo.Button;
import com.ztravel.weixin.menu.pojo.CommonButton;
import com.ztravel.weixin.menu.pojo.ComplexButton;
import com.ztravel.weixin.menu.pojo.Menu;

public class MenuManager {

//	private static String DOMAIN = "wx.extdev.travelzen.cn%2Fztravel-front-weixin";//op环境域名
	private static final String DOMAIN = WebEnv.get("server.path.wxServer", "") ;

	/**
	 * 组装菜单数据
	 *
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static Menu getMenu() throws UnsupportedEncodingException {
		CommonButton btn11 = new CommonButton();
		btn11.setName("上海-西班牙");
		btn11.setType("view");
		btn11.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?"
				+ "appid=" + WechatAccountHolder.APP_ID
				+ "&redirect_uri="+URLEncoder.encode(DOMAIN, "utf-8")+ "%2FweixinOpenIdServlet"
				+ "&response_type=code"
				+ "&scope=snsapi_base"
				+ "&state=111#wechat_redirect");

		CommonButton btn12 = new CommonButton();
		btn12.setName("上海-葡萄牙");
		btn12.setType("view");
		btn12.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?"
				+ "appid=" + WechatAccountHolder.APP_ID
				+ "&redirect_uri=http%3A%2F%2F" + DOMAIN + "%2FweixinOpenIdServlet"
				+ "&response_type=code"
				+ "&scope=snsapi_base"
				+ "&state=112#wechat_redirect");

		CommonButton btn13 = new CommonButton();
		btn13.setName("上海-越南");
		btn13.setType("view");
		btn13.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?"
				+ "appid=" + WechatAccountHolder.APP_ID
				+ "&redirect_uri=http%3A%2F%2F" + DOMAIN + "%2FweixinOpenIdServlet"
				+ "&response_type=code"
				+ "&scope=snsapi_base"
				+ "&state=113#wechat_redirect");

		CommonButton btn14 = new CommonButton();
		btn14.setName("北京-西班牙");
		btn14.setType("view");
		btn14.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?"
				+ "appid=" + WechatAccountHolder.APP_ID
				+ "&redirect_uri=http%3A%2F%2F" + DOMAIN + "%2FweixinOpenIdServlet"
				+ "&response_type=code"
				+ "&scope=snsapi_base"
				+ "&state=114#wechat_redirect");

		CommonButton btn15 = new CommonButton();
		btn15.setName("北京-葡萄牙");
		btn15.setType("view");
		btn15.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?"
				+ "appid=" + WechatAccountHolder.APP_ID
				+ "&redirect_uri=http%3A%2F%2F" + DOMAIN + "%2FweixinOpenIdServlet"
				+ "&response_type=code"
				+ "&scope=snsapi_base"
				+ "&state=115#wechat_redirect");

		//跳转至订单列表页
		CommonButton btn21 = new CommonButton();
		btn21.setName("我的订单");
		btn21.setType("view");
		btn21.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?"
		+ "appid=" + WechatAccountHolder.APP_ID
		+ "&redirect_uri=http%3A%2F%2F" + DOMAIN + "%2FweixinOpenIdServlet"
		+ "&response_type=code"
		+ "&scope=snsapi_base"
		+ "&state=121#wechat_redirect");

		//跳转至电子钱包首页
		CommonButton btn22 = new CommonButton();
		btn22.setName("电子钱包");
		btn22.setType("view");
		btn22.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?"
		+ "appid=" + WechatAccountHolder.APP_ID
		+ "&redirect_uri=http%3A%2F%2F" + DOMAIN + "%2FweixinOpenIdServlet"
		+ "&response_type=code"
		+ "&scope=snsapi_base"
		+ "&state=122#wechat_redirect");

		CommonButton btn23 = new CommonButton();
		btn23.setName("真旅客服");
		btn23.setType("click");
		btn23.setKey("23");

		CommonButton btn24 = new CommonButton();
		btn24.setName("关于我们");
		btn24.setType("click");
		btn24.setKey("24");

		CommonButton btn31 = new CommonButton();
		btn31.setName("色诱|巴塞罗那");
		btn31.setType("click");
		btn31.setKey("31");

		CommonButton btn32 = new CommonButton();
		btn32.setName("真旅行！越南见");
		btn32.setType("click");
		btn32.setKey("32");

//		CommonButton btn33 = new CommonButton();
//		btn33.setName("梦鸽的沙发客之旅");
//		btn33.setType("click");
//		btn33.setKey("33");

		CommonButton btn33 = new CommonButton();
		btn33.setName("更多旅行故事");
		btn33.setType("click");
		btn33.setKey("33");

		CommonButton btn34 = new CommonButton();
		btn34.setName("加入粉丝群");
		btn34.setType("click");
		btn34.setKey("34");

		CommonButton btn35 = new CommonButton();
		btn35.setName("分享人计划");
		btn35.setType("view");
		btn35.setUrl("http://" + DOMAIN + "/rl/jumpToSharePlan");

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("真旅行");
		mainBtn1.setSub_button(new CommonButton[] { btn11, btn12, btn13, btn14, btn15 });

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("真服务");
		mainBtn2.setSub_button(new CommonButton[] { btn21, btn22, btn23, btn24 });

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("真分享");
		mainBtn3.setSub_button(new CommonButton[] { btn31, btn32, btn33, btn34, btn35  });

		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

		return menu;
	}
}
