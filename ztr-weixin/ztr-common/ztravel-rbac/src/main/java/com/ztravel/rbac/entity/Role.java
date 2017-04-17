package com.ztravel.rbac.entity;

import com.ztravel.common.entity.AbstractBase;

public class Role extends AbstractBase {

	private String roleId;

	/** 角色名 */
	private String roleName;

	/** 角色的描述 */
	private String description;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
