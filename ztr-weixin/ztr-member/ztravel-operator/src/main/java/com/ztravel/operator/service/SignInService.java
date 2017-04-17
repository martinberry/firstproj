package com.ztravel.operator.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.operator.po.LoginUser;

public interface SignInService {

	/**
	 * 验证验证码, 密码, 设置自动登录
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public AjaxResponse checkAndLogin(LoginUser user, HttpServletRequest request, HttpServletResponse response) throws Exception;

	/**
	 * 退出登录时删除登录信息
	 * @param username
	 * @param sessionid
	 * @param request
	 */
	public void removeAutoLogin(String username, String sessionid, HttpServletRequest request);

	/**
	 * 获得登录失败次数
	 * @param username
	 * @return
	 */
	public int getLoginFailureCount(String username);

	Boolean checkUsernamePassword(String username, String password, String sessionid, boolean isBefore);

}
