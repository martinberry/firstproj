package com.ztravel.rbac.dao;

import java.util.List;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.rbac.entity.RolePermission;
/**
 * @author
 *
 */
public interface RolePermissionDao extends BaseDao<RolePermission> {

	void deleteByRoleId(String roleId);

	List<RolePermission> selectByRoleId(String roleId);

	void insertBatch(List<RolePermission> rolePerms);

}
