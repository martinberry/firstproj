package com.ztravel.member.opera.wo;

import java.util.List;

import com.ztravel.member.po.TravelerEntity.Credentials;


public class TravellerResponseInfo {

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
	 * 手机号码
	 */
	private String phoneNum;

	/**
	 * 常旅客性别
	 */
	private String gender;

	/**
	 * 常旅客类型(成人 儿童 婴儿)
	 */
	private String travelType;

	/**
	 * 常旅客证件信息
	 */
	private List<Credentials> credentials;

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

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTravelType() {
		return travelType;
	}

	public void setTravelType(String travelType) {
		this.travelType = travelType;
	}

	public List<Credentials> getCredentials() {
		return credentials;
	}

	public void setCredentials(List<Credentials> credentials) {
		this.credentials = credentials;
	}


}
