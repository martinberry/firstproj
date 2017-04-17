package com.ztravel.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.holder.WechatAccountHolder;
import com.ztravel.common.util.WebEnv;
import com.ztravel.weixin.util.WeixinShareUtil;

/**
 * 此Servlet用于处理微信分享功能发来的消息请求
 * @author xutian
 *
 */
@WebServlet(name = "weixinShareServlet", urlPatterns = "/weixinShareServlet")
public class WeixinShareServlet extends HttpServlet {

	private static Logger logger = RequestIdentityLogger.getLogger(WeixinServlet.class);

	private static final String wxServer = WebEnv.get("server.path.wxServer", "");

	private static final long serialVersionUID = 1L;

    public WeixinShareServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//1、获取access_token
//		String accessToken = AccessTokenThread.getAccessToken();

		//2、用第一步拿到的access_token 采用http GET方式请求获得jsapi_ticket
		String jsapi_ticket = AccessTokenThread.getJsapiTicket();

		logger.info("获取到jsapi_ticket为：{}", jsapi_ticket);

        String url = request.getParameter("url");
        logger.info("获取到url为：{}", url);
        if (!url.startsWith("http://")) {
            String tmp = wxServer;
            if(wxServer.endsWith("/")){
                tmp = wxServer.substring(0, wxServer.length()-1);
            }
            url = tmp + url;
        }
        logger.info("获取到url为：{}", url);
        String callback = request.getParameter("callback");
        Map<String, String> signMap = WeixinShareUtil.sign(jsapi_ticket, url);
        signMap.put("appId", WechatAccountHolder.APP_ID);
        String jsonStr = callback + "(" +JSON.toJSONString(signMap) + ")";
        logger.info("返回调用的JSON信息为：{}", jsonStr);

        PrintWriter out = response.getWriter();
		out.print(jsonStr);
		out.close();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
