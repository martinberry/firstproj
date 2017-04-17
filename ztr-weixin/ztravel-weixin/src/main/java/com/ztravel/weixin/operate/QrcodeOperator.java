package com.ztravel.weixin.operate;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.config.tops.TopsConfReader;
import com.travelzen.framework.config.tops.TopsConfEnum.ConfScope;

public class QrcodeOperator {

    private static final String QRCODE_CREATE_URL = TopsConfReader.getConfContent("properties/wx-mp.properties", "QRCODE_CREATE_URL", ConfScope.R) ;

    private static final String SHOWQRCODE_URL = TopsConfReader.getConfContent("properties/wx-mp.properties", "SHOWQRCODE_URL", ConfScope.R) ;

    private static final String SHORTURL_URL = TopsConfReader.getConfContent("properties/wx-mp.properties", "SHORTURL_URL", ConfScope.R) ;

    /**
     * 创建二维码ticket
     * @param token
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static String createTicket(String params, String token) throws ClientProtocolException, IOException {

        String url = QRCODE_CREATE_URL.replace("#ACCESS_TOKEN#", token);
        JSONObject jsonObj = HttpUtils.getJsonByHttpPost(url, params);
        System.out.println(jsonObj);
        return jsonObj.getString("ticket");

    }

    /**
     * 通过ticket换取二维码，返回为图片
     * @param ticket
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String showQrcode(String ticket) throws ClientProtocolException, IOException {
        String url = SHOWQRCODE_URL.replace("#TICKET#", ticket);
        return url;
    }

    /**
     * 长链接转短链接接口
     * @param params
     * @param token
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static JSONObject shorturl(String params, String token) throws ClientProtocolException, IOException {

        String url = SHORTURL_URL.replace("#ACCESS_TOKEN#", token);
        JSONObject jsonObj = HttpUtils.getJsonByHttpPost(url, params);
        System.out.println(jsonObj);
        return jsonObj;

    }

    public static void main (String[] args) throws ClientProtocolException, IOException {
        String token = "BwEB6-guNwk-uxfRYPpjuIUfCH8knBEm0IlJ31U73IaZ_UHbl-a9o5USPQoU-O9iTGdwq-8xSsX4KsCOLRtD_rzAaAjQlyUnOmoN7GWSLZY";
        String params = "{\"expire_seconds\": 604800, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 999}}}";

        createTicket(params, token);
    }

//    public static void main (String[] args) throws ClientProtocolException, IOException {
//        String ticket = "gQF28DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xLzNreDRoM1Rremp5M2l1NnY2V0o2AAIEL/wIVgMEgDoJAA==";
//
//        showQrcode(ticket);
//    }

}
