package com.ztravel.operator.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.WebUtils;

import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.common.util.CookieFactory;

/**
 * SID filter 生成一个新的会话id，并做成线程安全的存取
 * @author liuzhuo
 *
 */

public class SIDFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {


		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		Cookie cookie = WebUtils.getCookie(req, "SID");

		if(cookie == null) {
			cookie = CookieFactory.buildOperSID(UUID.randomUUID().toString()) ;
			res.addCookie(cookie);
		}

		OperatorSidHolder.remove();
		OperatorSidHolder.set(cookie.getValue());

		chain.doFilter(request, response);
		return ;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	public static String secLevelDomain(String url) {
		String host = url.replaceAll("https?:\\/{2,}", "").replaceAll("(:\\d*)?(\\/.*)?", "");
		if (host.indexOf('.') < 0 || host.indexOf('.') == host.lastIndexOf('.')) {
			return host;
		} else {
			//目前只考虑包含.cn顶级域名的情况，其他顶级域名暂未考虑，如有需要会做进一步改进（InternetDomainName）
			int idx = 0;
			if (host.endsWith(".com.cn")) {
				idx = host.lastIndexOf(".com.cn");
			}else {
		    	idx = host.lastIndexOf('.');
			}
	    	idx = host.lastIndexOf('.', --idx);
	    	return '.' + host.substring(++idx);
		}
	}



}
