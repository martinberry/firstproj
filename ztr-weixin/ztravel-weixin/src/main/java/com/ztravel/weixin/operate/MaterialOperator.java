package com.ztravel.weixin.operate;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.config.tops.TopsConfReader;
import com.travelzen.framework.config.tops.TopsConfEnum.ConfScope;
import com.ztravel.weixin.servlet.AccessTokenThread;

public class MaterialOperator {

    private static final String BATCHGET_MATERIAL_URL = TopsConfReader.getConfContent("properties/wx-mp.properties", "BATCHGET_MATERIAL_URL", ConfScope.R) ;

    private static final String GET_MATERIAL_URL = TopsConfReader.getConfContent("properties/wx-mp.properties", "GET_MATERIAL_URL", ConfScope.R) ;

    private static final String GET_MATERIAL_COUNT_URL = TopsConfReader.getConfContent("properties/wx-mp.properties", "GET_MATERIAL_COUNT_URL", ConfScope.R) ;

    private static final String ADD_NEWS_URL = TopsConfReader.getConfContent("properties/wx-mp.properties", "ADD_NEWS_URL", ConfScope.R) ;

    /**
     * 新增永久图文素材
     * @param params
     * @param accessToken
     * @return
     * @throws Exception
     */
    public static JSONObject addNews(String params, String accessToken) throws Exception {

        String url = ADD_NEWS_URL.replace("#ACCESS_TOKEN#", accessToken);
        JSONObject jsonObj = HttpUtils.getJsonByHttpPost(url, params);

        return jsonObj;
    }

    /**
     * 获取素材列表
     * @param params
     * @param accessToken
     * @return
     * @throws Exception
     */
    public static JSONObject getBatchMaterial(String params, String accessToken) throws Exception {

        String url = BATCHGET_MATERIAL_URL.replace("#ACCESS_TOKEN#", accessToken);
        JSONObject jsonObj = HttpUtils.getJsonByHttpPost(url, params);

        return jsonObj;
    }

    /**
     * 按素材ID获取素材
     * @param params
     * @param accessToken
     * @return
     * @throws Exception
     */
    public static JSONObject getMaterial(String params, String accessToken) throws Exception {

        String url = GET_MATERIAL_URL.replace("#ACCESS_TOKEN#", accessToken);
        JSONObject jsonObj = HttpUtils.getJsonByHttpPost(url, params);

        return jsonObj;
    }

    /**
     * 获取素材总数
     * @param accessToken
     * @return
     * @throws Exception
     */
    public static JSONObject getMaterialCount(String accessToken) throws Exception {

        String url = GET_MATERIAL_COUNT_URL.replace("#ACCESS_TOKEN#", accessToken);
        JSONObject jsonObj = HttpUtils.getJsonByHttpGet(url);

        return jsonObj;
    }

    /**
     * 主要用于获取微信素材模板id
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

    	String accessToken = "IbsvjpoTWCPViNB-OvzvsqkjELPASQo7XFg_L_edQuIBNt-6l2sw2kGzIQDbnx30xORYnyzZpadcM2QmIvU2MNnP2OXR9JooO3zUtqXwEwQ";
//
//    	String jsonStr = "{\"media_id\":\"ii_Lsu30b3-sguRfzznInx3L-6_rRDWHO_IfYh5KYwg\"}";
//        getMaterial(jsonStr, accessToken);

        String jsonStr = "{\"type\":\"image\","
                + "\"offset\":\"0\","
                + "\"count\":\"1\"}";

        getBatchMaterial(jsonStr, accessToken);

    }

}
