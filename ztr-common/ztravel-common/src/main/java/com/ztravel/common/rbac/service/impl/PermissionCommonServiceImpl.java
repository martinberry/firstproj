package com.ztravel.common.rbac.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.common.rbac.dao.PermissionDao;
import com.ztravel.common.rbac.service.PermissionCommonService;
import com.ztravel.common.rbac.wo.Permission;
import com.ztravel.common.rbac.wo.PermissionWo;

@Service
public class PermissionCommonServiceImpl implements PermissionCommonService {

	private final RedisClient redisClient = RedisClient.getInstance();

	@Resource
	private PermissionDao permissionDaoImpl;

	@Override
	public List<PermissionWo> getMenubyPermissions() {

		List<Permission> perms = permissionDaoImpl.selectByType("MENU_1");

		List<PermissionWo> permissions = new ArrayList<>();
		for(Permission perm : perms){
			List<Permission> ps = permissionDaoImpl.selectByUserName(redisClient.get(OperatorSidHolder.get()), null);
			List<Permission> children = new ArrayList<>();
			for(Permission p :ps){
				if(p.getParentId().equals(perm.getPermissionId()) && !p.getPermissionName().equals("订单消息通知")){
					children.add(p);
				}
			}
			if(children != null && children.size() > 0){
				PermissionWo permission = new PermissionWo();
				permission.setPermissionId(perm.getPermissionId());
				permission.setPermissionName(perm.getPermissionName());
				permission.setUrl(perm.getUrl());
				permission.setType(perm.getType());
				permission.setDescription(perm.getDescription());
				permission.setPermissions(children);
				permissions.add(permission);
			}
		}

		return permissions;

	}

	@Override
	public String getMessagePermissions(boolean isAdmin) {

		List<Permission> perms = new ArrayList<>();

		if(isAdmin){
			perms = permissionDaoImpl.selectByPermissionName("订单消息通知");
		}else{
			perms = permissionDaoImpl.selectByUserName(redisClient.get(OperatorSidHolder.get()),"订单消息通知");
		}

		if(perms != null && perms.size() > 0){
			return perms.get(0).getUrl();
		}

		return null;

	}

	@Override
	public List<PermissionWo> getAllPermissions() {

		List<Permission> perms = permissionDaoImpl.selectByType("MENU_1");

		List<PermissionWo> permissions = new ArrayList<>();
		for(Permission perm : perms){
			List<Permission> children = permissionDaoImpl.selectByParentId(perm.getPermissionId());
			PermissionWo permission = new PermissionWo();
			permission.setPermissionId(perm.getPermissionId());
			permission.setPermissionName(perm.getPermissionName());
			permission.setType(perm.getType());
			permission.setDescription(perm.getDescription());
			permission.setPermissions(children);
			permissions.add(permission);
		}

		return permissions;

	}

	@Override
	public void addPermission(Permission permission) {
		permissionDaoImpl.insert(permission);
	}

}
