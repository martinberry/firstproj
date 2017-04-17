/**
 *
 */
package com.ztravel.rbac.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.rbac.dao.RolePermissionDao;
import com.ztravel.rbac.entity.RolePermission;

/**
 * @author
 *
 */
@Repository
public class RolePermissionDaoImpl extends BaseDaoImpl<RolePermission> implements RolePermissionDao {

	public static final String SELECT_BY_ROLEID = ".selectByRoleId";
	public static final String DELETE_BY_ROLEID = ".deleteByRoleId";
	public static final String INSERT_BATCH = ".insertBatch";

	@Override
    public List<RolePermission> selectByRoleId(String roleId) {
		Map params = new HashMap<>();
		params.put("roleId", roleId);
		return session.selectList(nameSpace + SELECT_BY_ROLEID, params);
    }

	@Override
    public void deleteByRoleId(String roleId) {
		session.delete(nameSpace + DELETE_BY_ROLEID, roleId);
    }

	@Override
    public void insertBatch(List<RolePermission> rolePerms) {
        session.insert(nameSpace + INSERT_BATCH, rolePerms);
    }

}
