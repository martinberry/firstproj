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

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.common.enums.RedisTime;
import com.ztravel.common.holder.TokenHolder;
import com.ztravel.common.util.OpenIdUtil;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.common.util.TokenUtil;
import com.ztravel.sso.client.service.OpenidMemberClientService;
import com.ztravel.sso.client.service.SSOClientService;
import com.ztravel.sso.dict.SSOConstants;
import com.ztravel.sso.po.OpenidMemberEntity;
import com.ztravel.sso.po.SSOBasicEntity;
import com.ztravel.sso.security.AuthUrlAccessable;

@Component
public class WxMemberSessionFilter implements Filter{

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(WxMemberSessionFilter.class);

	@Resource
	private SSOClientService ssoClientService ;

	@Resource
    private OpenidMemberClientService openidMemberClientService ;

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

		if(!isPage){
			chain.doFilter(request, response);
			return ;
		}

		if(SSOUtil.isMemberExists()) {
			memberSessionBean = SSOUtil.getMemberSessionBean() ;
	    	SSOUtil.expireMemberSessionBean(RedisTime.ONE_MONTH_MILLIS);
		} else {
			memberSessionBean = new MemberSessionBean() ;
			memberSessionBean.setUrl(SSOConstants.WXMEMBER_LOGIN_PAGE);
			SSOUtil.refreshMemberSessionBean(memberSessionBean);
		}

		String openId = OpenIdUtil.getOpenId();
		//可以获取到openid，即认为是从微信端进行访问（区别直接从浏览器访问微信模块的场景）
        if (openId != null) {
            OpenidMemberEntity openidMember = openidMemberClientService.selectOpenidMemberByOpenId(openId);
            //通过openid可以在openid_member表中取到映射的member信息
            if (openidMember != null && openidMember.getMemberId() != null) {
                String memberId = openidMember.getMemberId();
                //当前没有用户登陆或者登陆用户与映射的member不同
                if (!SSOUtil.isLogin() || !memberId.equals(SSOUtil.getMemberId())) {
                    if (openidMember.getPreMemberId() != null) {
                        TokenUtil.deleteToken(openidMember.getPreMemberId());
                    }
                    if (openidMember.getToken() != null && !openidMember.getToken().equals(TokenHolder.get())) {
                        SSOUtil.deleteFromRedis(openidMember.getToken());
                    }
                    //使用memberid登陆用户
                    SSOBasicEntity entity = ssoClientService.doLoginByMemberId(memberId);
                    if (entity != null && entity.getIsLogin() && entity.getIsActive()) {
                        //更新OpenidMember
                        updateOpenidMember(openidMember);

                        memberSessionBean.setIsLogin(true);
                        memberSessionBean.setIsActive(true);
                        memberSessionBean.setMemberId(memberId);
                        memberSessionBean.setImageId(entity.getHeadImageId());
                        memberSessionBean.setMobile(entity.getMobile());
                        memberSessionBean.setMid(entity.getMid());
                        memberSessionBean.setNickName(entity.getNickName());
                        SSOUtil.refreshMemberSessionBean(memberSessionBean);
                    }
                }
            }
        }

		Cookie cookie = WebUtils.getCookie(req, "account") ;
		if(cookie != null && !SSOUtil.isLogin()){
			String accountAndPwd = SSOUtil.getFromRedis(cookie.getValue()) ;
			SSOBasicEntity entity = ssoClientService.login(accountAndPwd.split(":")[0], accountAndPwd.split(":")[1]) ;
			if(entity != null && entity.getIsLogin() && entity.getIsActive() ){
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
		for(String url : AuthUrlAccessable.WXNEED_LOGIN){
			if(!"".equals(url) && requestUrl.indexOf(url) == 0){
				//用户未登陆
				if(!memberSessionBean.getIsLogin()) {
					if(isAjax){
						resp.setStatus(901);//用户未登录
						return ;
					}else{
						resp.sendRedirect(SSOConstants.WXMEMBER_LOGIN_PAGE);
						return ;
					}
				}
				if(!memberSessionBean.getIsActive()){
					if(isAjax){
						resp.setStatus(902);//用户被挂起
						return ;
					}else{
						resp.sendRedirect(SSOConstants.WXMEMBER_LOGIN_PAGE);
						return ;
					}
				}
			}
		}

		for(String url : AuthUrlAccessable.WXNEED_LOGOUT){
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

	private void updateOpenidMember(OpenidMemberEntity openidMember) {
	    openidMember.setPreMemberId(null);
        openidMember.setToken(TokenHolder.get());
        openidMember.setLoginTime(new DateTime());
        openidMemberClientService.updateOpenidMember(openidMember);
	}

	@Override
	public void destroy() {

	}

}
