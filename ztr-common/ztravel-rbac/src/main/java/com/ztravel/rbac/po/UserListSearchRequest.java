package com.ztravel.rbac.po;

import com.ztravel.common.entity.PaginationEntity;

public class UserListSearchRequest extends PaginationEntity {

	/** 真实姓名，可以重复 */
	private String realName;

	/** 工号 */
	private String employeeCode;

	private String roleId;

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

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
