package com.ztravel.member.opera.wo;

public class TravellerRequestInfo {

	/**
	 * 会员ID,用于删除和编辑
	 */
	private String memberId;
	/**
	 * 常旅客ID,用于删除和编辑
	 */
	private String travelerId;

	/**
	 * 中文名
	 */
	private String travelerNameCn;

	/**
	 * 拼音或者英文名
	 */
	private String travelerNameEn;

	/**
	 * 手机号码
	 */
	private String phoneNum;

	private String index;

	private String size;

	private String total;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getTravelerId() {
		return travelerId;
	}

	public void setTravelerId(String travelerId) {
		this.travelerId = travelerId;
	}


	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getSize() {
		return size;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getTotal() {
		return total;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getTravelerNameEn() {
		return travelerNameEn;
	}

	public void setTravelerNameEn(String travelerNameEn) {
		this.travelerNameEn = travelerNameEn;
	}

	public String getTravelerNameCn() {
		return travelerNameCn;
	}

	public void setTravelerNameCn(String travelerNameCn) {
		this.travelerNameCn = travelerNameCn;
	}


}
