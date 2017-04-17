package com.ztravel.common.freemarker;

import java.util.List;

import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.holder.OperatorSidHolder;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * 获取当前登录管理员
 * @author liuzhuo
 *
 */
public class getCurrOperator implements TemplateMethodModelEx {
	
	private final RedisClient redisClient = RedisClient.getInstance();

	@SuppressWarnings("rawtypes")
	@Override
	public Object exec(List arguments) throws TemplateModelException {
		return redisClient.get(OperatorSidHolder.get());
	}

}
