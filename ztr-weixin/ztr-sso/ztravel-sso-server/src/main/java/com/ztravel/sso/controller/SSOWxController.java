package com.ztravel.sso.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.core.util.StringUtil;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.ResponseConstants;
import com.ztravel.common.constants.WxMembConst;
import com.ztravel.common.entity.AjaxJsonpResponse;
import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.common.holder.TokenHolder;
import com.ztravel.common.security.SignUtil;
import com.ztravel.common.util.CookieFactory;
import com.ztravel.common.util.OpenIdUtil;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.common.util.TokenUtil;
import com.ztravel.member.po.WxUserEntity;
import com.ztravel.member.client.service.IMemberClientService;
import com.ztravel.reuse.member.validation.MemberLoginValidation;
import com.ztravel.reuse.member.validation.MemberRegisterValidation;
import com.ztravel.reuse.member.entity.MemberConstants;
import com.ztravel.sso.annotation.RegWhilteListFilter;
import com.ztravel.sso.po.OpenidMemberEntity;
import com.ztravel.sso.po.SSOBasicEntity;
import com.ztravel.sso.service.SSOService;
import com.ztravel.sso.service.OpenidMemberService;

/**
 *
 * @author wanhaofan
 *
 * 微信登录SSO入口
 */
@Controller
@RequestMapping("/sso/wx")
public class SSOWxController {

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(SSOWxController.class);

	@Resource
	private IMemberClientService memberClientService ;

	@Resource
	private SSOService ssoService ;

	@Resource
	OpenidMemberService openidMemberService;

	private static final RedisClient redisClient = RedisClient.getInstance();


