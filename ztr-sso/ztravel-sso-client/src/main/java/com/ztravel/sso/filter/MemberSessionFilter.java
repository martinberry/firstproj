package com.ztravel.sso.filter;

import java.io.IOException;

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

import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.common.enums.RedisTime;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.sso.client.service.SSOClientService;
import com.ztravel.sso.dict.SSOConstants;
import com.ztravel.sso.po.SSOBasicEntity;
import com.ztravel.sso.security.AuthUrlAccessable;

@Component
public class MemberSessionFilter implements Filter{

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(MemberSessionFilter.class);

	@Resource
	private SSOClientService ssoClientService ;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOGGER.debug(filterConfig.getFilterName() + " init...");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		MemberSessionBean memberSessionBean = null ;
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		boolean isPage = req.getRequestURI().indexOf(".js") < 0 && req.getRequestURI().indexOf(".css") < 0 ;
		boolean isAjax = req.getHeader("X-Requested-With") != null && "XMLHttpRequest".equals(req.getHeader("X-Requested-With").toString()) ;

		if(!isPage||"true".equals(req.getParameter("auto"))){
			chain.doFilter(request, response);
			return ;
		}

		if(SSOUtil.isMemberExists()) {
			memberSessionBean = SSOUtil.getMemberSessionBean() ;
	    	SSOUtil.expireMemberSessionBean(RedisTime.ONE_MONTH_MILLIS);
		}else {
			memberSessionBean = new MemberSessionBean() ;
			memberSessionBean.setUrl(SSOConstants.MEMBER_LOGIN_PAGE);
			SSOUtil.refreshMemberSessionBean(memberSessionBean);
		}

		Cookie cookie = WebUtils.getCookie(req, "account") ;
		if(cookie != null && !SSOUtil.isLogin()){
			String accountAndPwd = SSOUtil.getFromRedis(cookie.getValue()) ;
			SSOBasicEntity entity = ssoClientService.login(accountAndPwd.split(":")[0], accountAndPwd.split(":")[1]) ;
			if(entity != null && entity.getIsLogin() && entity.getIsActive()){
				try{
					memberSessionBean = SSOUtil.getMemberSessionBean() ;
					ssoClientService.updateLastLoginDate(entity.getId());
				}catch(Exception e){
					//保存最后登陆时间不影响正常流程
					LOGGER.error(e.getMessage(), e);
				}
			}
		}

		String requestUrl = req.getRequestURI().replace(req.getContextPath(),"") ;
		requestUrl = requestUrl.replaceAll("//", "/") ;
		for(String url : AuthUrlAccessable.NEED_LOGIN){
			if(!"".equals(url) && requestUrl.indexOf(url) == 0){
				//用户未登陆
				if(!memberSessionBean.getIsLogin()) {
					if(isAjax){
						resp.setStatus(901);//用户未登录
						return ;
					}else{
						resp.sendRedirect(SSOConstants.MEMBER_LOGIN_PAGE);
						return ;
					}
				}
				if(!memberSessionBean.getIsActive()){
					if(isAjax){
						resp.setStatus(902);//用户被挂起
						return ;
					}else{
						resp.sendRedirect(SSOConstants.MEMBER_LOGIN_PAGE);
						return ;
					}
				}
			}
		}

		for(String url : AuthUrlAccessable.NEED_LOGOUT){
			if(!"".equals(url) && requestUrl.indexOf(url) == 0){
				if(memberSessionBean.getIsLogin()) {
					resp.sendRedirect(memberSessionBean.getUrl());
					return ;
				}
			}
		}

		chain.doFilter(request, response);
		return ;
	}

	@Override
	public void destroy() {

	}

}
