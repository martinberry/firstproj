/**
 *
 */
package com.ztravel.common.rbac.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.common.rbac.dao.PermissionDao;
import com.ztravel.common.rbac.wo.Permission;

/**
 * @author
 *
 */
@Repository
public class PermissionDaoImpl extends BaseDaoImpl<Permission> implements PermissionDao {

	public static final String SELECT_BY_TYPE = ".selectByType";
	public static final String SELECT_BY_PARENTID = ".selectByParentId";
	public static final String SELECT_BY_USERNAME = ".selectByUserName";
	public static final String SELECT_BY_PERMNAME = ".selectByPermName";

    @Override
    public List<Permission> selectByType(String type) {
    	Map params = new HashMap<>();
		params.put("type", type);
        return session.selectList(nameSpace + SELECT_BY_TYPE, params);
    }

    @Override
    public List<Permission> selectByParentId(String parentId) {
    	Map params = new HashMap<>();
		params.put("parentId", parentId);
        return session.selectList(nameSpace + SELECT_BY_PARENTID, params);
    }

    @Override
    public List<Permission> selectByUserName(String userName, String permissionName) {
    	Map params = new HashMap<>();
		params.put("userName", userName);
		params.put("permissionName", permissionName);
        return session.selectList(nameSpace + SELECT_BY_USERNAME, params);
    }

    @Override
    public List<Permission> selectByPermissionName(String permissionName) {
    	Map params = new HashMap<>();
		params.put("permissionName", permissionName);
        return session.selectList(nameSpace + SELECT_BY_PERMNAME, params);
    }

}
