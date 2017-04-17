package com.ztravel.weixin.activity.vo;

/***
 *
 * @author zhoubo
 *
 */
public class GameRecordVo {
	/**
	 * 活动名称
	 */
	private String activityName;

	/**
	 * 用户昵称
	 */
	private String nickName;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 收件人
	 */
	private String addressee;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 奖品类型
	 */
	private String awardCode;

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddressee() {
		return addressee;
	}

	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAwardCode() {
		return awardCode;
	}

	public void setAwardCode(String awardCode) {
		this.awardCode = awardCode;
	}

}
