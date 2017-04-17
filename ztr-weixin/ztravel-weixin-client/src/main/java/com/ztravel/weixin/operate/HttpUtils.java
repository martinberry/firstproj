package com.ztravel.weixin.operate;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ztravel.weixin.common.HttpClientConnectionManager;

public class HttpUtils {

    public static CloseableHttpResponse getResponseByHttpGet(String url) throws ClientProtocolException, IOException {

        CloseableHttpResponse response = null;
        CloseableHttpClient httpclient = HttpClients.createDefault() ;
        try {
            HttpGet get = HttpClientConnectionManager.getGetMethod(url);
            response = httpclient.execute(get);
        } finally {
            httpclient.close();
        }

        return response;
    }

    public static JSONObject getJsonByHttpGet(String url) throws ClientProtocolException, IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault() ;
        JSONObject object = new JSONObject() ;
        try {
            HttpGet get = HttpClientConnectionManager.getGetMethod(url);
            CloseableHttpResponse response = httpclient.execute(get);
            try {
                String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
                object = JSON.parseObject(jsonStr);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }

        return object;
    }

    public static JSONObject getJsonByHttpPost(String url, String params) throws ClientProtocolException, IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault() ;
        JSONObject object = new JSONObject() ;
        try {
            HttpPost httpPost = HttpClientConnectionManager.getPostMethod(url);
            httpPost.setEntity(new StringEntity(params, "UTF-8"));
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
                object = JSON.parseObject(jsonStr);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }

        return object;
    }

}
