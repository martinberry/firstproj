package com.ztravel.member.weixin.vo;

import java.util.List;

/**
 * 微信用户信息页面展示
 * @author MH
 */
public class WMemberVO {
	/**
	 * 头像imageId
	 */
	private String headImgId;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 性别
	 */
	private String gender;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 完整地址
	 */
	private String address;
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
	private String area ;
	/**
	 * 详细地址
	 */
	private String detailAddress ;
	/**
	 * 常旅客信息
	 */
	private List<WTravellerVO> travellers;

	public String getHeadImgId() {
		return headImgId;
	}
	public void setHeadImgId(String headImgId) {
		this.headImgId = headImgId;
	}
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public List<WTravellerVO> getTravellers() {
		return travellers;
	}
	public void setTravellers(List<WTravellerVO> travellers) {
		this.travellers = travellers;
	}

}
