package com.ztravel.common.exception;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.travelzen.framework.logger.core.TZMarkers;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;

/**
 * 定制的spring mvc  异常处理,
 * 以下页面错误 不在处理范围以内{404}
 * 需要判断是前台异常 还是后台异常
 * @author liuzhuo
 *
 */

public class WxCustomerGlobalExceptionHandler implements HandlerExceptionResolver {
	
	private static final Logger LOG = RequestIdentityLogger.getLogger(WxCustomerGlobalExceptionHandler.class);
	
	/**
	 * 默认异常提示
	 */
	private static final String DEFALUT_ERR_MSG = "系统默认异常提示";
	
	

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		
		ModelAndView mv = new ModelAndView("/common/weixin/error/500");
		
		if(ex instanceof ZtrBizException) {
			mv.addObject("errMsg", ((ZtrBizException) ex).getRetMsg());
		}else {
			LOG.error(TZMarkers.p3, "全局异常捕获",ex);
			mv.addObject("errMsg", DEFALUT_ERR_MSG);
		}
		
		return mv;
	}
	
}
