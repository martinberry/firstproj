package com.ztravel.weixin.bean;

public class BaseMessageBean {

	protected String toUserName;

	protected String fromUserName;

	protected long createTime;

	protected String msgType;

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String buildXmlToWeixin(){

		StringBuffer xmlBuffer = new StringBuffer();

		xmlBuffer.append("<xml>");
		xmlBuffer.append("<ToUserName><![CDATA[").append(toUserName).append("]]></ToUserName>");
		xmlBuffer.append("<FromUserName><![CDATA[").append(fromUserName).append("]]></FromUserName>");
		xmlBuffer.append("<CreateTime><![CDATA[").append(createTime).append("]]></CreateTime>");
		xmlBuffer.append("<MsgType><![CDATA[").append(msgType).append("]]></MsgType>");
		xmlBuffer.append("</xml>");

		return xmlBuffer.toString();

	}

}
