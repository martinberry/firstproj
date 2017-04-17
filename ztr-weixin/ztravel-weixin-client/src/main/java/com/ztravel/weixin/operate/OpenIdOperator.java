package com.ztravel.weixin.operate;

import java.io.IOException;

import org.apache.http.ParseException;
import org.slf4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.config.tops.TopsConfReader;
import com.travelzen.framework.config.tops.TopsConfEnum.ConfScope;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.holder.WechatAccountHolder;

public class OpenIdOperator {

    private static final String OAUTH2_ACCESS_TOKEN_URL = TopsConfReader.getConfContent("properties/wx-mp.properties", "OAUTH2_ACCESS_TOKEN_URL", ConfScope.R) ;

    private static final String USERINFO_URL = TopsConfReader.getConfContent("properties/wx-mp.properties", "USERINFO_URL", ConfScope.R) ;

    private static final String USER_INFO_URL = TopsConfReader.getConfContent("properties/wx-mp.properties", "USER_INFO_URL", ConfScope.R) ;

    private static final String USER_GET_URL = TopsConfReader.getConfContent("properties/wx-mp.properties", "USER_GET_URL", ConfScope.R) ;

    private static final String USER_INFO_BATCHGET_URL = TopsConfReader.getConfContent("properties/wx-mp.properties", "USER_INFO_BATCHGET_URL", ConfScope.R) ;

	private static Logger logger = RequestIdentityLogger.getLogger(OpenIdOperator.class);

	/**
     * 获取accessToken，通过code换取网页授权access_token。注：与基础支持中的access_token不同
     * @throws IOException
     * @throws ParseException
     */
    public static JSONObject oAuthAccessToken(String code) throws ParseException, IOException {

        String url = OAUTH2_ACCESS_TOKEN_URL.replace("#APPID#", WechatAccountHolder.APP_ID).replace("#SECRET#", WechatAccountHolder.SECRET).replace("#CODE#", code);
        logger.info("获取openid时请求调用的URL为：" + url);
        JSONObject jsonObj = HttpUtils.getJsonByHttpGet(url);
        logger.info("获取openid时得到微信的返回JSON为：" + jsonObj);
        return jsonObj;
    }

    /**
     * 获取用户信息
     * @param access_token
     * @param openid
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static JSONObject getUserBasicInfo(String access_token, String openid) throws ParseException, IOException {

        String url = USERINFO_URL.replace("#ACCESS_TOKEN#", access_token).replace("#OPEN_ID#", openid);
        logger.info("获取用户信息时请求调用的URL为：" + url);
        JSONObject jsonObj = HttpUtils.getJsonByHttpGet(url);
        logger.info("获取用户信息时得到微信的返回JSON为："+jsonObj);
        return jsonObj;
    }

    /**
     * 获取用户昵称
     * @param access_token
     * @param openid
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static String getUserNickName(String access_token, String openid) throws ParseException, IOException {

        String url = USERINFO_URL.replace("#ACCESS_TOKEN#", access_token).replace("#OPEN_ID#", openid);
        logger.info("获取用户信息时请求调用的URL为：" + url);
        JSONObject jsonObj = HttpUtils.getJsonByHttpGet(url);
        logger.info("获取用户信息时得到微信的返回JSON为："+jsonObj);
        return jsonObj.getString("nickname");
    }

    /**
     * 获取用户基本信息(UnionID机制)
     * @param access_token
     * @param openid
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static JSONObject getUserInfo(String access_token, String openid) throws ParseException, IOException {

        String url = USER_INFO_URL.replace("#ACCESS_TOKEN#", access_token).replace("#OPEN_ID#", openid);
        logger.info("获取用户基本信息(UnionID机制)时请求调用的URL为：" + url);
        JSONObject jsonObj = HttpUtils.getJsonByHttpGet(url);
        logger.info("获取用户基本信息(UnionID机制)时得到微信的返回JSON为："+jsonObj);
        return jsonObj;
    }

    /**
     * 获取用户列表。注：粉丝不到10000，将NEXT_OPENID默认设置为空
     * @param access_token
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static JSONObject getUserList(String access_token) throws ParseException, IOException {

        String url = USER_GET_URL.replace("#ACCESS_TOKEN#", access_token).replace("#NEXT_OPENID#", "");
        logger.info("获取用户列表时请求调用的URL为：" + url);
        JSONObject jsonObj = HttpUtils.getJsonByHttpGet(url);
        logger.info("获取用户列表时得到微信的返回JSON为："+jsonObj);
        return jsonObj;
    }

    /**
     * 批量获取用户基本信息。注：最多支持一次拉取100条
     * @param access_token
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static JSONObject batchGetUserInfo(String params, String access_token) throws ParseException, IOException {

        String url = USER_INFO_BATCHGET_URL.replace("#ACCESS_TOKEN#", access_token);
        logger.info("批量获取用户基本信息时请求调用的URL为：" + url);
        JSONObject jsonObj = HttpUtils.getJsonByHttpPost(url, params);
        logger.info("批量获取用户基本信息时得到微信的返回JSON为："+jsonObj);
        return jsonObj;
    }

}
