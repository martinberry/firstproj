package com.ztravel.order.back.vo;

/**
 * 订单旅客,用于后台自由行订单详情页面展示
 * @author MH
 */
public class TravellerVO {
	/**
	 * 中文名
	 */
	private String nameCn;
	/**
	 * 英文名/拼音
	 */
	private String nameEn;
	/**
	 * 旅客类型(成人/儿童)
	 */
	private String type;
	/**
	 * 性别
	 */
	private String gender;
	/**
	 * 国籍
	 */
	private String nationality;
	/**
	 * 出生年月
	 */
	private String birthday;
	/**
	 * 证件类型
	 */
	private String credentialType;
	/**
	 * 证件号
	 */
	private String credentialNo;
	/**
	 * 证件有效期
	 */
	private String credentialExpireDate;

	public String getNameCn() {
		return nameCn;
	}
	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}
	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
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
	public String getCredentialType() {
		return credentialType;
	}
	public void setCredentialType(String credentialType) {
		this.credentialType = credentialType;
	}
	public String getCredentialNo() {
		return credentialNo;
	}
	public void setCredentialNo(String credentialNo) {
		this.credentialNo = credentialNo;
	}
	public String getCredentialExpireDate() {
		return credentialExpireDate;
	}
	public void setCredentialExpireDate(String credentialExpireDate) {
		this.credentialExpireDate = credentialExpireDate;
	}

}
