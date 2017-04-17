package com.ztravel.reuse.member.entity;

import java.util.List;

import com.ztravel.member.po.SystemNotice;

public class WorkPlatFormVo {
	private String headImageId ;

	private String userName ;

	private Integer loginFailCount ;

	private boolean isLogin ;

	private List<PrivateLetterVo> pls ;

	private List<SystemNotice> sns ;

	private boolean isPlAllRead ;

	private boolean isSnAllRead ;

	private String ticket;
	
	private Boolean wxLogined;

	public Boolean getWxLogined() {
		return wxLogined;
	}

	public void setWxLogined(Boolean wxLogined) {
		this.wxLogined = wxLogined;
	}

	public String getHeadImageId() {
		return headImageId;
	}

	public void setHeadImageId(String headImageId) {
		this.headImageId = headImageId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getLoginFailCount() {
		return loginFailCount;
	}

	public void setLoginFailCount(Integer loginFailCount) {
		this.loginFailCount = loginFailCount;
	}

	public boolean getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public List<PrivateLetterVo> getPls() {
		return pls;
	}

	public void setPls(List<PrivateLetterVo> pls) {
		this.pls = pls;
	}

	public List<SystemNotice> getSns() {
		return sns;
	}

	public void setSns(List<SystemNotice> sns) {
		this.sns = sns;
	}

	public boolean getIsPlAllRead() {
		return isPlAllRead;
	}

	public void setIsPlAllRead(boolean isPlAllRead) {
		this.isPlAllRead = isPlAllRead;
	}

	public boolean getIsSnAllRead() {
		return isSnAllRead;
	}

	public void setIsSnAllRead(boolean isSnAllRead) {
		this.isSnAllRead = isSnAllRead;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}


}
