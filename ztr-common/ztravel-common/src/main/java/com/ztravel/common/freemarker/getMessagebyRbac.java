package com.ztravel.common.freemarker;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.constants.Const;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.common.rbac.service.PermissionCommonService;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * 获取消息权限
 * @author tengmeilin
 *
 */
@Controller
public class getMessagebyRbac implements TemplateMethodModelEx {

	private final RedisClient redisClient = RedisClient.getInstance();

	@Resource
	private PermissionCommonService permissionCommonServiceImpl;

	@SuppressWarnings("rawtypes")
	@Override
	public Object exec(List arguments) throws TemplateModelException {
		String username = redisClient.get(Const.ZTRAVEL_OPERATOR_ADMIN);
		return permissionCommonServiceImpl.getMessagePermissions(redisClient.get(OperatorSidHolder.get()).equals(username));
	}

}
