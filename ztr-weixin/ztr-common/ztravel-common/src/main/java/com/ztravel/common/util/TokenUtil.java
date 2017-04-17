package com.ztravel.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.common.holder.TokenHolder;

/**
 * @author wanhaofan
 * */
public class TokenUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(TokenUtil.class);

	private static final RedisClient redisClient = RedisClient.getInstance();

	private static final String TOKEN_PREFIX = "TOKEN:" ;

	public static String getTokens(String memberId){
		return redisClient.get(TOKEN_PREFIX + memberId);
	}

	public static void setTokens(String memberId){
		try{
			if(redisClient.exists(TOKEN_PREFIX + memberId)){
				String tokens = getTokens(memberId) ;
				if(tokens.indexOf(TokenHolder.get()) < 0){
					tokens += "," + TokenHolder.get() ;
				}
				redisClient.setNoJSON(TOKEN_PREFIX + memberId, tokens, 7 * 24 * 60 * 60);
			}else{
				redisClient.setNoJSON(TOKEN_PREFIX + memberId, "," + TokenHolder.get(), 7 * 24 * 60 * 60);
			}
		}catch(Exception e){
			logger.error("memberId:" + memberId + " setTokens error", e);
		}
	}

	public static void deleteToken(String memberId){
		try{
			if(redisClient.exists(TOKEN_PREFIX + memberId)){
				String tokens = getTokens(memberId) ;
				if(tokens.indexOf(TokenHolder.get()) >= 0){
					tokens = tokens.replaceAll("," + TokenHolder.get(), "") ;
				}
				redisClient.setNoJSON(TOKEN_PREFIX + memberId, tokens, 7 * 24 * 60 * 60);
			}else{
				redisClient.setNoJSON(TOKEN_PREFIX + memberId, "," + TokenHolder.get(), 7 * 24 * 60 * 60);
			}
		}catch(Exception e){
			logger.error("memberId:" + memberId + " deleteToken error", e);
		}
	}

	public static void kickToken(String memberId){
		try{
			if(redisClient.exists(TOKEN_PREFIX + memberId)){
				String tokens = getTokens(memberId) ;
				String[] tokenArray = tokens.split(",") ;
				for(String token:tokenArray){
					if(redisClient.exists(token)){
						MemberSessionBean bean = redisClient.get(token, MemberSessionBean.class) ;
						if(memberId.equals(bean.getMemberId())){
							bean.setIsActive(false);
							redisClient.set(token, bean);
						}
					}
				}
			}
		}catch(Exception e){
			logger.error("memberId:" + memberId + " kickToken error", e);
		}
	}

	public static void addToken(String memberId){
		try{
			if(redisClient.exists(TOKEN_PREFIX + memberId)){
				String tokens = getTokens(memberId) ;
				String[] tokenArray = tokens.split(",") ;
				for(String token:tokenArray){
					if(redisClient.exists(token)){
						MemberSessionBean bean = redisClient.get(token, MemberSessionBean.class) ;
						if(memberId.equals(bean.getMemberId())){
							bean.setIsActive(true);
							redisClient.set(token, bean);
						}
					}
				}
			}
		}catch(Exception e){
			logger.error("memberId:" + memberId + " addToken error", e);
		}
	}
}
