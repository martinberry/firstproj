package com.ztravel.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.common.eventbus.EventBus;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.weixin.bean.BaseMessageBean;
import com.ztravel.weixin.bean.KfMessageBean;
import com.ztravel.weixin.event.WechatEvent;
import com.ztravel.weixin.front.service.IMaterialMediaService;
import com.ztravel.weixin.util.MessageManager;
import com.ztravel.weixin.util.SignatureUtil;
import com.ztravel.weixin.util.WeixinUtil;

/**
 * 此Servlet用于处理微信服务器发来的消息请求
 * @author xutian
 *
 */
@WebServlet(name = "weixinServlet", urlPatterns = "/weixinServlet")
public class WeixinServlet extends HttpServlet {

    private static Logger logger = RequestIdentityLogger.getLogger(WeixinServlet.class);

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignatureUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Map<String, String> requestMap = WeixinUtil.parseXml(request);
            logger.info("requestMap:::{}", requestMap);

//            String returnXml = WeixinUtil.buildXmlToWeixin(requestMap);
            String returnXml = "";
            returnXml = buildXmlToWeixin(requestMap);
            logger.info("returnXml:::{}", returnXml);

            WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
            EventBus wechatEventBus = (EventBus)ac.getBean("wechatEventBus") ;
            WechatEvent event = new WechatEvent(requestMap) ;
            wechatEventBus.post(event);
            PrintWriter out = response.getWriter();
            out.write(returnXml);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {

        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("FromUserName", "oSyr1wS9acV8bPcsfzFC8Kc-IhaA");
        requestMap.put("EventKey", null);
        requestMap.put("Event", "subscribe");
        System.out.println("requestMap:::{}" + requestMap);
    }
    private String buildXmlToWeixin(Map<String, String> requestMap) {
        String returnXml = "";

        String msgType = requestMap.get("MsgType");

        switch (msgType) {
            case "event" : returnXml = receiveEvent (requestMap); break;
            case "text" : returnXml = receiveText (requestMap); break;
        }

        return returnXml;
    }

    private String receiveEvent(Map<String, String> requestMap) {
    	// TODO Auto-generated method stub
        String returnXml = "";
        BaseMessageBean message = null;

        String event = requestMap.get("Event");
        if (event.equals("subscribe")) {
            message = MessageManager.eventMap.get("subscribe");
        } else if (event.equals("CLICK")) {

            String eventKey =  requestMap.get("EventKey");

            WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
            IMaterialMediaService materialMediaServiceImpl = (IMaterialMediaService) ac.getBean("materialMediaServiceImpl");
        	message = materialMediaServiceImpl.buildMessageByKey(eventKey);

    //        message = MessageManager.eventMap.get(eventKey);
        } else if (event.equals("VIEW")) {
            //暂不处理
        }

        if (message != null) {
            message.setToUserName(requestMap.get("FromUserName"));
            message.setFromUserName(requestMap.get("ToUserName"));
            returnXml = message.buildXmlToWeixin();
        }

        return returnXml;
    }


    private  String receiveText (Map<String, String> requestMap) {

        String returnXml = "";
        BaseMessageBean message = null;

        String content = requestMap.get("Content");
        if (content != null && !content.trim().isEmpty()) {
            String msg = content.trim();
            message = MessageManager.keyWordMap.get(msg);
        }

        if (message== null) {
            message = new KfMessageBean();
        }

        message.setToUserName(requestMap.get("FromUserName"));
        message.setFromUserName(requestMap.get("ToUserName"));
        returnXml = message.buildXmlToWeixin();

        return returnXml;
    }

}