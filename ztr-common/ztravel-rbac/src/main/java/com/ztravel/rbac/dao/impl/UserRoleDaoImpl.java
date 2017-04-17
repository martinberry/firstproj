/**
 *
 */
package com.ztravel.rbac.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.common.entity.PaginationEntity;
import com.ztravel.rbac.dao.UserRoleDao;
import com.ztravel.rbac.entity.Role;
import com.ztravel.rbac.entity.UserRole;

/**
 * @author
 *
 */
@Repository
public class UserRoleDaoImpl extends BaseDaoImpl<UserRole> implements UserRoleDao {

	public static final String SELECT_BY_USERID = ".selectByUserId";
	public static final String COUNT_BY_ROLEID = ".countByRoleId";
	public static final String DELETE_BY_USERID = ".deleteByUserId";
	public static final String DELETE_BY_ROLEID = ".deleteByRoleId";

    @Override
    public List<UserRole> selectByUserId(String userId) {
    	Map params = new HashMap<>();
		params.put("userId", userId);
        return session.selectList(nameSpace + SELECT_BY_USERID, params);
    }

	@Override
    public Integer countByRoleId(String roleId) {
		Map params = new HashMap<>();
		params.put("roleId", roleId);
        return session.selectOne(nameSpace + COUNT_BY_ROLEID, params);
    }

	@Override
    public void deleteByUserId(String userId) {
		session.delete(nameSpace + DELETE_BY_USERID, userId);
    }

	@Override
    public void deleteByRoleId(String roleId) {
		session.delete(nameSpace + DELETE_BY_ROLEID, roleId);
    }

}
