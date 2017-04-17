package com.travelzen.framework.logger.ri;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.travelzen.framework.core.json.JsonUtil;
import com.travelzen.framework.logger.core.ri.CallInfo;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class RequestIdentityInterceptor implements HandlerInterceptor {

    private static Random RDM = new Random();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RequestIdentityHolder.init();
        resetCallInfo(request, response, RequestIdentityHolder.get());
        response.addHeader("tops-request-identity", JsonUtil.toJson(RequestIdentityHolder.get(), false));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestIdentityHolder.remove();
    }

    protected CallInfo resetCallInfo(HttpServletRequest request, HttpServletResponse response, CallInfo callInfo) {
		return callInfo;
	}
}
