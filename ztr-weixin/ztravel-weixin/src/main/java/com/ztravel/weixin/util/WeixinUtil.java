package com.ztravel.weixin.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ztravel.weixin.bean.BaseMessageBean;
import com.ztravel.weixin.bean.KfMessageBean;

public class WeixinUtil {

    public static String buildXmlToWeixin(Map<String, String> requestMap) {

        String returnXml = "";

        String msgType = requestMap.get("MsgType");

        switch (msgType) {
            case "event" : returnXml = receiveEvent (requestMap); break;
            case "text" : returnXml = receiveText (requestMap); break;
        }

        return returnXml;
    }

    private static String receiveEvent (Map<String, String> requestMap) {

        String returnXml = "";
        BaseMessageBean message = null;

        String event = requestMap.get("Event");
        if (event.equals("subscribe")) {
            message = MessageManager.eventMap.get("subscribe");
        } else if (event.equals("CLICK")) {
            String eventKey =  requestMap.get("EventKey");
            message = MessageManager.eventMap.get(eventKey);
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

    private static String receiveText (Map<String, String> requestMap) {

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

    /**
     * 解析微信发来的请求数据包（XML）
     *
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
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
            map.put(e.getName(), e.getText());
        }

        // 释放资源
        inputStream.close();
        inputStream = null;
        return map;
    }

}