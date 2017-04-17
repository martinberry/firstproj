package com.ztravel.common.weixin.notice;

public class ReceiveCouponNotice {
	private String userName;

	private String couponAmount;

	private String openId;

	private String validateDateInfo;//优惠券有效期信息,如:"2014-05-01至2014-05-10"

	private String couponUrl;//电子钱包URL,查看优惠券

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(String couponAmount) {
		this.couponAmount = couponAmount;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getValidateDateInfo() {
		return validateDateInfo;
	}

	public void setValidateDateInfo(String validateDateInfo) {
		this.validateDateInfo = validateDateInfo;
	}

	public String getCouponUrl() {
		return couponUrl;
	}

	public void setCouponUrl(String couponUrl) {
		this.couponUrl = couponUrl;
	}

}
