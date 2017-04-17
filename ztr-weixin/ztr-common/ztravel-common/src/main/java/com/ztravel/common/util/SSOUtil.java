package com.ztravel.common.util;

import com.google.code.kaptcha.Constants;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.common.enums.RedisTime;
import com.ztravel.common.holder.TokenHolder;

public class SSOUtil {

	private static final RedisClient redisClient = RedisClient.getInstance();

	public static MemberSessionBean getMemberSessionBean(){
		return redisClient.get(TokenHolder.get(), MemberSessionBean.class) ;
	}

	public static String getMemberId(){
		return getMemberSessionBean() == null ? "" : getMemberSessionBean().getMemberId() ;
	}

	public static boolean isLogin(){
		return getMemberSessionBean() == null ? false : getMemberSessionBean().getIsLogin() ;
	}

	public static String getLoginVerifyCode(){
		return redisClient.get(Constants.KAPTCHA_SESSION_KEY + ":" + TokenHolder.get()) ;
	}

	public static void refreshMemberSessionBean(MemberSessionBean memberSessionBean){
		redisClient.set(TokenHolder.get(), memberSessionBean,RedisTime.ONE_MONTH_MILLIS);
	}

	public static boolean isMemberExists(){
		return redisClient.exists(TokenHolder.get()) ;
	}

	public static void expireMemberSessionBean(int seconds){
		redisClient.expire(TokenHolder.get(), seconds) ;
	}


	public static void refreshMemberSessionBean(MemberSessionBean memberSessionBean,int seconds){
		redisClient.set(TokenHolder.get(), memberSessionBean, seconds);
	}

	public static String getFromRedis(String key){
		return redisClient.get(key);
	}

	public static void deleteFromRedis(String key){
		redisClient.delete(key);
	}

}
