package com.ztravel.common.weixin.notice;

/**邀请注册通知*/
public class InviteRegisNotice {

	private String openId;

	private String userName;

	private String friendName;

	private String regisTime;//具体到分,如:2014年8月21日 18:30

	private String detailUrl;//电子钱包URL

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

	public String getRegisTime() {
		return regisTime;
	}

	public void setRegisTime(String regisTime) {
		this.regisTime = regisTime;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}
}
