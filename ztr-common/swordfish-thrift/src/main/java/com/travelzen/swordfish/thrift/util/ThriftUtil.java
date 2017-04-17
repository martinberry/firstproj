package com.travelzen.swordfish.thrift.util;

import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;

import com.incubate.framework.thrift.ThriftException;
import com.incubate.framework.thrift.ThriftRequest;
import com.travelzen.swordfish.thrift.constants.ThriftConstants;

public class ThriftUtil {

	public static String getQualifiedRequest(ThriftRequest request) {

		return request.getDeclaredInterface() + "." + request.getMethod() + "(" + request.getParameters() + ")";

	}
	
	/**
	 * 获取target
	 * 1 JDK 动态代理返回target
	 * 2 Cglib 代理 抛出异常
	 * 3 普通bean 直接返回
	 * @param source
	 * @return
	 * @throws Exception 
	 */
	public static Object getTargetClassBySource(Object source) throws Exception {
		
		if(AopUtils.isJdkDynamicProxy(source)) {
			return ((Advised)source).getTargetSource().getTarget();
		}
		if(AopUtils.isCglibProxy(source)) {
			ThriftException tfe = new ThriftException();
			tfe.setErrorCode(ThriftConstants.THRIFT_PROXY_ERR_CODE);
			tfe.setMessage(ThriftConstants.THRIFT_PROXY_ERR_MSG);
			throw tfe;
		}
		return source;
	}

}
