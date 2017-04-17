package com.ztravel.operator.po;

import java.io.Serializable;

public class UserSession implements Serializable {

	private static final long serialVersionUID = -657411872354860402L;

	private String userName;

	private String sessionid;

	private String password;

	private long expireTime;

//	private Boolean isAutoLogin;

	private String ip;

	private String browser;

	public UserSession() {}

	public String getUserName(){
		return userName;
	}

	public void setUserName(String userName){
		this.userName=userName;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getExpireTime(){
		return expireTime;
	}

	public void setExpireTime(long expireTime){
		this.expireTime=expireTime;
	}

	public String getIp(){
		return ip;
	}

	public void setIp(String ip){
		this.ip=ip;
	}

	public String getBrowser(){
		return browser;
	}

	public void setBrowser(String browser){
		this.browser=browser;
	}

}
