package com.ztravel.reuse.member.entity;

/**
 * Simplified traveler entity
 * @author zuoning.shen
 *
 */
public class SimpleTravelerEntity {
	
	/**
	 * 常旅客ID,用于删除和编辑
	 */
	private String travelerId;
	
	/**
	 * 中文名
	 */
	private String travelerNameCn;
	
	/**
	 * 英文名
	 */
	private String travelerNameEn;
	
	/**
	 * 头像id
	 */
	private String imageId;
	
	/**
	 * 手机号码
	 */
	private String phoneNum;
	
	/**
	 * 是否默认
	 */
	private String isDefault;

	public String getTravelerId() {
		return travelerId;
	}

	public void setTravelerId(String travelerId) {
		this.travelerId = travelerId;
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

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	
	
}
