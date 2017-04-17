package com.ztravel.weixin.bean;

import java.util.Calendar;

public class KfMessageBean extends BaseMessageBean {

	private String kfAccount;

	public String getKfAccount() {
		return kfAccount;
	}

	public void setKfAccount(String kfAccount) {
		this.kfAccount = kfAccount;
	}

	public KfMessageBean() {
		super();
		this.createTime = Calendar.getInstance().getTimeInMillis() /1000;
		this.msgType = "transfer_customer_service";
	}

	public KfMessageBean(String kfAccount) {
		super();
		this.kfAccount = kfAccount;
		this.createTime = Calendar.getInstance().getTimeInMillis() /1000;
		this.msgType = "transfer_customer_service";
	}

	public String buildXmlToWeixin(){

		StringBuffer xmlBuffer = new StringBuffer();

		xmlBuffer.append("<xml>");
		xmlBuffer.append("<ToUserName><![CDATA[").append(toUserName).append("]]></ToUserName>");
		xmlBuffer.append("<FromUserName><![CDATA[").append(fromUserName).append("]]></FromUserName>");
		xmlBuffer.append("<CreateTime>").append(createTime).append("</CreateTime>");
		xmlBuffer.append("<MsgType><![CDATA[").append(msgType).append("]]></MsgType>");
		if (kfAccount != null) {
			xmlBuffer.append("<TransInfo>");
			xmlBuffer.append("<KfAccount><![CDATA[").append(kfAccount).append("]]></KfAccount>");
			xmlBuffer.append("</TransInfo>");
		}
		xmlBuffer.append("</xml>");

		return xmlBuffer.toString();

	}

}
