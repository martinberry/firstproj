package com.ztravel.weixin.operate;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.config.tops.TopsConfReader;
import com.travelzen.framework.config.tops.TopsConfEnum.ConfScope;

public class TemplateOperator {

    private static final String SET_INDUSTRY_URL = TopsConfReader.getConfContent("properties/wx-mp.properties", "SET_INDUSTRY_URL", ConfScope.R) ;

    private static final String ADD_TEMPLATE_URL = TopsConfReader.getConfContent("properties/wx-mp.properties", "ADD_TEMPLATE_URL", ConfScope.R) ;

    private static final String SEND_TEMPLATE_URL = TopsConfReader.getConfContent("properties/wx-mp.properties", "SEND_TEMPLATE_URL", ConfScope.R) ;

	/**
     * 设置所属行业
     */
    public static String setIndustry(String params, String accessToken) throws Exception {

        String url = SET_INDUSTRY_URL.replace("#ACCESS_TOKEN#", accessToken);
        JSONObject jsonObj = HttpUtils.getJsonByHttpPost(url, params);

        return jsonObj.getString("errmsg");
    }

    /**
     * 获取模板ID
     * @param params
     * @param accessToken
     * @return
     * @throws Exception
     */
    public static String getTemplateId(String params, String accessToken) throws Exception {

        String url = ADD_TEMPLATE_URL.replace("#ACCESS_TOKEN#", accessToken);
        JSONObject jsonObj = HttpUtils.getJsonByHttpPost(url, params);

        String errmsg =  jsonObj.getString("errmsg");
        if (errmsg.equals("ok")) {
            return jsonObj.getString("template_id");
        }
        return jsonObj.getString("errcode");
    }

    /**
     * 发送模板消息
     * @param params
     * @param accessToken
     * @return
     * @throws Exception
     */
    public static String sendTemplateMessage(String params, String accessToken) throws Exception {

        String url = SEND_TEMPLATE_URL.replace("#ACCESS_TOKEN#", accessToken);
        JSONObject jsonObj = HttpUtils.getJsonByHttpPost(url, params);

        return jsonObj.getString("errmsg");
    }



}
