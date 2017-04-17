package com.ztravel.rbac.po;

import java.util.List;

public class RoleSaveRequest {

	private String roleId;

	/** 角色名 */
	private String roleName;

	private List<String> permissionIds;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<String> getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(List<String> permissionIds) {
		this.permissionIds = permissionIds;
	}

}
