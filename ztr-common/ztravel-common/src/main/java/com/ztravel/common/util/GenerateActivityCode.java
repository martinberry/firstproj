package com.ztravel.common.util;

import com.travelzen.framework.redis.client.RedisClient;

public class GenerateActivityCode {

	private static final String key = "ZTRAVEL_ACTIVITY";
	private static final RedisClient redis = RedisClient.getInstance();

	public static String generateActivityCode() throws Exception{
		Long num = redis.incr(key);
		String code = "HD" + String.format("%06d", num);
		return code;
	}
}
