package com.ztravel.test.redis;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.travelzen.framework.redis.client.RedisClient;

public class RedisCommand {

	private static final RedisClient client = RedisClient.getInstance();


    @Test
	public void getValueFromRedis() {
    	String key = "xiaoqing.liu:9da15d91-eb95-4efc-a003-1433317a611e:autoLogin";
		System.out.println(JSON.toJSONString(client.get(key)));
		System.out.println( System.currentTimeMillis()/1000);
	}

}
