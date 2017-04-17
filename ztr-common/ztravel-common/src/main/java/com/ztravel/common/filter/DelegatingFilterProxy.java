package com.ztravel.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.travelzen.framework.spring.web.context.SpringApplicationContext;

public class DelegatingFilterProxy implements Filter {

    private String targetFilterBean;
    private Filter proxy;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {
        proxy.doFilter(servletRequest, servletResponse, filterChain);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        this.targetFilterBean = config.getInitParameter("targetFilterBean");
    	this.proxy = (Filter)SpringApplicationContext.getApplicationContext().getBean(targetFilterBean) ;
        this.proxy.init(config);
    }

    @Override
    public void destroy() {}
}
