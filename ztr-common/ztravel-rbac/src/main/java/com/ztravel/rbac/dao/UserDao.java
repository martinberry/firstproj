package com.ztravel.rbac.dao;

import java.util.List;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.rbac.entity.User;
import com.ztravel.rbac.po.UserListSearchRequest;
/**
 * @author
 *
 */
public interface UserDao extends BaseDao<User> {

	List<User> selectByPage(UserListSearchRequest request);

	Integer countBySearch(UserListSearchRequest request);

	List<User> selectByUserName(String userName);

}
