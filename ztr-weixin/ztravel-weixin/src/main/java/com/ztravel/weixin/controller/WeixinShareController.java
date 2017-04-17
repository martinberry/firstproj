package com.ztravel.weixin.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.entity.AjaxJsonpResponse;
import com.ztravel.common.util.IdGeneratorUtil;
import com.ztravel.weixin.activity.entity.GameRecordEntity;
import com.ztravel.weixin.activity.service.IGameRecordService;
import com.ztravel.weixin.operate.OpenIdOperator;
import com.ztravel.weixin.servlet.AccessTokenThread;
import com.ztravel.weixin.util.WeixinShareUtil;

@Controller
@RequestMapping("/weixinShareController")
public class WeixinShareController {

	private static Logger logger = RequestIdentityLogger.getLogger(WeixinShareController.class);

	@Resource
	IGameRecordService gameRecordServiceImpl;

	@Resource
	private IdGeneratorUtil idGeneratorUtil ;

	@ResponseBody
	@RequestMapping(value = "/share", method = RequestMethod.GET)
	public String share(HttpServletRequest request, String callback, String url) throws ClientProtocolException, IOException {

		String jsapi_ticket = AccessTokenThread.getJsapiTicket();

        Map<String, String> shareMap = WeixinShareUtil.sign(jsapi_ticket, url);

        return AjaxJsonpResponse.getInstance(callback, AjaxResponse.instance("msg", JSON.toJSONString(shareMap))).toString();

	}

	@ResponseBody
	@RequestMapping(value = "/getUserNickName", produces="text/html;charset=UTF-8", method = RequestMethod.GET)
	public String getUserNickNameFromWeixin(HttpServletRequest request, String code, String state) throws ParseException, IOException {

		logger.info("请求的参数code为：" + code + ",state为：" + state + ".");

		JSONObject oAuthAccessToken = OpenIdOperator.oAuthAccessToken(code);

		//获取得到access_token
		String access_token = oAuthAccessToken.getString("access_token");
		logger.info("获得的access_token为：" + access_token);
		//获取得到openid
		String openid = oAuthAccessToken.getString("openid");
		logger.info("获得的openid为：" + openid);

		String nickName = OpenIdOperator.getUserNickName(access_token, openid);

		String returnStr = "<div id =\"userNickName\" style=\"color:red;font-size:80px;\">" + nickName + "</div>";
		logger.info("返回的html结构为：" + returnStr);

		return returnStr;

	}

	@ResponseBody
	@RequestMapping(value = "/createUserNickName", method = RequestMethod.GET)
	public String createUserNickNameFromWeixin(HttpServletRequest request, String code, String state) throws ParseException, IOException {

		logger.info("请求的参数code为：" + code + ",state为：" + state + ".");

		JSONObject oAuthAccessToken = OpenIdOperator.oAuthAccessToken(code);

		//获取得到access_token
		String access_token = oAuthAccessToken.getString("access_token");
		logger.info("获得的access_token为：" + access_token);
		//获取得到openid
		String openid = oAuthAccessToken.getString("openid");
		logger.info("获得的openid为：" + openid);

		String nickName = OpenIdOperator.getUserNickName(access_token, openid);

		return nickName;

	}

	@ResponseBody
	@RequestMapping(value = "/saveGameRecord", method = RequestMethod.GET)
	public String saveGameRecord(HttpServletRequest request) throws Exception {
		String gameId = request.getParameter("gameId") ;
		String gameResult = request.getParameter("gameResult") ;
		String gameScore = request.getParameter("gameScore") ;

		GameRecordEntity gameRecordEntity = new GameRecordEntity();
		gameRecordEntity.setId(idGeneratorUtil.getGameRecordId());
		gameRecordEntity.setCreateTime(new DateTime());
		gameRecordEntity.setGameId(gameId);
		gameRecordEntity.setGameResult(gameResult);
		gameRecordEntity.setGameScore(Double.parseDouble(gameScore));
		gameRecordServiceImpl.saveGameRecord(gameRecordEntity);

		return "ok";

	}



}
