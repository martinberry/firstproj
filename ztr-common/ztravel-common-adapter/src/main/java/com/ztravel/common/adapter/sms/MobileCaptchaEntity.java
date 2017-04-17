package com.ztravel.common.adapter.sms;


/**
 * 手机验证码发送实体类
 * @author liuzhuo
 *
 */
public class MobileCaptchaEntity {
	
	/**
	 * 消息类型
	 * 注册
	 * 密码找回
	 */
	private String msgType;
	
	/**
	 * 发送手机号码
	 */
	private String mobileNum;
	
	/**
	 * 短信内容
	 */
	private String content;

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	

}
