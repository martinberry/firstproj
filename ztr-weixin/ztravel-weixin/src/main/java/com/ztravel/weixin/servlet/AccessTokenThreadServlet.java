package com.ztravel.weixin.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.util.WebEnv;

/**
 * 
 * @author wanhaofan
 *
 */
public class AccessTokenThreadServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8385209820038341992L;
	private static Logger logger = RequestIdentityLogger.getLogger(AccessTokenThreadServlet.class);
	
	
	public void init() throws ServletException { 
		boolean isDev = Boolean.valueOf(WebEnv.get("isDev","true")) ;
		if(!isDev){
			startAccessTokenThread() ;
		}
	}
	
	/**
	 * 启动AccessTokenThread
	 */
	public void startAccessTokenThread() {
		logger.info("start AccessTokenThread");
		Thread thread = new Thread(new AccessTokenThread(), "Thread-AccessToken");
		thread.start();
   }
}