	@RequestMapping(value = "/doRegister")
	@ResponseBody
	@RegWhilteListFilter
	public String doRegister(String callback,String mobile, String verifyCode, String password, HttpServletRequest request) {
		try{
			mobile = MemberRegisterValidation.validate(mobile, MemberRegisterValidation.MOBILE);
			MemberRegisterValidation.validateVerifyCode(verifyCode);
			MemberRegisterValidation.validatePassword(password);
		}catch(Exception e){
			return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(e.getMessage(), e.getMessage())).toString() ;
		}
		try{
			if(memberClientService.isMobileAlreadyExists(mobile)){
				return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_VERIFYMOBILE_ALREADYEXISTS_ERROR_CODE
						, ResponseConstants.MEMB_VERIFYMOBILE_ALREADYEXISTS_ERROR_MSG)).toString() ;
			}
			String serverVerfyCode = redisClient.get(String.format("%s:%s", MemberConstants.VERIFYCODE_REGISTER, mobile)) ;
			if(!verifyCode.equals(serverVerfyCode)){
				return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_VERIFYCODE_VERIFY_FAIL_CODE
						, ResponseConstants.MEMB_VERIFYCODE_VERIFY_FAIL_MSG)).toString() ;
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_VERIFYCODE_VERIFY_ERROR_CODE
					, ResponseConstants.MEMB_VERIFYCODE_VERIFY_ERROR_MSG)).toString() ;
		}

		try{
			ssoService.doRegister(mobile, password);
			return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_REGISTER_SUCCESS_CODE
					, ResponseConstants.MEMB_REGISTER_SUCCESS_MSG)).toString() ;
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_REGISTER_ERROR_CODE
					, ResponseConstants.MEMB_REGISTER_ERROR_MSG)).toString() ;
		}
	}

	@RequestMapping("/login")
	@ResponseBody
	public String login(String callback,String account, String password, String verifyCode, Boolean rememberMe, HttpServletRequest request, HttpServletResponse response) throws IOException {
		MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;
		int loginFailCount = memberSessionBean.getLoginFailCount() ;
		try {
			if(loginFailCount >= 3) {
				String serverVerifyCode = SSOUtil.getLoginVerifyCode() ;
				if(serverVerifyCode != null && !serverVerifyCode.equals(verifyCode)) {
					return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_LOGIN_VERIFYCODEERROR_CODE
							, ResponseConstants.MEMB_LOGIN_VERIFYCODEERROR_MSG)).toString() ;
				}
			}
			if (StringUtil.isTrimEmpty(account) || StringUtil.isTrimEmpty(password)) {
			    loginFailCount ++ ;
                memberSessionBean.setLoginFailCount(loginFailCount);
                SSOUtil.refreshMemberSessionBean(memberSessionBean);
			    return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.ACCOUNT_PASSWORD_IS_NULL_CODE
                        , String.valueOf(loginFailCount))).toString() ;
			}
			try{
				MemberRegisterValidation.validatePassword(password) ;
			}catch(Exception e){
				return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(e.getMessage()
						, e.getMessage())).toString() ;
			}
			if(MemberLoginValidation.validate(account)) {
				String signPassword = SignUtil.signPassword(password, SignUtil.FRONT_SIGN_KEY) ;
				SSOBasicEntity entity = ssoService.login(account, signPassword) ;
				if(entity.getIsLogin() && entity.getIsActive()) {
					try{
						ssoService.updateLastLoginDate(entity.getId());
						recordOpenidMember(entity);

						if(rememberMe){
							response.addCookie(ssoService.buildRememberMeCookie(account, signPassword));
						}
					}catch(Exception e){
						//保存最后登陆时间不影响正常流程
						LOGGER.error(e.getMessage(), e);
					}
					return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_LOGIN_SUCCESS_CODE
							, JSONObject.toJSONString(entity))).toString() ;
				}else if(entity.getIsActive() != null && !entity.getIsActive()){
					loginFailCount ++ ;
					memberSessionBean.setLoginFailCount(loginFailCount);
					SSOUtil.refreshMemberSessionBean(memberSessionBean);
					return AjaxJsonpResponse.getInstance(callback,
							AjaxResponse.instance(ResponseConstants.MEMB_NOT_ACTIVE_CODE, String.valueOf(loginFailCount))).toString() ;
				}else{
					loginFailCount ++ ;
					memberSessionBean.setLoginFailCount(loginFailCount);
					SSOUtil.refreshMemberSessionBean(memberSessionBean);
					return AjaxJsonpResponse.getInstance(callback,
							AjaxResponse.instance(ResponseConstants.MEMB_LOGIN_FAIL_CODE, String.valueOf(loginFailCount))).toString() ;
				}
			} else {
				loginFailCount ++ ;
				memberSessionBean.setLoginFailCount(loginFailCount);
				SSOUtil.refreshMemberSessionBean(memberSessionBean);
				return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_LOGIN_FAIL_CODE, String.valueOf(loginFailCount))).toString() ;
			}
		}catch (Exception e){
			return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_LOGIN_ERROR_CODE
					, ResponseConstants.MEMB_LOGIN_ERROR_MSG)).toString() ;
		}
	}

	private void recordOpenidMember (SSOBasicEntity entity) {
	    String openId = OpenIdUtil.getOpenId();
        if (openId != null) {
            OpenidMemberEntity openidMember = openidMemberService.selectOpenidMemberByOpenId(openId);
            if (openidMember == null) {
                openidMember = new OpenidMemberEntity();
                openidMember.setOpenId(openId);
                openidMember.setMemberId(entity.getId());
                openidMember.setLoginTime(new DateTime());
                openidMember.setToken(TokenHolder.get());
                openidMemberService.insertOpenidMember(openidMember);
            } else {
                openidMember.setMemberId(entity.getId());
                openidMember.setLoginTime(new DateTime());
                openidMember.setToken(TokenHolder.get());
                openidMemberService.updateOpenidMember(openidMember);
            }
        }
	}

	@RequestMapping("/logout")
    @ResponseBody
    public String logout(String callback, String account,HttpServletResponse response){
        try{
            String openId = OpenIdUtil.getOpenId();
            if (openId != null) {
                OpenidMemberEntity openidMember = openidMemberService.selectOpenidMemberByOpenId(openId);
                if (openidMember != null) {
                    openidMember = new OpenidMemberEntity();
                    openidMember.setOpenId(openId);
                    openidMember.setLoginTime(new DateTime());
                    openidMemberService.updateOpenidMemberToUnBind(openidMember);
                }
            }
            TokenUtil.deleteToken(SSOUtil.getMemberId());
            SSOUtil.deleteFromRedis(TokenHolder.get());
            Cookie accountCookie = CookieFactory.buildRememberMe(null, 0) ;
            response.addCookie(accountCookie);
            return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance("success", "success")).toString() ;
        }catch(Exception e){
            LOGGER.error(e.getMessage(), e);
            return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance("fail", "fail")).toString() ;
        }
    }

	@RequestMapping(value = "/resetPasswordByMobile")
	@ResponseBody
	public String resetPasswordByMobile(String callback, HttpServletRequest request, String password, String password2) {
		MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;
		String mobile = memberSessionBean.getMobile() ;
		try {
			MemberRegisterValidation.validatePassword(password);
			MemberRegisterValidation.validatePassword(password2);
		} catch (Exception e1) {
			return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_PASSWORD_ILLEGE_ERROR_CODE
					, ResponseConstants.MEMB_PASSWORD_ILLEGE_ERROR_MSG)).toString() ;
		}
		if(!password.equals(password2)){
			return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_PASSWORD_NOTSAME_CODE
					, ResponseConstants.MEMB_PASSWORD_NOTSAME_MSG)).toString() ;
		}
		String signPassword = SignUtil.signPassword(password, SignUtil.FRONT_SIGN_KEY) ;
		try{
			memberClientService.updateMemberPasswordByMobile(mobile, signPassword);
			memberSessionBean.setLoginFailCount(0);
			SSOUtil.refreshMemberSessionBean(memberSessionBean);
			return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_RESET_PASSWORD_BYMOBILE_SUCCESS_CODE
					, memberSessionBean.getUrl())).toString() ;
		}catch(Exception e){
			return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_RESET_PASSWORD_BYMOBILE_ERROR_CODE
					, ResponseConstants.MEMB_RESET_PASSWORD_BYMOBILE_ERROR_MSG)).toString() ;
		}
	}

	@RequestMapping("/resetPasswordByMail")
	@ResponseBody
	public String resetPasswordByMail(String callback, HttpServletRequest request, String password, String password2, String sid) {
		try {
			MemberRegisterValidation.validatePassword(password);
			MemberRegisterValidation.validatePassword(password2);
		} catch (Exception e1) {
			return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_PASSWORD_ILLEGE_ERROR_CODE
					, ResponseConstants.MEMB_PASSWORD_ILLEGE_ERROR_MSG)).toString() ;
		}
		if(!password.equals(password2)){
			return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_PASSWORD_NOTSAME_CODE
					, ResponseConstants.MEMB_PASSWORD_NOTSAME_MSG)).toString() ;
		}
		String signPassword = SignUtil.signPassword(password, SignUtil.FRONT_SIGN_KEY) ;
		String email = redisClient.get(sid);
		MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;
		try{
			memberClientService.updateMemberPasswordByEmail(email, signPassword);
			redisClient.delete(sid);
			memberSessionBean.setLoginFailCount(0);
			SSOUtil.refreshMemberSessionBean(memberSessionBean);
			return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_RESET_PASSWORD_BYEMAIL_SUCCESS_CODE
					, memberSessionBean.getUrl())).toString() ;
		}catch(Exception e){
			return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_RESET_PASSWORD_BYEMAIL_ERROR_CODE
					, ResponseConstants.MEMB_RESET_PASSWORD_BYMOBILE_ERROR_MSG)).toString() ;
		}
	}


	/**
	 * 未注册登陆的用户提交预约单时,调用此方法进行注册
	 * */
	@RequestMapping(value = "/doRegisterByGet")
	@ResponseBody
	@RegWhilteListFilter
	public String doRegisterByGet(String callback,String mobile, String email,String realName,String province,String city,String county,String area,String verifyCode, String password, HttpServletRequest request) {
		try{
			mobile = MemberRegisterValidation.validate(mobile, MemberRegisterValidation.MOBILE);
			MemberRegisterValidation.validateVerifyCode(verifyCode);
			MemberRegisterValidation.validatePassword(password);
		}catch(Exception e){
			return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(e.getMessage(), e.getMessage())).toString() ;
		}
		try{
			if(memberClientService.isMobileAlreadyExists(mobile)){
				return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_VERIFYMOBILE_ALREADYEXISTS_ERROR_CODE
						, ResponseConstants.MEMB_VERIFYMOBILE_ALREADYEXISTS_ERROR_MSG)).toString() ;
			}
			String serverVerfyCode = redisClient.get(String.format("%s:%s", MemberConstants.VERIFYCODE_REGISTER, mobile)) ;
			if(!verifyCode.equals(serverVerfyCode)){
				return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_VERIFYCODE_VERIFY_FAIL_CODE
						, ResponseConstants.MEMB_VERIFYCODE_VERIFY_FAIL_MSG)).toString() ;
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_VERIFYCODE_VERIFY_ERROR_CODE
					, ResponseConstants.MEMB_VERIFYCODE_VERIFY_ERROR_MSG)).toString() ;
		}

		try{
			ssoService.doRegisterByGet(mobile, password,email,realName,province,city,county,area);
			return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_REGISTER_SUCCESS_CODE
					, ResponseConstants.MEMB_REGISTER_SUCCESS_MSG)).toString() ;
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_REGISTER_ERROR_CODE
					, ResponseConstants.MEMB_REGISTER_ERROR_MSG)).toString() ;
		}
	}



    @RequestMapping(value="/setPasswordToWxUser")
    @ResponseBody
    public String setPasswordToWxUser(String callback,String newPwd){
        MemberSessionBean memberSessionBean;
        memberSessionBean = SSOUtil.getMemberSessionBean() ;
        if(!SSOUtil.isLogin()){
            return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(WxMembConst.MEMB_CHANGE_PASSWORD_OLDPWD_CHECK_FAILED_CODE
                    , WxMembConst.MEMB_CHANGE_PASSWORD_OLDPWD_CHECK_FAILED_MSG)).toString();
        }
        //校验新密码格式
        try {
            MemberRegisterValidation.validatePassword(newPwd);
        } catch (Exception e) {
            return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(WxMembConst.MEMB_CHANGE_PASSWORD_NEWPWD_INVALID_ERROR_CODE
                    , WxMembConst.MEMB_CHANGE_PASSWORD_NEWPWD_INVALID_ERROR_MSG)).toString();
        }

        try {
            memberClientService.updatePasswordById(memberSessionBean.getMemberId(), newPwd);
            return AjaxJsonpResponse.getInstance(callback, AjaxResponse.instance(WxMembConst.MEMB_CHANGE_PASSWORD_SUCCESS_CODE, WxMembConst.MEMB_CHANGE_PASSWORD_SUCCESS_MSG)).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return AjaxJsonpResponse.getInstance(callback, AjaxResponse.instance(WxMembConst.MEMB_CHANGE_PASSWORD_FAILED_CODE, WxMembConst.MEMB_CHANGE_PASSWORD_FAILED_MSG)).toString();
        }

    }

    @RequestMapping("/registerAndLoginByWx")
    @ResponseBody
    public String registerAndLogin(String callback, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String openId = OpenIdUtil.getOpenId();
        if (openId == null) {
            return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.GET_OPENID_FAIL_CODE
                    , ResponseConstants.GET_OPENID_FAIL_MSG)).toString() ;
        }
        WxUserEntity wxUserEntity = memberClientService.selectWxUserByOpenId(openId);
        if (wxUserEntity == null) {
            try {
                wxUserEntity = memberClientService.insertWxUserByOpenId(openId);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.INSERT_WX_USER_FAIL_CODE
                        , ResponseConstants.INSERT_WX_USER_FAIL_MSG)).toString() ;
            }
        }
        String nickName = wxUserEntity.getNickname();
        String headImgUrl = wxUserEntity.getHeadimgurl();
        try {
            SSOBasicEntity entity = ssoService.doRegisterAndLoginByWx(openId, nickName, headImgUrl);

            if(entity.getIsLogin() && entity.getIsActive()) {
                try{
                    ssoService.updateLastLoginDate(entity.getId());
                    recordOpenidMember(entity);
                }catch(Exception e){
                    //保存最后登陆时间不影响正常流程
                    LOGGER.error(e.getMessage(), e);
                }
                return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_LOGIN_SUCCESS_CODE
                        , JSONObject.toJSONString(entity))).toString() ;
            }else if(entity.getIsActive() != null && !entity.getIsActive()){
                return AjaxJsonpResponse.getInstance(callback,
                        AjaxResponse.instance(ResponseConstants.MEMB_NOT_ACTIVE_CODE, ResponseConstants.MEMB_NOT_ACTIVE_MSG)).toString() ;
            }else{
                return AjaxJsonpResponse.getInstance(callback,
                        AjaxResponse.instance(ResponseConstants.MEMB_LOGIN_FAIL_CODE, ResponseConstants.MEMB_LOGIN_FAIL_CODE)).toString() ;
            }
        }catch (Exception e){
            return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_LOGIN_ERROR_CODE
                    , ResponseConstants.MEMB_LOGIN_ERROR_MSG)).toString() ;
        }

    }

    @RequestMapping("/loginByWx")
    @ResponseBody
    public String loginByWxInfo(String callback, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String openId = OpenIdUtil.getOpenId();
        if (openId == null) {
            return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.GET_OPENID_FAIL_CODE
                    , ResponseConstants.GET_OPENID_FAIL_MSG)).toString() ;
        }
        try {
            SSOBasicEntity entity =ssoService.selectMemberByOpenId(openId);
            if (entity != null && entity.getId() != null) {
                entity = ssoService.doLoginByMemberId(entity.getId());
            } else {
                return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.REDIRECT_WX_BIND_PAGE_CODE
                        , ResponseConstants.REDIRECT_WX_BIND_PAGE_MSG)).toString() ;
            }

            if(entity.getIsLogin() && entity.getIsActive()) {
                try{
                    ssoService.updateLastLoginDate(entity.getId());
                    recordOpenidMember(entity);
                }catch(Exception e){
                    //保存最后登陆时间不影响正常流程
                    LOGGER.error(e.getMessage(), e);
                }
                return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_LOGIN_SUCCESS_CODE
                        , JSONObject.toJSONString(entity))).toString() ;
            }else if(entity.getIsActive() != null && !entity.getIsActive()){
                return AjaxJsonpResponse.getInstance(callback,
                        AjaxResponse.instance(ResponseConstants.MEMB_NOT_ACTIVE_CODE, ResponseConstants.MEMB_NOT_ACTIVE_MSG)).toString() ;
            }else{
                return AjaxJsonpResponse.getInstance(callback,
                        AjaxResponse.instance(ResponseConstants.MEMB_LOGIN_FAIL_CODE, ResponseConstants.MEMB_LOGIN_FAIL_CODE)).toString() ;
            }
        }catch (Exception e){
            return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_LOGIN_ERROR_CODE
                    , ResponseConstants.MEMB_LOGIN_ERROR_MSG)).toString() ;
        }

    }

    @RequestMapping("/bindAndLoginByWx")
    @ResponseBody
    public String bindAndLogin(String callback,String account, String password, String verifyCode, HttpServletRequest request, HttpServletResponse response) throws IOException {
        MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;
        int loginFailCount = memberSessionBean.getLoginFailCount() ;
        try {
            String openId = OpenIdUtil.getOpenId();
            if (openId == null) {
                return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.GET_OPENID_FAIL_CODE
                        , ResponseConstants.GET_OPENID_FAIL_MSG)).toString() ;
            }
            SSOBasicEntity ssoBasicEntity = ssoService.selectMemberByOpenId(openId);
            if (ssoBasicEntity != null && ssoBasicEntity.getMid() != null) {
                if (ssoBasicEntity.getMobile() != null) {
                    return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.OPENID_BINDED_MOBILE_CODE
                            , ResponseConstants.OPENID_BINDED_MOBILE_MSG)).toString() ;
                }
                return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.OPENID_REGISTERED_CODE
                        , ResponseConstants.OPENID_REGISTERED_MSG)).toString() ;
            }

            if(loginFailCount >= 3) {
                String serverVerifyCode = SSOUtil.getLoginVerifyCode() ;
                if(serverVerifyCode != null && !serverVerifyCode.equals(verifyCode)) {
                    return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_LOGIN_VERIFYCODEERROR_CODE
                            , ResponseConstants.MEMB_LOGIN_VERIFYCODEERROR_MSG)).toString() ;
                }
            }
            if (StringUtil.isTrimEmpty(account) || StringUtil.isTrimEmpty(password)) {
                loginFailCount ++ ;
                memberSessionBean.setLoginFailCount(loginFailCount);
                SSOUtil.refreshMemberSessionBean(memberSessionBean);
                return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.ACCOUNT_PASSWORD_IS_NULL_CODE
                        , String.valueOf(loginFailCount))).toString() ;
            }
            try{
                MemberRegisterValidation.validatePassword(password) ;
            }catch(Exception e){
                return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(e.getMessage()
                        , e.getMessage())).toString() ;
            }
            try{
                MemberRegisterValidation.validatePassword(password) ;
            }catch(Exception e){
                return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(e.getMessage()
                        , e.getMessage())).toString() ;
            }
            if(MemberLoginValidation.validate(account)) {
                String signPassword = SignUtil.signPassword(password, SignUtil.FRONT_SIGN_KEY) ;
                SSOBasicEntity entity = ssoService.selectMemberByAccountAndPassword(account, signPassword);
                if (entity == null) {
                    loginFailCount ++ ;
                    memberSessionBean.setLoginFailCount(loginFailCount);
                    SSOUtil.refreshMemberSessionBean(memberSessionBean);
                    return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_LOGIN_FAIL_CODE, String.valueOf(loginFailCount))).toString() ;
                } else if (!StringUtil.isEmpty(entity.getOpenId())) {
                    return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_BINDED_OPENID_CODE
                            , ResponseConstants.MEMB_BINDED_OPENID_MSG)).toString() ;
                } else {
                    entity = ssoService.login(account, signPassword) ;
                    if(entity.getIsLogin() && entity.getIsActive()) {
                        try{
                            ssoService.doBindOpenIdToMember(openId, entity.getId());
                            ssoService.updateLastLoginDate(entity.getId());
                            recordOpenidMember(entity);
                        }catch(Exception e){
                            //保存最后登陆时间不影响正常流程
                            LOGGER.error(e.getMessage(), e);
                        }
                        return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_LOGIN_SUCCESS_CODE
                                , JSONObject.toJSONString(entity))).toString() ;
                    }else if(entity.getIsActive() != null && !entity.getIsActive()){
                        loginFailCount ++ ;
                        memberSessionBean.setLoginFailCount(loginFailCount);
                        SSOUtil.refreshMemberSessionBean(memberSessionBean);
                        return AjaxJsonpResponse.getInstance(callback,
                                AjaxResponse.instance(ResponseConstants.MEMB_NOT_ACTIVE_CODE, String.valueOf(loginFailCount))).toString() ;
                    }else{
                        loginFailCount ++ ;
                        memberSessionBean.setLoginFailCount(loginFailCount);
                        SSOUtil.refreshMemberSessionBean(memberSessionBean);
                        return AjaxJsonpResponse.getInstance(callback,
                                AjaxResponse.instance(ResponseConstants.MEMB_LOGIN_FAIL_CODE, String.valueOf(loginFailCount))).toString() ;
                    }
                }
            } else {
                loginFailCount ++ ;
                memberSessionBean.setLoginFailCount(loginFailCount);
                SSOUtil.refreshMemberSessionBean(memberSessionBean);
                return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_LOGIN_FAIL_CODE, String.valueOf(loginFailCount))).toString() ;
            }
        }catch (Exception e){
            return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_LOGIN_ERROR_CODE
                    , ResponseConstants.MEMB_LOGIN_ERROR_MSG)).toString() ;
        }
    }

}
