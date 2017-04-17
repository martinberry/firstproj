package com.ztravel.member.weixin.vo;

import java.util.List;

import com.ztravel.common.enums.CredentialType;

public class WTravellerVO {
	/**
	 * 常旅客ObjectId
	 */
	private String id;
	/**
	 * 常旅客中文名
	 */
	private String travelerNameCn;
	/**
	 * 常旅客中文 姓
	 */
	private String firstNameCn;
	/**
	 * 常旅客中文 名
	 */
	private String lastNameCn;
	/**
	 * 常旅客英文名
	 */
	private String travelerNameEn;
	/**
	 * 常旅客英文名 first name
	 */
	private String firstNameEn;
	/**
	 * 常旅客英文名 last name
	 */
	private String lastNameEn;
	/**
	 * 常旅客性别
	 */
	private String gender;
	/**
	 * 常旅客类型(成人 儿童 婴儿)
	 */
	private String travellerType;
	/**
	 * 手机
	 */
	private String mobile;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 常旅客国籍
	 */
	private String nationality;
	/**
	 * 常旅客生日
	 */
	private String birthday;
	/**
	 * 省
	 */
	private String province ;
	/**
	 * 市
	 */
	private String city ;
	/**
	 * 区
	 */
	private String district ;
	/**
	 * 详细地址
	 */
	private String detailAddress ;
	/**
	 * 证件信息
	 */
	private List<Credentials> credentials;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTravelerNameCn() {
		return travelerNameCn;
	}
	public void setTravelerNameCn(String travelerNameCn) {
		this.travelerNameCn = travelerNameCn;
	}
	public String getFirstNameCn() {
		return firstNameCn;
	}
	public void setFirstNameCn(String firstNameCn) {
		this.firstNameCn = firstNameCn;
	}
	public String getLastNameCn() {
		return lastNameCn;
	}
	public void setLastNameCn(String lastNameCn) {
		this.lastNameCn = lastNameCn;
	}
	public String getTravelerNameEn() {
		return travelerNameEn;
	}
	public void setTravelerNameEn(String travelerNameEn) {
		this.travelerNameEn = travelerNameEn;
	}
	public String getFirstNameEn() {
		return firstNameEn;
	}
	public void setFirstNameEn(String firstNameEn) {
		this.firstNameEn = firstNameEn;
	}
	public String getLastNameEn() {
		return lastNameEn;
	}
	public void setLastNameEn(String lastNameEn) {
		this.lastNameEn = lastNameEn;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getTravellerType() {
		return travellerType;
	}
	public void setTravellerType(String travellerType) {
		this.travellerType = travellerType;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public List<Credentials> getCredentials() {
		return credentials;
	}
	public void setCredentials(List<Credentials> credentials) {
		this.credentials = credentials;
	}

	public static class Credentials {
		/**
		 * 证件类型
		 */
		private CredentialType type;
		/**
		 * 证件号码
		 */
		private String number;
		/**
		 * 证件有效期
		 */
		private String deadLineDay;

		public String getNumber() {
			return number;
		}
		public void setNumber(String number) {
			this.number = number;
		}
		public String getDeadLineDay() {
			return deadLineDay;
		}
		public void setDeadLineDay(String deadLineDay) {
			this.deadLineDay = deadLineDay;
		}
		public CredentialType getType() {
			return type;
		}
		public void setType(CredentialType type) {
			this.type = type;
		}
	}

}
