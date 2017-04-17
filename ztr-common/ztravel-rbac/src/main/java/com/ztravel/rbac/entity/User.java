package com.ztravel.rbac.entity;

import com.ztravel.common.entity.AbstractBase;

public class User extends AbstractBase {

	private String userId;

	/** 用户名，用于登录系统 */
	private String userName;

	/** 密码 */
	private String password;

	/** 真实姓名，可以重复 */
	private String realName;

	/** 工号 */
	private String employeeCode;

	/** 手机号码 */
	private String mobile;

	/** 邮件地址 */
	private String email;

	private String description;

	private Boolean isActive;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}