package com.ztravel.common.freemarker;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.constants.Const;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.common.rbac.service.PermissionCommonService;
import com.ztravel.common.rbac.wo.PermissionWo;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * 通过权限获取菜单
 * @author tengmeilin
 *
 */
@Controller
public class getMenubyRbac implements TemplateMethodModelEx {

	private static Logger logger = RequestIdentityLogger.getLogger(getMenubyRbac.class);
	private final RedisClient redisClient = RedisClient.getInstance();

	@Resource
	private PermissionCommonService permissionCommonServiceImpl;

	@SuppressWarnings("rawtypes")
	@Override
	public Object exec(List arguments) throws TemplateModelException {
		List<PermissionWo> allPerms = null;
		String username = redisClient.get(Const.ZTRAVEL_OPERATOR_ADMIN);

		try {
			if(redisClient.get(OperatorSidHolder.get()).equals(username)){
				allPerms = permissionCommonServiceImpl.getAllPermissions();
			}else{
				allPerms = permissionCommonServiceImpl.getMenubyPermissions();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return allPerms;
	}

}
