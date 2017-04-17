package com.ztravel.weixin.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.util.OpenIdUtil;
import com.ztravel.weixin.front.service.IViewStateService;
import com.ztravel.weixin.operate.OpenIdOperator;

/**
 * 此Servlet用于处理微信服务器发来的消息请求
 * @author xutian
 *
 */
@WebServlet(name = "weixinOpenIdServlet", urlPatterns = "/weixinOpenIdServlet")
public class WeixinOpenIdServlet extends HttpServlet {

	private static Logger logger = RequestIdentityLogger.getLogger(WeixinOpenIdServlet.class);

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String code = request.getParameter("code");
		String state = request.getParameter("state");

		logger.info("请求的参数code为：" + code + ",state为：" + state + ".");

		JSONObject oAuthAccessToken = OpenIdOperator.oAuthAccessToken(code);

		//获取得到openid
		String openid = oAuthAccessToken.getString("openid");
		logger.info("获得的openid为：" + openid);

		//将获取到的openid保存到redis中
		OpenIdUtil.setOpenId(openid);
	     WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
         IViewStateService viewStateServiceImpl = (IViewStateService) ac.getBean("viewStateServiceImpl");
         logger.info("state is : [{}]",state);
		String redirectUrl = viewStateServiceImpl.getUrlById(state);
		logger.info("redirect url is : [{}]",redirectUrl);
		response.sendRedirect(redirectUrl);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


	}

	/**
     * 解析微信发来的请求数据包（XML）
     *
     * @param request
     * @return
     * @throws Exception
     */
	@SuppressWarnings("unchecked")
	public Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();

        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
		List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList) {
        	logger.info("节点key:" + e.getName() + ",节点值:" + e.getText());
            map.put(e.getName(), e.getText());
        }

        // 释放资源
        inputStream.close();
        inputStream = null;
        return map;
	}

}