package com.ztravel.rbac.service;

import java.util.List;

import com.ztravel.common.entity.PaginationEntity;
import com.ztravel.rbac.entity.Role;
import com.ztravel.rbac.po.RoleListResponse;
import com.ztravel.rbac.po.RoleSaveRequest;

public interface RoleService {

	/**
	 * 根据角色id删除角色
	 * @param roleId
	 * @throws Exception
	 */
	void deleteRole(String roleId) throws Exception;

	/**
	 * 根据角色id查询角色
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	Role getRoleById(String roleId) throws Exception;

	/**
	 * 获取角色对应的权限
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	List<String> getPermissionByRoleId(String roleId) throws Exception;

	/**
	 * 更新角色信息
	 * @param request
	 * @throws Exception
	 */
	void updateRole(RoleSaveRequest request) throws Exception;

	/**
	 * 添加角色
	 * @param request
	 * @throws Exception
	 */
	void insertRole(RoleSaveRequest request) throws Exception;

	/**
	 * 分页显示角色
	 * @param page
	 * @param roles
	 * @return
	 * @throws Exception
	 */
	Integer getAllRolesWithPage(PaginationEntity page, List<RoleListResponse> roles) throws Exception;

	/**
	 * 获取所有角色
	 * @return
	 * @throws Exception
	 */
	List<Role> getAllRoles() throws Exception;

}
