package com.ztravel.paygate.server.alipay.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ztravel.paygate.server.alipay.AlipayServiceServer;

/**
 * 支付宝服务启动程序
 * 
 * @author dingguangxian
 * 
 */
public class AlipayServiceStartupServlet extends HttpServlet {
	private static final long serialVersionUID = -1422423508507396506L;

	private static final Logger logger = LoggerFactory.getLogger(AlipayServiceStartupServlet.class);

	private static AlipayServiceServer server;
	private static ApplicationContext applicationContext;

	public void init(ServletConfig config) throws ServletException {
		String contextConfigLocation = config.getInitParameter("contextConfigLocation");
		logger.info("using ContextConfig:{}", contextConfigLocation);
		applicationContext = new ClassPathXmlApplicationContext(contextConfigLocation);
		server = applicationContext.getBean(AlipayServiceServer.class);
		server.start();
	}

	public void destroy() {
		if (server != null) {
			server.stop();
		}
	}
}
