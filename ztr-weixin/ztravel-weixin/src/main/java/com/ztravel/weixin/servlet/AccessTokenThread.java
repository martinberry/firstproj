package com.ztravel.weixin.servlet;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.site.lookup.util.StringUtils;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.bean.AccessToken;
import com.ztravel.common.holder.WechatAccountHolder;
import com.ztravel.common.util.HttpUtil;

/**
 *
 * @author wanhaofan
 *
 */
public class AccessTokenThread implements Runnable{
	private static Logger logger = RequestIdentityLogger.getLogger(AccessTokenThread.class);
	private static final String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token"
			+ "?grant_type=client_credential&appid=" + WechatAccountHolder.APP_ID + "&secret=" + WechatAccountHolder.SECRET ;

	private static final String GET_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=";

	private static final RedisClient redisClient = RedisClient.getInstance() ;

	private static final String ACCESS_TOKEN_TAG = "WECHAT_ACCESS_TOKEN" ;
	private static final String JSAPI_TICKET_TAG = "WECHAT_JSAPI_TICKET" ;

	public static String getAccessToken(){
		return redisClient.get(ACCESS_TOKEN_TAG) ;
	}

	public static String getJsapiTicket(){
		return redisClient.get(JSAPI_TICKET_TAG) ;
	}

	@Override
	public void run() {
		while(true){
			AccessToken accessToken = getAccessToken2() ;
			logger.info("get wechat access_token:{} success, expire in {} seconds", accessToken.getValue(), accessToken.getExpire());
			if(accessToken.getExpire() != 0){
				try{
					redisClient.setNoJSON(ACCESS_TOKEN_TAG, accessToken.getValue(), accessToken.getExpire());
					AccessToken ticket = getJsapiTicket2(accessToken.getValue());
					logger.info("get wechat ticket:{} success, expire in {} seconds", ticket.getValue(), ticket.getExpire());
					if(ticket.getExpire() != 0){
						redisClient.setNoJSON(JSAPI_TICKET_TAG, ticket.getValue(), ticket.getExpire());
						Thread.sleep((accessToken.getExpire() - 200) * 1000);
					}
				}catch(Exception e){
					logger.error(e.getMessage(), e);
					try {
						Thread.sleep(60 * 1000);
					} catch (InterruptedException e1) {
						logger.error(e.getMessage(), e1);
					}
				}
			}
		}
	}

	private AccessToken getAccessToken2(){
		AccessToken token = new AccessToken() ;
		try{
			String result = HttpUtil.httpGet(GET_ACCESS_TOKEN_URL, "utf-8") ;
			if(StringUtils.isNotEmpty(result)){
				JSONObject json = JSONObject.parseObject(result) ;
				String value = json.containsKey("access_token") ? json.getString("access_token") : "" ;
				int expire = json.containsKey("expires_in") ? json.getIntValue("expires_in") : 0 ;
				token.setExpire(expire);
				token.setValue(value);
			}
		}catch(Exception e){
			token.setExpire(0);
			logger.error(e.getMessage(), e);
		}
		return token ;
	}

	private AccessToken getJsapiTicket2(String accessToken){
		AccessToken ticket = new AccessToken() ;
		try{
			String result = HttpUtil.httpGet(GET_TICKET_URL + accessToken + "&type=jsapi", "utf-8") ;
			if(StringUtils.isNotEmpty(result)){
				JSONObject json = JSONObject.parseObject(result) ;
				String value = json.containsKey("ticket") ? json.getString("ticket") : "" ;
				int expire = json.containsKey("expires_in") ? json.getIntValue("expires_in") : 0 ;
				ticket.setExpire(expire);
				ticket.setValue(value);
			}
		}catch(Exception e){
			ticket.setExpire(0);
			logger.error(e.getMessage(), e);
		}
		return ticket ;
	}

}
