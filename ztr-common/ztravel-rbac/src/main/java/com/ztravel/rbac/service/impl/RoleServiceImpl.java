package com.ztravel.rbac.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.constants.Const;
import com.ztravel.common.entity.PaginationEntity;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.common.util.IdGeneratorUtil;
import com.ztravel.rbac.dao.RoleDao;
import com.ztravel.rbac.dao.RolePermissionDao;
import com.ztravel.rbac.dao.UserRoleDao;
import com.ztravel.rbac.entity.Role;
import com.ztravel.rbac.entity.RolePermission;
import com.ztravel.rbac.po.RoleListResponse;
import com.ztravel.rbac.po.RoleSaveRequest;
import com.ztravel.rbac.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

	private final RedisClient redisClient = RedisClient.getInstance();

	@Resource
	private RoleDao roleDaoImpl;

	@Resource
	private UserRoleDao userRoleDaoImpl;

	@Resource
	private RolePermissionDao rolePermissionDaoImpl;
	
	@Resource
	private IdGeneratorUtil idGeneratorUtil ;

	@Override
	public List<Role> getAllRoles() throws Exception{

		List<Role> roles = roleDaoImpl.selectAll();

		return roles;

	}

	@Override
	public Integer getAllRolesWithPage(PaginationEntity page, List<RoleListResponse> roles) throws Exception{

		List<Role> roleEntitys = roleDaoImpl.selectByPage(page);

		for(Role roleEntity : roleEntitys){
			Integer count = userRoleDaoImpl.countByRoleId(roleEntity.getRoleId());
			RoleListResponse role = new RoleListResponse();
			role.setRoleId(roleEntity.getRoleId());
			role.setRoleName(roleEntity.getRoleName());
			role.setDescription(roleEntity.getDescription());
			role.setCount(count);
			roles.add(role);
		}

		Integer num = roleDaoImpl.countAll();

		return num;

	}

	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor=RuntimeException.class)
	public void deleteRole(String roleId) throws Exception{

		roleDaoImpl.deleteById(roleId);

		userRoleDaoImpl.deleteByRoleId(roleId);

		rolePermissionDaoImpl.deleteByRoleId(roleId);

	}

	@Override
	public Role getRoleById(String roleId) throws Exception{

		return roleDaoImpl.selectById(roleId);

	}

	@Override
	public List<String> getPermissionByRoleId(String roleId) throws Exception{

		List<RolePermission> rolePerms = rolePermissionDaoImpl.selectByRoleId(roleId);

		List<String> permissions = new ArrayList<>();
		for(RolePermission rolePerm : rolePerms){
			permissions.add(rolePerm.getPermissionId());
		}

		return permissions;

	}

	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor=RuntimeException.class)
	public void insertRole(RoleSaveRequest request) throws Exception{

		String sessionid = OperatorSidHolder.get();
		String username = redisClient.get(sessionid);

		List<Role> roles = roleDaoImpl.selectByRoleName(request.getRoleName());

		if(roles == null || roles.size() < 1){
			Role role = new Role();
			String roleId = idGeneratorUtil.getRoleId() ;
			role.setRoleId(roleId);
			role.setRoleName(request.getRoleName());
			role.setCreatedby(username);
			role.setCreated(new DateTime());
			role.setUpdatedby(username);
			role.setUpdated(new DateTime());
			roleDaoImpl.insert(role);

			List<RolePermission> rolePerms = new ArrayList<>();
			for(String permissionId : request.getPermissionIds()){
				RolePermission rolePerm = new RolePermission();
				rolePerm.setRoleId(roleId);
				rolePerm.setPermissionId(permissionId);
//				rolePermissionDaoImpl.insert(rolePerm);
				rolePerms.add(rolePerm);
			}
			rolePermissionDaoImpl.insertBatch(rolePerms);
		}else{
			throw ZtrBizException.instance(Const.FO_RBAC_CODE_1007, Const.FO_RBAC_REASON_1007) ;
		}

	}

	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor=RuntimeException.class)
	public void updateRole(RoleSaveRequest request) throws Exception{

		String sessionid = OperatorSidHolder.get();
		String username = redisClient.get(sessionid);

		Role role = new Role();
		role.setRoleId(request.getRoleId());
		role.setRoleName(request.getRoleName());
		role.setUpdatedby(username);
		role.setUpdated(new DateTime());
		roleDaoImpl.update(role);

		rolePermissionDaoImpl.deleteByRoleId(request.getRoleId());

		List<RolePermission> rolePerms = new ArrayList<>();
		for(String permissionId : request.getPermissionIds()){
			RolePermission rolePerm = new RolePermission();
			rolePerm.setRoleId(request.getRoleId());
			rolePerm.setPermissionId(permissionId);
			rolePerms.add(rolePerm);
		}
		rolePermissionDaoImpl.insertBatch(rolePerms);

	}

}
