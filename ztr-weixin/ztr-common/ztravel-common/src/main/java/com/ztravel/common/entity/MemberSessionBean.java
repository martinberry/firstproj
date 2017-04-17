package com.ztravel.common.entity;

import java.io.Serializable;


/**
 * @author wanhaofan
 * 当请求访问服务器时自动生成的Bean
 * */
public class MemberSessionBean implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -2978109967675498365L;
	/**
	 *
	 */
	private String url ;
	private String memberId ;
	private String mid ;
	private Boolean isLogin ;
	private Boolean isActive ;
	private String mobile ;
	private String imageId ;
	private String nickName ;
	private Integer loginFailCount ;

	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUrl() {
		return url == null ? "/home" : url ;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public Boolean getIsLogin() {
		return isLogin == null ? false : isLogin ;
	}
	public void setIsLogin(Boolean isLogin) {
		this.isLogin = isLogin;
	}
	public Boolean getIsActive() {
		return isActive == null ? false : isActive ;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Integer getLoginFailCount() {
		return loginFailCount == null ? 0 : loginFailCount ;
	}
	public void setLoginFailCount(Integer loginFailCount) {
		this.loginFailCount = loginFailCount;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}




}
