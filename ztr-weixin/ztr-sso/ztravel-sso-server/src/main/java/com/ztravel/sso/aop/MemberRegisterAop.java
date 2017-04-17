package com.ztravel.sso.aop;

import java.util.Set;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.travelzen.framework.config.tops.TopsConfEnum.ConfScope;
import com.travelzen.framework.config.tops.TopsConfReader;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.bean.AjaxResponse;

/**
 * 用户注册aop
 * @author liuzhuo
 *
 */
//@Component
//@Aspect
public class MemberRegisterAop {
	
	
	private static final String REG_WHITELIST_KEY = "regWhiteListKey";
	
	private static final RedisClient redisClient = RedisClient.getInstance();
	
	/**
	 * 灰度发布，根据用户手机号来做白名单
	 * @param jp
	 * @param request
	 * @param retVal
	 * @return 
	 */
	@Around(value="@annotation(com.ztravel.sso.annotation.RegWhilteListFilter)")
	public AjaxResponse registerGrayPostEndPoint(ProceedingJoinPoint pjp) {
		
		String mobile = pjp.getArgs()[0].toString();
		String graypost = getRegStrategy();
		if("true".equals(graypost)) {
			Set<String> mobileWhiteList = redisClient.get(REG_WHITELIST_KEY, Set.class);
			if(!mobileWhiteList.contains(mobile)){
				return AjaxResponse.instance("NOT_IN_WHITE_LIST", "NOT_IN_WHITE_LIST");
			}else{
				try {
					return (AjaxResponse) pjp.proceed();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}else{
			try {
				return (AjaxResponse) pjp.proceed();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		
		return null;
		
	}
	
	
	
	/**
	 * 获取注册策略
	 * @return
	 */
	private String getRegStrategy() {
		
		return TopsConfReader.getConfContent("ztr-sso/regStrategy.properties", "graypost", ConfScope.R);
		
	}
	

}
