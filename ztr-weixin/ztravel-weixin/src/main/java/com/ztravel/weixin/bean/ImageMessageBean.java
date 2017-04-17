package com.ztravel.weixin.bean;

import java.util.Calendar;

public class ImageMessageBean extends BaseMessageBean {

	private String mediaId;

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public ImageMessageBean() {
		super();
		this.createTime = Calendar.getInstance().getTimeInMillis() /1000;
		this.msgType = "image";
	}

	public ImageMessageBean(String mediaId) {
		this.createTime = Calendar.getInstance().getTimeInMillis() /1000;
		this.msgType = "image";
		this.mediaId = mediaId;
	}

	public String buildXmlToWeixin(){

		StringBuffer xmlBuffer = new StringBuffer();

		xmlBuffer.append("<xml>");
		xmlBuffer.append("<ToUserName><![CDATA[").append(toUserName).append("]]></ToUserName>");
		xmlBuffer.append("<FromUserName><![CDATA[").append(fromUserName).append("]]></FromUserName>");
		xmlBuffer.append("<CreateTime>").append(createTime).append("</CreateTime>");
		xmlBuffer.append("<MsgType><![CDATA[").append(msgType).append("]]></MsgType>");
		xmlBuffer.append("<Image>");
		xmlBuffer.append("<MediaId><![CDATA[").append(mediaId).append("]]></MediaId>");
		xmlBuffer.append("</Image>");
		xmlBuffer.append("</xml>");

		return xmlBuffer.toString();

	}

}
