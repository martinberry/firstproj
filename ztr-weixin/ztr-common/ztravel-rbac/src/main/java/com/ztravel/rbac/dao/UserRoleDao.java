package com.ztravel.rbac.dao;

import java.util.List;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.rbac.entity.UserRole;
/**
 * @author
 *
 */
public interface UserRoleDao extends BaseDao<UserRole> {

	Integer countByRoleId(String roleId);

	void deleteByRoleId(String roleId);

	void deleteByUserId(String userId);

	List<UserRole> selectByUserId(String userId);


}
