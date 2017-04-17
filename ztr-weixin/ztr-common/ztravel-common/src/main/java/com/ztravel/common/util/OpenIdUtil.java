package com.ztravel.common.util;

import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.holder.TokenHolder;

public class OpenIdUtil {
	private static final RedisClient redisClient = RedisClient.getInstance();

	private static final String OPEN_ID = "OPENID" ;


	public static String getOpenId(){
		return redisClient.get(TokenHolder.get() + ":" + OPEN_ID) ;
	}

	public static void setOpenId(String openId){
		redisClient.setNoJSON(TokenHolder.get() + ":" + OPEN_ID, openId, 7200);
	}

}
