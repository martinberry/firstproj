package com.ztravel.rbac.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.common.entity.PaginationEntity;
import com.ztravel.rbac.dao.RoleDao;
import com.ztravel.rbac.entity.Role;

@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {

	public static final String SELECT_ALL = ".selectAll";
	public static final String SELECT_BY_PAGE = ".selectByPage";
	public static final String COUNT_ALL = ".countAll";
	public static final String SELECT_BY_ROLENAME= ".selectByRoleName";
	public static final String SELECT_BY_USERID= ".selectByUserId";

    @Override
    public List<Role> selectAll() {
        return session.selectList(nameSpace + SELECT_ALL);
    }

    @Override
    public List<Role> selectByPage(PaginationEntity page) {
    	Map params = new HashMap<>();
		params.put("offset", (page.getPageNo()-1)*page.getPageSize());
		params.put("limit", page.getPageSize());
        return session.selectList(nameSpace + SELECT_BY_PAGE, params);
    }

	@Override
    public Integer countAll() {
        return session.selectOne(nameSpace + COUNT_ALL);
    }

    @Override
    public List<Role> selectByRoleName(String roleName) {
    	Map params = new HashMap<>();
		params.put("roleName", roleName);
        return session.selectList(nameSpace + SELECT_BY_ROLENAME, params);
    }

    @Override
    public List<Role> selectByUserId(String userId) {
    	Map params = new HashMap<>();
		params.put("userId", userId);
        return session.selectList(nameSpace + SELECT_BY_USERID, params);
    }

}
