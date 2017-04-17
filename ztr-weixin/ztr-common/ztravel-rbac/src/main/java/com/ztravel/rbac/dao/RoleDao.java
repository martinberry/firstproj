package com.ztravel.rbac.dao;

import java.util.List;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.common.entity.PaginationEntity;
import com.ztravel.rbac.entity.Role;

public interface RoleDao extends BaseDao<Role> {

	Integer countAll();

	List<Role> selectByPage(PaginationEntity page);

	List<Role> selectByRoleName(String roleName);

	List<Role> selectAll();

	List<Role> selectByUserId(String userId);

}
