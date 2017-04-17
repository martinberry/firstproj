package com.ztravel.common.util;

import com.travelzen.framework.core.time.DateTimeUtil;
import com.travelzen.framework.redis.client.RedisClient;

public class GenerateOrderConverCode {

	private static final String key= "DH_ORDER";
	private static final RedisClient redis = RedisClient.getInstance();

	public static String generateOrderDhCode() throws Exception{
		Long num = redis.incr(key);
		String code = "DH" +DateTimeUtil.date8().substring(2, 8)+ String.format("%04d", num);
		return code;
	}
}
