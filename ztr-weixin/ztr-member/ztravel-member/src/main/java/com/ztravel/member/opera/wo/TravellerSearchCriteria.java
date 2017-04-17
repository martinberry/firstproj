package com.ztravel.member.opera.wo;

public class TravellerSearchCriteria {

	/**
	 * 常旅客ID,用于删除和编辑
	 */
	private String travelerId;

	/**
	 * 会员id
	 */
	private String memberId;

	/**
	 * 中文名
	 */
	private String travelerNameCn;

	/**
	 * 英文名
	 */
	private String travelerNameEn;

	/**
	 * 常旅客英文名
	 */
	private String firstEnName;

	/**
	 * 常旅客英文名
	 */
	private String lastEnName;

	/**
	 * 手机号码
	 */
	private String phoneNum;

	public String getTravelerId() {
		return travelerId;
	}

	public void setTravelerId(String travelerId) {
		this.travelerId = travelerId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getTravelerNameCn() {
		return travelerNameCn;
	}

	public void setTravelerNameCn(String travelerNameCn) {
		this.travelerNameCn = travelerNameCn;
	}

	public String getTravelerNameEn() {
		return travelerNameEn;
	}

	public void setTravelerNameEn(String travelerNameEn) {
		this.travelerNameEn = travelerNameEn;
	}

	public String getFirstEnName() {
		return firstEnName;
	}

	public void setFirstEnName(String firstEnName) {
		this.firstEnName = firstEnName;
	}

	public String getLastEnName() {
		return lastEnName;
	}

	public void setLastEnName(String lastEnName) {
		this.lastEnName = lastEnName;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}


}
