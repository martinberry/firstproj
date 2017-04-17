package com.ztravel.operator.po;

public class LoginUser {

	private String userName;

	private String password;

	private String authCode;

	private Boolean rememberMe;

	public LoginUser() {}

	public String getUserName(){
		return userName;
	}

	public void setUserName(String userName){
		this.userName=userName;
	}

	public String getPassword(){
		return password;
	}

	public void setPassword(String password){
		this.password=password;
	}

	public String getAuthCode(){
		return authCode;
	}

	public void setAuthCode(String authCode){
		this.authCode=authCode;
	}

	public Boolean getRememberMe(){
		return rememberMe;
	}

	public void setRememberMe(Boolean rememberMe){
		this.rememberMe=rememberMe;
	}

//	@Override
//	public String toString() {
//		return "{\"userName\":" + userName + ", \"realName\":" + realName + ", \"email\":" + email + ", \"mobilePhone\":" + mobilePhone+ ", \"roles\":" + roles+ ",\"remark\":"
//				+ remark + "}";
//	}


}
