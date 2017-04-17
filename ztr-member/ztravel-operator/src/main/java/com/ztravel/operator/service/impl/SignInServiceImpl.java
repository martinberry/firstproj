package com.ztravel.operator.service.impl;

import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.Const;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.common.security.SignUtil;
import com.ztravel.common.util.CookieFactory;
import com.ztravel.operator.po.LoginUser;
import com.ztravel.operator.po.UserSession;
import com.ztravel.operator.service.SignInService;
import com.ztravel.operator.util.util;
import com.ztravel.rbac.dao.UserDao;
import com.ztravel.rbac.entity.User;

@Service
public class SignInServiceImpl implements SignInService{

	private static Logger logger = RequestIdentityLogger.getLogger(SignInServiceImpl.class);

	private static int ONE_DAY = 24 * 60 * 60;
	private static int ONE_MONTH= 30 * 24 * 60 * 60;
	private static int COOKIE_TIME = 60 * 60 * 24 * 30 * 3;

	@Resource
	private UserDao userDaoImpl;

	private final RedisClient redisClient = RedisClient.getInstance();

	@Override
	public AjaxResponse checkAndLogin(LoginUser user, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String username = StringUtils.trim(user.getUserName());
		String password = StringUtils.trim(user.getPassword());
		String authCode = StringUtils.trim(user.getAuthCode());

		String sessionid = OperatorSidHolder.get();

		if( ! checkUsernamePassword(username, password, sessionid, true)){
			return AjaxResponse.instance(Const.FO_OPER_CODE_1003, Const.FO_OPER_REASON_1003);
		}

		if( ! checkAuthCode(username, authCode, request)){
			return AjaxResponse.instance(Const.FO_OPER_CODE_1002, Const.FO_OPER_REASON_1002);
		}

		redisClient.set(sessionid, username, 30 * 60);

		if(user.getRememberMe()){
			setAutoLogin(sessionid, username, SignUtil.signPassword(password, Const.ZTRAVEL_OPERATOR_USER), request, response);
		}

		return AjaxResponse.instance(Const.SO_OPER_CODE_1001, Const.SUCCESS);

	}

	@Override
	public void removeAutoLogin(String username, String sessionid, HttpServletRequest request){

		Cookie ckSessionid = WebUtils.getCookie(request, "autoLoginSessionid");
		Cookie ckSID = WebUtils.getCookie(request, "SID");

		if( StringUtils.isNotBlank(username)){
			//删除用户登录失败次数
			redisClient.delete(username);

			if(ckSessionid != null){
				String sessid = ckSessionid.getValue();
				//删除用户自动登录
				redisClient.delete(username + ":" +  sessid + Const.ZTRAVEL_OPERATOR_AUTOLOGIN_KEY);
				ckSessionid.setMaxAge(0);
			}
		}

		List<User> users = userDaoImpl.selectByUserName(username);

		if(users != null && users.size() > 0){
			User user = users.get(0);
			if(user != null && StringUtils.isNotBlank(user.getUserId())){
				String sessionids = redisClient.get(user.getUserId() + Const.ZTRAVEL_OPERATOR_USER_KEY);
				if(StringUtils.isNotBlank(sessionids)){
					//删除用户所有登录会话中本次会话
					redisClient.set(user.getUserId() + Const.ZTRAVEL_OPERATOR_USER_KEY, sessionids.replace(sessionid+",", ""), ONE_MONTH);
				}
			}
		}

		if( StringUtils.isNotBlank(sessionid)){
			//删除用户本次登录会话
			redisClient.delete(sessionid);
		}

		if(ckSID != null){
			ckSID.setMaxAge(0);
		}

	}

	@Override
	public int getLoginFailureCount(String username){

		int count = 0;
		Integer value = redisClient.get(username, java.lang.Integer.class);
		if (value != null) {
			count = value.intValue();
		}
		return count;
	}

