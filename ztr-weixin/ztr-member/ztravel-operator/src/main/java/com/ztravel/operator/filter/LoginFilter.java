package com.ztravel.operator.filter;

import java.io.IOException;
import java.net.URLDecoder;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.constants.Const;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.common.util.WebEnv;
import com.ztravel.operator.po.UserSession;
import com.ztravel.operator.service.SignInService;

/**
 *
 * @author tengmeilin
 *
 */
@Component
public class LoginFilter implements Filter{

	private static final RedisClient redisClient = RedisClient.getInstance();

	@Resource
	private SignInService signInServiceImpl;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		String requestUrl = request.getRequestURI();

		if(requestUrl.contains("/signIn") || requestUrl.contains("/getLoginFailureCount") || requestUrl.contains("/captcha")
				|| requestUrl.contains("/signOut") || requestUrl.contains("/resources/javascripts") || requestUrl.contains("/resources/css")) {
			chain.doFilter(request, response);
			return ;
		}

		StringBuffer redirectUrl = new StringBuffer();
		redirectUrl.append(WebEnv.get("server.path.operaServer"));
//		redirectUrl.append(request.getContextPath().toString());
		StringBuffer mainPage = new StringBuffer(redirectUrl);
		redirectUrl.append("/user/login");
		mainPage.append("/user/main");

		String currsessionid = OperatorSidHolder.get();

		if(redisClient.exists(currsessionid)){
			redisClient.expire(currsessionid, 30 * 60);
			if(requestUrl.contains("/login")){
				response.sendRedirect(mainPage.toString());
				return ;
				}
		}

		/**
		 * ADD BY ZHUO.LIU TO FIX AJAX CALL TIME OUT 05.04
		 */
		boolean isAjaxRequest = (request.getHeader("X-Requested-With") != null && "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString()));
	    if(isAjaxRequest){
//	        response.setStatus(ResponseConstants.OPERA_AJAX_CALL_TIME_OUT_STATUS);
			chain.doFilter(request, response);
	        return ;
	    }
	    /****-----****/

		if(!redisClient.exists(currsessionid)){

			// 检查用户浏览器是否发送了上次登录的用户名和sessionid
			// 如果是，则为用户自动登陆。
			Cookie ckUsername = WebUtils.getCookie(request, "autoLoginUser");
			Cookie ckSessionid = WebUtils.getCookie(request, "autoLoginSessionid");

			if(ckUsername == null || ckSessionid == null){
				if(!requestUrl.contains("/login")){
					response.sendRedirect(redirectUrl.toString());
					return ;
					}else{
						chain.doFilter(request, response);
						return ;
					}
			}

			// 此sessionid是上次用户登录时保存于用户端的识别码，用于用户后续访问的自动登录。不是本次访问的session id
			String sessionid = ckSessionid.getValue();
			String username = URLDecoder.decode(ckUsername.getValue(),"utf-8");
//			String ip = util.getIpAddrByRequest(request);

			//判断浏览器
//			UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
//		    Browser browser = userAgent.getBrowser();

			UserSession userSession = redisClient.get(username + ":" +  sessionid + Const.ZTRAVEL_OPERATOR_AUTOLOGIN_KEY, UserSession.class);

			// 如果在数据库中找到了相应记录，则说明可以自动登录
			if (userSession != null && userSession.getSessionid().equals(sessionid)
					&& userSession.getExpireTime() > System.currentTimeMillis()/1000) {
				if(signInServiceImpl.checkUsernamePassword(username, userSession.getPassword(), currsessionid, false)){
					redisClient.set(currsessionid, username, 30 * 60);
					if(requestUrl.contains("/login")){
						response.sendRedirect(mainPage.toString());
						return ;
						}
					}else{
						redisClient.delete(username + ":" +  sessionid + Const.ZTRAVEL_OPERATOR_AUTOLOGIN_KEY);
						ckUsername.setMaxAge(0);
						ckSessionid.setMaxAge(0);
						if(!requestUrl.contains("/login")){
							response.sendRedirect(redirectUrl.toString());
							return ;
							}
					}
				}else{
					if(!requestUrl.contains("/login")){
						response.sendRedirect(redirectUrl.toString());
						return ;
						}
					}
		}

		chain.doFilter(request, response);

	}

	@Override
	public void destroy() {}


}
