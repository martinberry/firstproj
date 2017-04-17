/**
 *
 */
package com.ztravel.rbac.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.codehaus.plexus.util.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.constants.Const;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.common.security.SignUtil;
import com.ztravel.common.util.IdGeneratorUtil;
import com.ztravel.rbac.dao.RoleDao;
import com.ztravel.rbac.dao.UserDao;
import com.ztravel.rbac.dao.UserRoleDao;
import com.ztravel.rbac.entity.Role;
import com.ztravel.rbac.entity.User;
import com.ztravel.rbac.entity.UserRole;
import com.ztravel.rbac.po.UserListResponse;
import com.ztravel.rbac.po.UserListSearchRequest;
import com.ztravel.rbac.po.UserSaveRequest;
import com.ztravel.rbac.service.UserService;

/**
 * @author
 *
 */
@Service
public class UserServiceImpl implements UserService {

	private final RedisClient redisClient = RedisClient.getInstance();

	@Resource
	private UserDao userDaoImpl;

	@Resource
	private RoleDao roleDaoImpl;

	@Resource
	private UserRoleDao userRoleDaoImpl;
	
	@Resource
	private IdGeneratorUtil idGeneratorUtil;

	@Override
	public Integer getAllUsersWithPage(UserListSearchRequest request, List<UserListResponse> users) throws Exception{

		List<User> userEntitys = userDaoImpl.selectByPage(request);

		for(User userEntity : userEntitys){
			List<Role> roles = roleDaoImpl.selectByUserId(userEntity.getUserId());
			UserListResponse user = new UserListResponse();
			user.setUserId(userEntity.getUserId());
			user.setUserName(userEntity.getUserName());
			user.setRealName(userEntity.getRealName());
			user.setEmployeeCode(userEntity.getEmployeeCode());
			user.setEmail(userEntity.getEmail());
			user.setMobile(userEntity.getMobile());
			user.setIsActive(userEntity.getIsActive());
			if(roles != null && roles.size() > 0){
				user.setRole(roles.get(0));
			}
			users.add(user);
		}

		Integer num = userDaoImpl.countBySearch(request);

		return num;

	}

	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor=RuntimeException.class)
	public void deleteUser(String userId) throws Exception{

		userDaoImpl.deleteById(userId);

		userRoleDaoImpl.deleteByUserId(userId);

		deleteUserSession(userId);

	}

	@Override
	public void modifyUserStatus(String userId, boolean isActive) throws SQLException {

		String sessionid = OperatorSidHolder.get();
		String username = redisClient.get(sessionid);

		User user = new User();
		user.setUserId(userId);
		user.setIsActive(isActive);
		user.setUpdatedby(username);
		user.setUpdated(new DateTime());
		userDaoImpl.update(user);

		if(!isActive){
			deleteUserSession(userId);
			}

	}

	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor=RuntimeException.class)
	public void insertUser(UserSaveRequest request) throws Exception{

		String sessionid = OperatorSidHolder.get();
		String username = redisClient.get(sessionid);

		if(StringUtils.isBlank(username)){
			throw ZtrBizException.instance(Const.FO_RBAC_CODE_1019, Const.FO_RBAC_REASON_1019) ;
		}

		List<User> users = userDaoImpl.selectByUserName(request.getUserName());

		if(users == null || users.size() < 1){
			User user = new User();
			String userId = idGeneratorUtil.getUserId() ;
			user.setUserId(userId);
			user.setUserName(request.getUserName());
			user.setRealName(request.getRealName());
			user.setEmployeeCode(request.getEmployeeCode());
			user.setMobile(request.getMobile());
			user.setEmail(request.getEmail());
			user.setIsActive(true);
			user.setCreatedby(username);
			user.setUpdatedby(username);
			user.setCreated(new DateTime());
			user.setUpdated(new DateTime());
			if(StringUtils.isNotBlank(request.getPassword())){
				user.setPassword(SignUtil.signPassword(request.getPassword(), Const.ZTRAVEL_OPERATOR_USER));
			}
			userDaoImpl.insert(user);

			UserRole userRole = new UserRole();
			userRole.setUserId(userId);
			userRole.setRoleId(request.getRoleId());
			userRoleDaoImpl.insert(userRole);
		}else{
			throw ZtrBizException.instance(Const.FO_RBAC_CODE_1018, Const.FO_RBAC_REASON_1018) ;
		}

	}

	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor=RuntimeException.class)
	public void updateUser(UserSaveRequest request) throws Exception{

		String sessionid = OperatorSidHolder.get();
		String username = redisClient.get(sessionid);

		if(StringUtils.isBlank(username)){
			throw ZtrBizException.instance(Const.FO_RBAC_CODE_1019, Const.FO_RBAC_REASON_1019) ;
		}

		User user = new User();
		user.setUserId(request.getUserId());
		user.setUserName(request.getUserName());
		user.setRealName(request.getRealName());
		user.setEmployeeCode(request.getEmployeeCode());
		user.setMobile(request.getMobile());
		user.setEmail(request.getEmail());
		user.setUpdatedby(username);
		user.setUpdated(new DateTime());
		if(StringUtils.isNotBlank(request.getPassword())){
			user.setPassword(SignUtil.signPassword(request.getPassword(), Const.ZTRAVEL_OPERATOR_USER));
			deleteUserSession(request.getUserId());
		}
		userDaoImpl.update(user);

		userRoleDaoImpl.deleteByUserId(request.getUserId());

		UserRole userRole = new UserRole();
		userRole.setUserId(request.getUserId());
		userRole.setRoleId(request.getRoleId());
		userRoleDaoImpl.insert(userRole);

	}

	private void deleteUserSession(String userId){

		String sessionids = redisClient.get(userId + Const.ZTRAVEL_OPERATOR_USER_KEY);
		if(StringUtils.isNotBlank(sessionids)){
			for(String id : sessionids.split(",")){
				redisClient.delete(id);
			}
		}
		redisClient.delete(userId + Const.ZTRAVEL_OPERATOR_USER_KEY);

	}

}
