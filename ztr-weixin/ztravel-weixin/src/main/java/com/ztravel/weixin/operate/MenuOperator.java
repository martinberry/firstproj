package com.ztravel.weixin.operate;

import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.config.tops.TopsConfReader;
import com.travelzen.framework.config.tops.TopsConfEnum.ConfScope;
import com.ztravel.weixin.menu.pojo.Menu;
import com.ztravel.weixin.menu.util.MenuManager;

public class MenuOperator {

    private static final String CREATE_MENU_URL = TopsConfReader.getConfContent("properties/wx-mp.properties", "CREATE_MENU_URL", ConfScope.R) ;

    private static final String GET_MENU_URL = TopsConfReader.getConfContent("properties/wx-mp.properties", "GET_MENU_URL", ConfScope.R) ;

    private static final String DELETE_MENU_URL = TopsConfReader.getConfContent("properties/wx-mp.properties", "DELETE_MENU_URL", ConfScope.R) ;

    /**
     * 创建菜单
     */
    public static JSONObject createMenu(String params, String accessToken) throws Exception {

        String url = CREATE_MENU_URL.replace("#ACCESS_TOKEN#", accessToken);
        JSONObject jsonObj = HttpUtils.getJsonByHttpPost(url, params);

        return jsonObj;
    }

    /**
     * 查询菜单
     */
    public static JSONObject getMenuInfo(String accessToken) throws Exception {

        String url = GET_MENU_URL.replace("#ACCESS_TOKEN#", accessToken);
        JSONObject jsonObj = HttpUtils.getJsonByHttpGet(url);

        return jsonObj;
    }

    /**
     * 删除菜单
     */
    public static JSONObject deleteMenuInfo(String accessToken) throws Exception {

        String url = DELETE_MENU_URL.replace("#ACCESS_TOKEN#", accessToken);
        return HttpUtils.getJsonByHttpGet(url);
    }

    /**
     * 主要用于微信自定义菜单创建菜单之用（后台操作功能实现之前的临时办法）
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static void main(String[] args) throws ClientProtocolException, IOException {

    	String accessToken = "6QqolVKc47kL42YzADkv0Ouf-sc8He8dV31Z6tHXYwXhFfzXHVFnok1ionZSrvAfYIQCIu-9SuXG-qt3lVjpLoivh6UV4Sk7g6pSEPgTQLI";
		try {
//			accessToken = AccessTokenThread.getAccessToken();
		} catch (ParseException e) {
			e.printStackTrace();
		}

    	Menu menu = MenuManager.getMenu();
    	 String url = CREATE_MENU_URL.replace("#ACCESS_TOKEN#", accessToken);
    	 String jsonStr = JSONObject.toJSONString(menu).toString();
    	 HttpUtils.getJsonByHttpPost(url, jsonStr);

    }

}
