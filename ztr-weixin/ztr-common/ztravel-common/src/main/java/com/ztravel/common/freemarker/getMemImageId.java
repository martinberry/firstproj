package com.ztravel.common.freemarker;

import java.util.List;

import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.util.ZtrStringUtils;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;


/**
 * 根据用户id获取头像id
 * @author liuzhuo
 *
 */
public class getMemImageId implements TemplateMethodModelEx{
	
	private final RedisClient redisClient = RedisClient.getInstance();

	@Override
	public Object exec(List arguments) throws TemplateModelException {
		
		Object memId = (String) arguments.get(0);
		
		if(memId == null) {
			return null;
		}
		return redisClient.get(ZtrStringUtils.contact("MemImageIdPrefix",memId.toString()));
	}

}
