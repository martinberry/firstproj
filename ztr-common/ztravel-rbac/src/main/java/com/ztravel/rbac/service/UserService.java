/**
 *
 */
package com.ztravel.rbac.service;

import java.sql.SQLException;
import java.util.List;

import com.ztravel.rbac.po.UserListResponse;
import com.ztravel.rbac.po.UserListSearchRequest;
import com.ztravel.rbac.po.UserSaveRequest;

/**
 * @author tengmeilin
 *
 */
public interface UserService {

	/**
	 * 根据用户id删除用户
	 * @param userId
	 * @throws Exception
	 */
	void deleteUser(String userId) throws Exception;

	/**
	 * 挂起或者解挂用户
	 * @param userId
	 * @param isActive
	 * @throws SQLException
	 */
	void modifyUserStatus(String userId, boolean isActive) throws SQLException;

	/**
	 * 更新用户信息
	 * @param request
	 * @throws Exception
	 */
	void updateUser(UserSaveRequest request) throws Exception;

	/**
	 * 添加用户
	 * @param request
	 * @throws Exception
	 */
	void insertUser(UserSaveRequest request) throws Exception;

	/**
	 * 根据条件分页查询用户
	 * @param request
	 * @param users
	 * @return
	 * @throws Exception
	 */
	Integer getAllUsersWithPage(UserListSearchRequest request, List<UserListResponse> users) throws Exception;

}
