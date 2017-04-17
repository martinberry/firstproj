/**
 *
 */
package com.ztravel.rbac.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.rbac.dao.UserDao;
import com.ztravel.rbac.entity.User;
import com.ztravel.rbac.po.UserListSearchRequest;

/**
 * @author
 *
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	public static final String SELECT_BY_PAGE = ".selectByPage";
	public static final String COUNT_BY_SEARCH = ".countBySearch";
	public static final String SELECT_BY_USERNAME = ".selectByUserName";

    @Override
    public List<User> selectByPage(UserListSearchRequest request) {
    	Map params = new HashMap<>();
    	if(StringUtils.isNotBlank(request.getRealName())){
    		params.put("realName", '%' + request.getRealName().replace("%", "\\%") + '%');
    	}
    	params.put("employeeCode", request.getEmployeeCode());
    	params.put("roleId", request.getRoleId());
		params.put("offset", (request.getPageNo()-1)*request.getPageSize());
		params.put("limit", request.getPageSize());
        return session.selectList(nameSpace + SELECT_BY_PAGE, params);
    }

	@Override
    public Integer countBySearch(UserListSearchRequest request) {
		Map params = new HashMap<>();
		if(StringUtils.isNoneBlank(request.getRealName())){
			params.put("realName", '%' + request.getRealName().replace("%", "\\%") + '%');
    	}
    	params.put("employeeCode", request.getEmployeeCode());
    	params.put("roleId", request.getRoleId());
        return session.selectOne(nameSpace + COUNT_BY_SEARCH, params);
    }

    @Override
    public List<User> selectByUserName(String userName) {
    	Map params = new HashMap<>();
		params.put("userName", userName);
        return session.selectList(nameSpace + SELECT_BY_USERNAME, params);
    }

}
