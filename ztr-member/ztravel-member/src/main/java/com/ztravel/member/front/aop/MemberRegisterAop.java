package com.ztravel.member.front.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.ResponseConstants;

/**
 * 用户注册aop
 * @author liuzhuo
 *
 */
//@Aspect
public class MemberRegisterAop {
	
	/**
	 * 注册成功后，用户ip，解析出经纬度和地址，异步完成
	 * @param jp
	 * @param request
	 * @param retVal
	 */
	@AfterReturning(value="execution(public com.ztravel.member.front.controller.MemberController.doRegister())",argNames="（mobile,request)", returning="retVal")
	public void registerEndPoint(JoinPoint jp,String mobile,HttpServletRequest request, AjaxResponse retVal) {
		
		if(retVal.getRes_code().equals(ResponseConstants.MEMB_REGISTER_SUCCESS_CODE));
			
		String reuqestIp = request.getRemoteHost();
		
		/**
		 *将ip 用户保存到mongo，解析的工作异步完成
		 */
		
	}
	

}
