package com.ztravel.sso.po;

import org.joda.time.DateTime;

/**
 * @author wanhaofan
 * */
public class SSOBasicEntity {
	private String id ;
	private String mid ;
	private String nickName ;
	private String email ;
	private String mobile ;
	private DateTime lastLoginDate ;
	private String headImageId ;
	private Boolean isActive ;
	private Boolean isLogin ;
	private DateTime updateTime ;
	private DateTime createTime ;
	private String password ;
	private String province ;
	private String city ;
    private String area ;
	private String detailAddress ;
	private String realName ;
    private String openId ;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public DateTime getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(DateTime lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public String getHeadImageId() {
		return headImageId;
	}
	public void setHeadImageId(String headImageId) {
		this.headImageId = headImageId;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Boolean getIsLogin() {
		return isLogin;
	}
	public void setIsLogin(Boolean isLogin) {
		this.isLogin = isLogin;
	}
	public DateTime getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(DateTime updateTime) {
		this.updateTime = updateTime;
	}
	public DateTime getCreateTime() {
		return createTime;
	}
	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}

    public String getOpenId() {
        return openId;
    }
    public void setOpenId(String openId) {
        this.openId = openId;
    }

}
