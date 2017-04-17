package com.ztravel.operator.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.Const;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.operator.po.LoginUser;
import com.ztravel.operator.service.SignInService;
import com.ztravel.operator.util.util;

/**
 * 登录
 * @author tengmeilin
 *
 */

@Controller
@RequestMapping("/user")
public class SignInController {

	private static Logger logger = RequestIdentityLogger.getLogger(SignInController.class);

	@Resource
	private SignInService signInServiceImpl;

	@RequestMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("operator/userLogin/login");
	}

	@RequestMapping("/main")
	public ModelAndView main() {
		return new ModelAndView("operator/userLogin/main");
	}

	@RequestMapping(value = "/signIn", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse signIn(@RequestBody LoginUser user, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String username = StringUtils.trim(user.getUserName());
		String password = StringUtils.trim(user.getPassword());

		try {

			if (!util.validateUsername(username) || !util.validatePassword(password)) {
				return AjaxResponse.instance(Const.FO_OPER_CODE_1001, Const.FO_OPER_REASON_1001);
			}

			return signInServiceImpl.checkAndLogin(user, request, response);

        }catch (ZtrBizException ze) {
            logger.error("no captcha generated", ze);
            return AjaxResponse.instance(Const.FO_OPER_CODE_1002, Const.FO_OPER_REASON_1002);
        }catch (Exception e) {
            logger.error("Failed to login !", e);
            return AjaxResponse.instance(Const.FO_OPER_CODE_1004, Const.FO_OPER_REASON_1004);
        }

	}

	@RequestMapping(value = "/getLoginFailureCount", method = RequestMethod.POST)
	@ResponseBody
	public String getLoginFailureCount(@RequestParam("userName") String userName, HttpServletRequest request) {

		int count = 0;

		try {

			count = signInServiceImpl.getLoginFailureCount(userName);

        } catch (Exception e) {
        	//不影响业务, 不抛出异常
            logger.error("Failed to get login failure count !", e);
        }

		return Integer.toString(count);

	}

	@RequestMapping("/signOut")
	public ModelAndView signOut(@RequestParam String userName, HttpServletRequest request) throws Exception {

		String sessionid = OperatorSidHolder.get();

		try {

			signInServiceImpl.removeAutoLogin(userName, sessionid, request);

        } catch (Exception e) {
        	//不影响业务, 不抛出异常
            logger.error("Failed to sign out !", e);
        }

		return new ModelAndView("operator/userLogin/login");

	}

}