	private Boolean checkAuthCode(String username, String authCode, HttpServletRequest request) throws Exception{

		int count = getLoginFailureCount(username);
		if(count>=3){

			if(StringUtils.isBlank(authCode)){
				return false;
			}

			String captchaCode = redisClient.get(Const.KAPTCHA_OPER_LOGIN_KEY + ":" + OperatorSidHolder.get());

			if (StringUtils.isBlank(captchaCode)) {
				throw ZtrBizException.instance(Const.FO_OPER_CODE_1005, Const.FO_OPER_REASON_1005);
			}

			if (!captchaCode.equalsIgnoreCase(authCode)) {
				return false;
			}
		}

		logger.info("checkAuthCode successfully:" + username);

		return true;
	}

	@Override
	public Boolean checkUsernamePassword(String username, String password, String sessionid, boolean isBefore){

		int count = getLoginFailureCount(username);

		List<User> users = userDaoImpl.selectByUserName(username);

		if(users == null || users.size() < 1){
			// 未注册用户，更新Redis
			redisClient.set(username, Integer.valueOf(++count), ONE_DAY);
			return false;
		}

		User user = users.get(0);
		if(user == null || !user.getIsActive()){
			// 用户被挂起，更新Redis
			redisClient.set(username, Integer.valueOf(++count), ONE_DAY);
			return false;
		}

		if(isBefore){
			if (!SignUtil.verifyPassword(password, Const.ZTRAVEL_OPERATOR_USER, user.getPassword())) {
				// 密码不正确，更新Redis
				redisClient.set(username, Integer.valueOf(++count), ONE_DAY);
				return false;
			}
		}else{
			if (!user.getPassword().equals(password)) {
				// 密码不正确，更新Redis
				redisClient.set(username, Integer.valueOf(++count), ONE_DAY);
				return false;
			}
		}

		if(StringUtils.isNotBlank(user.getDescription()) && user.getDescription().equals("administrator")){
			redisClient.setNoJSON(Const.ZTRAVEL_OPERATOR_ADMIN, username);
			redisClient.persist(Const.ZTRAVEL_OPERATOR_ADMIN);
		}

		if(StringUtils.isNotBlank(user.getUserId())){
			if(redisClient.exists(user.getUserId() + Const.ZTRAVEL_OPERATOR_USER_KEY)){
				String sessionids = redisClient.get(user.getUserId() + Const.ZTRAVEL_OPERATOR_USER_KEY);
				if(!sessionids.contains(sessionid)){
					sessionids += sessionid + ",";
					redisClient.set(user.getUserId() + Const.ZTRAVEL_OPERATOR_USER_KEY, sessionids, ONE_MONTH);
				}
			}else{
				redisClient.set(user.getUserId() + Const.ZTRAVEL_OPERATOR_USER_KEY, sessionid + ",", ONE_MONTH);
			}
		}

		logger.info("checkUsernamePassword successfully,用户名:" + username);

		return true;
	}

	private void setAutoLogin(String sessionid, String username, String password, HttpServletRequest request, HttpServletResponse response) throws Exception{

		UserSession userSession = new UserSession();
		userSession.setUserName(username);
		userSession.setSessionid(sessionid);
		userSession.setPassword(password);

		Cookie ckUsername = CookieFactory.buildOperRememberMe("autoLoginUser", URLEncoder.encode(username, "utf-8"), COOKIE_TIME);
		Cookie ckSessionid = CookieFactory.buildOperRememberMe("autoLoginSessionid", sessionid, COOKIE_TIME);

		response.addCookie(ckUsername);
		response.addCookie(ckSessionid);

		long currentTime = System.currentTimeMillis();
		long expireTime = currentTime/1000+(long)COOKIE_TIME;

		userSession.setExpireTime(expireTime);

		String ip = util.getIpAddrByRequest(request);
		userSession.setIp(ip);

		//判断浏览器
//		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
//	    Browser browser = userAgent.getBrowser();
//	    userSession.setBrowser(browser.name());

		// 在redis中插入相应记录
		redisClient.set(username + ":" +  sessionid + Const.ZTRAVEL_OPERATOR_AUTOLOGIN_KEY, userSession, COOKIE_TIME);

}


}
