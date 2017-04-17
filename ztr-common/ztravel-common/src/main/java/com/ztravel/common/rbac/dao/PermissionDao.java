package com.ztravel.common.rbac.dao;

import java.util.List;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.common.rbac.wo.Permission;
/**
 * @author
 *
 */
public interface PermissionDao extends BaseDao<Permission> {

	List<Permission> selectByType(String type);

	List<Permission> selectByParentId(String parentId);

	List<Permission> selectByUserName(String userName, String permissionName);

	List<Permission> selectByPermissionName(String permissionName);

}
