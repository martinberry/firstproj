package com.ztravel.common.rbac.service;

import java.util.List;

import com.ztravel.common.rbac.wo.Permission;
import com.ztravel.common.rbac.wo.PermissionWo;

/**
 *
 * @author tengmeilin
 *
 */
public interface PermissionCommonService {

	/**
	 * 获取用户权限，菜单展示
	 * @return
	 */
	List<PermissionWo> getMenubyPermissions();

	/**
	 * 获取所有权限
	 * @return
	 */
	List<PermissionWo> getAllPermissions();

	/**
	 * 添加权限
	 * @param permission
	 */
	void addPermission(Permission permission);

	/**
	 * 获取消息权限url
	 * @param isAdmin
	 * @return
	 */
	String getMessagePermissions(boolean isAdmin);

}
