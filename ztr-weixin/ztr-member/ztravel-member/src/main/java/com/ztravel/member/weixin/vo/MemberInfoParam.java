package com.ztravel.member.weixin.vo;

import javax.validation.constraints.Pattern;

/**
 * 修改会员信息参数
 * @author MH
 */
public class MemberInfoParam {
	/**
	 * 昵称
	 */
	@Pattern(regexp = "^([0-9a-zA-Z\u4E00-\u9FA5\u002A]+)$")
	private String nickName;
	/**
	 * 真实姓名
	 */
//	@Pattern(regexp = "")
	private String realName;
	/**
	 * 性别
	 */
	private String gender;


	private String email;

	private String newEmail;
	/**
	 * 新手机号
	 */
	@Pattern(regexp = "^[1]{0,1}[3,4,5,7,8]{0,1}[0-9]{0,9}$")
	private String newMobile;
	/**
	 * 验证码
	 */
	@Pattern(regexp = "^([0-9]{0,6})$")
	private String verificationCode;
	/**
	 * 省
	 */
	private String province;
	/**
	 * 市
	 */
	private String city;
	/**
	 * 区
	 */
	private String area;
	/**
	 * 详细地址
	 */
	private String detailAddress;

	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getNewMobile() {
		return newMobile;
	}
	public void setNewMobile(String newMobile) {
		this.newMobile = newMobile;
	}
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
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
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNewEmail() {
		return newEmail;
	}
	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}

}
