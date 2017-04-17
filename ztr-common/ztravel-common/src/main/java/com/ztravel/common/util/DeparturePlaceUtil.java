package com.ztravel.common.util;

import java.util.List;

import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.constants.RedisKeyConst;

/**
 * 从redis中取产品出发地工具类
 * @author MH
 */
public class DeparturePlaceUtil {

	private static RedisClient redisClient = RedisClient.getInstance();

	public static List<String> getDeparturePlaces(){
		return redisClient.get(RedisKeyConst.DEPARTURE_PLACE_KEY, List.class);
	}

}
