package com.ztravel.member.front.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.ztravel.common.bean.AjaxResponse;


/**
 * <b> @EnableAspectJAutoProxy Enables support for handling components marked with AspectJ's @Aspect annotation, 
 * <b><p>similar to functionality found in Spring's <aop:aspectj-autoproxy> XML element.
 * <b><p>see @MemberFrontModuleConfig
 * @author liuzhuo
 *
 */

@Component
@Aspect
public class MemberLoginAop {
	
	
	@Before(value="@annotation(com.ztravel.member.front.aop.LoginPrintAnnotation) && args(account,password,verifyCode,rememberMe,request,response)")
	public void loginPrintAop(JoinPoint jp,String account, String password, String verifyCode, Boolean rememberMe, HttpServletRequest request, HttpServletResponse response) {
		
		
		//do you bussniess
		//get params from pointcut args expression  is not performance
		
		System.out.println("before login");
		
	}
	
	@AfterReturning(value="@annotation(com.ztravel.member.front.aop.LoginPrintAnnotation)", returning="retVal")
	public void logoutPrintAop(AjaxResponse retVal) {
		
		//do you bussniess
		System.out.println("after login");
		
	}


}
