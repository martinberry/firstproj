package com.ztravel.weixin.bean;

import java.util.Calendar;

public class TextMessageBean extends BaseMessageBean {

	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public TextMessageBean() {
		super();
		this.createTime = Calendar.getInstance().getTimeInMillis() /1000;
		this.msgType = "text";
	}

	public TextMessageBean(String content) {
		this.createTime = Calendar.getInstance().getTimeInMillis() /1000;
		this.msgType = "text";
		this.content = content;
	}

	public String buildXmlToWeixin(){

		StringBuffer xmlBuffer = new StringBuffer();

		xmlBuffer.append("<xml>");
		xmlBuffer.append("<ToUserName><![CDATA[").append(toUserName).append("]]></ToUserName>");
		xmlBuffer.append("<FromUserName><![CDATA[").append(fromUserName).append("]]></FromUserName>");
		xmlBuffer.append("<CreateTime>").append(createTime).append("</CreateTime>");
		xmlBuffer.append("<MsgType><![CDATA[").append(msgType).append("]]></MsgType>");
		xmlBuffer.append("<Content><![CDATA[").append(content).append("]]></Content>");
		xmlBuffer.append("</xml>");

		return xmlBuffer.toString();

	}

}
