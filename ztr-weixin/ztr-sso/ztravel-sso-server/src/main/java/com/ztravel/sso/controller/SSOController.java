package com.ztravel.sso.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.travelzen.framework.core.util.StringUtil;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.ResponseConstants;
import com.ztravel.common.entity.AjaxJsonpResponse;
import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.common.enums.NoticeType;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.holder.TokenHolder;
import com.ztravel.common.mail.MailEntity;
import com.ztravel.common.mail.MailEnums;
import com.ztravel.common.security.RandomVerificationUtil;
import com.ztravel.common.security.SignUtil;
import com.ztravel.common.util.CookieFactory;
import com.ztravel.common.util.IpUtils;
import com.ztravel.common.util.MailSender;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.common.util.SystemNoticeContentUtil;
import com.ztravel.common.util.TokenUtil;
import com.ztravel.common.util.WebEnv;
import com.ztravel.member.client.service.FrontCommonService;
import com.ztravel.member.client.service.IBalanceDetailClientService;
import com.ztravel.member.client.service.ICouponClientService;
import com.ztravel.member.client.service.IMemberClientService;
import com.ztravel.member.client.service.ISystemNoticeClientService;
import com.ztravel.reuse.member.validation.MemberLoginValidation;
import com.ztravel.reuse.member.validation.MemberRegisterValidation;
import com.ztravel.reuse.member.entity.MemberConstants;
import com.ztravel.sso.annotation.RegWhilteListFilter;
import com.ztravel.sso.po.SSOBasicEntity;
import com.ztravel.sso.service.SSOService;


@Controller
@RequestMapping("/sso")
public class SSOController {


	private static final Logger LOGGER = RequestIdentityLogger.getLogger(SSOController.class);

	@Resource
	private SSOService ssoService ;

	@Resource
	private IMemberClientService memberClientService ;

	@Resource
	private FrontCommonService frontCommonService;

	@Resource
	private ICouponClientService couponClientService ;

	@Resource
	private IBalanceDetailClientService balanceDetailClientService ;

	@Resource
	private ISystemNoticeClientService systemNoticeClientServiceImpl;

	private static final RedisClient redisClient = RedisClient.getInstance();

	@Resource
	Producer captchaProducer;

	@RequestMapping("/toLogin")
	public String toLogin(){
		return "common/sso/login" ;
	}

	public static void main (String[] args) {
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Long a = 1429529631*1000L;
	    Date date = new Date(a);
//        Date date = new Date();
	    System.out.println(sdf.format(date));
        Date newDate = new Date();
        newDate.getTime();
        System.out.println(newDate.getTime());
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
						if(rememberMe){
							response.addCookie(ssoService.buildRememberMeCookie(account, signPassword));
						}
					}catch(Exception e){
						//保存最后登陆时间不影响正常流程
						LOGGER.error(e.getMessage(), e);
					}

			        String entityJson = JSONObject.toJSONString(entity);
			        JSONObject json = JSONObject.parseObject(entityJson);
			        json.put("wxLogined", frontCommonService.isWxLogined(entity.getId()));
					return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_LOGIN_SUCCESS_CODE
							, json.toJSONString())).toString() ;
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

	@RequestMapping("/logout")
	@ResponseBody
	public String logout(String callback, String account,HttpServletResponse response){
		try{
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

	@RequestMapping("/register")
	public String register(HttpServletRequest request, String url,Model model) {
		if(url != null){
			MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;
			memberSessionBean.setUrl(url);
			SSOUtil.refreshMemberSessionBean(memberSessionBean);
		}
		String ticket = frontCommonService.getTicket();
		model.addAttribute("ticket", ticket);
		return "member/front/register" ;
	}

	@RequestMapping("/findPassword")
	public String findPassword(HttpServletRequest request, String url) {
		if(url != null){
			MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;
			memberSessionBean.setUrl(url);
			SSOUtil.refreshMemberSessionBean(memberSessionBean);
		}
		return "member/front/findpassword" ;
	}

	@RequestMapping("/findPasswordNewPasswordEmail")
	public ModelAndView findPasswordNewPassword(String sid, Model model) {
		String email = redisClient.get(sid) ;
		if(email != null){
			model.addAttribute("sid", sid) ;
			return new ModelAndView("member/front/findpassword_newpsw_email") ;
		}else{
			model.addAttribute("errorHint", "链接已失效，请重新获取") ;
			return new ModelAndView("member/front/findpassword_email") ;
		}

	}

	@RequestMapping("/findPasswordNewPassword")
	public String findPasswordNewPassword() {
		return "member/front/findpassword_newpsw" ;
	}

	@RequestMapping("/findPasswordEmailSuccess")
	public String findPasswordEmailSuccess() {
		return "member/front/findpassword_email_success" ;
	}

	@RequestMapping("/resetPassword")
	@ResponseBody
	public AjaxResponse resetPassword(HttpServletRequest request, String password, String password2) {
		MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;
		String mobile = memberSessionBean.getMobile() ;
		try {
			MemberRegisterValidation.validatePassword(password);
			MemberRegisterValidation.validatePassword(password2);
		} catch (Exception e1) {
			return AjaxResponse.instance(ResponseConstants.MEMB_PASSWORD_ILLEGE_ERROR_CODE
					, ResponseConstants.MEMB_PASSWORD_ILLEGE_ERROR_MSG) ;
		}
		if(!password.equals(password2)){
			return AjaxResponse.instance(ResponseConstants.MEMB_PASSWORD_NOTSAME_CODE
					, ResponseConstants.MEMB_PASSWORD_NOTSAME_MSG) ;
		}
		String signPassword = SignUtil.signPassword(password, SignUtil.FRONT_SIGN_KEY) ;
		try{
			memberClientService.updateMemberPasswordByMobile(mobile, signPassword);
			memberSessionBean.setLoginFailCount(0);
			SSOUtil.refreshMemberSessionBean(memberSessionBean);
			return AjaxResponse.instance(ResponseConstants.MEMB_RESET_PASSWORD_BYMOBILE_SUCCESS_CODE
					, memberSessionBean.getUrl()) ;
		}catch(Exception e){
			return AjaxResponse.instance(ResponseConstants.MEMB_RESET_PASSWORD_BYMOBILE_ERROR_CODE
					, ResponseConstants.MEMB_RESET_PASSWORD_BYMOBILE_ERROR_MSG) ;
		}
	}

	@RequestMapping("/resetPasswordByEmail")
	@ResponseBody
	public AjaxResponse resetPasswordByEmail(HttpServletRequest request, String password, String password2, String sid) {
		try {
			MemberRegisterValidation.validatePassword(password);
			MemberRegisterValidation.validatePassword(password2);
		} catch (Exception e1) {
			return AjaxResponse.instance(ResponseConstants.MEMB_PASSWORD_ILLEGE_ERROR_CODE
					, ResponseConstants.MEMB_PASSWORD_ILLEGE_ERROR_MSG) ;
		}
		if(!password.equals(password2)){
			return AjaxResponse.instance(ResponseConstants.MEMB_PASSWORD_NOTSAME_CODE
					, ResponseConstants.MEMB_PASSWORD_NOTSAME_MSG) ;
		}
		String signPassword = SignUtil.signPassword(password, SignUtil.FRONT_SIGN_KEY) ;
		String email = redisClient.get(sid);
		MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;
		try{
			memberClientService.updateMemberPasswordByEmail(email, signPassword);
			redisClient.delete(sid);
			memberSessionBean.setLoginFailCount(0);
			SSOUtil.refreshMemberSessionBean(memberSessionBean);
			return AjaxResponse.instance(ResponseConstants.MEMB_RESET_PASSWORD_BYEMAIL_SUCCESS_CODE
					, memberSessionBean.getUrl()) ;
		}catch(Exception e){
			return AjaxResponse.instance(ResponseConstants.MEMB_RESET_PASSWORD_BYEMAIL_ERROR_CODE
					, ResponseConstants.MEMB_RESET_PASSWORD_BYMOBILE_ERROR_MSG) ;
		}
	}

	@RequestMapping("/findPasswordByMobile")
	public String findPasswordByMobile() {
		return "member/front/findpassword_mobile" ;
	}

	@RequestMapping("/findPasswordByEmail")
	public String findPasswordByEmail() {
		return "member/front/findpassword_email" ;
	}

	@RequestMapping("/captcha-image/{version}")
    public void captchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//失败3次,需要验证码
		response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg
        response.setContentType("image/jpeg");
        // create the text for the image
        String capText = captchaProducer.createText();
        // store the text in the redis
        redisClient.set(Constants.KAPTCHA_SESSION_KEY + ":" + TokenHolder.get(), capText, 10 * 60);
        // create the image with the text
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        // write the data out
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
	}

	@RequestMapping("/skipImprovePersonalData")
	@ResponseBody
	public AjaxResponse skipImprovePersonalData(HttpServletRequest request, String nickName, String imageId) throws Exception {
		MemberSessionBean memberSessionBean = memberClientService.getMemberFromRedisBySID();
		nickName = memberSessionBean.getNickName() == null ? nickName : memberSessionBean.getNickName() ;
		imageId = memberSessionBean.getImageId() == null ? imageId : memberSessionBean.getImageId() ;
		try{
			nickName = MemberRegisterValidation.validate(nickName, MemberRegisterValidation.NICKNAME);
		}catch(Exception e){
			return new AjaxResponse(e.getMessage(), e.getMessage()) ;//EF_MEMB_1019
		}
		try{
			memberClientService.saveMemberImproveData(memberSessionBean.getMemberId(), nickName, null, null, imageId);
			memberSessionBean.setImageId(imageId);
			memberSessionBean.setNickName(nickName);
			SSOUtil.refreshMemberSessionBean(memberSessionBean);
			return new AjaxResponse(ResponseConstants.MEMB_SKIP_IMPROVE_PERSONAL_DATA_SUCCESS_CODE
					, memberSessionBean.getUrl()) ;//SF_MEMB_1009
		}catch(Exception e){
			return new AjaxResponse(ResponseConstants.MEMB_SKIP_IMPROVE_PERSONAL_DATA_ERROR_CODE
					, ResponseConstants.MEMB_SKIP_IMPROVE_PERSONAL_DATA_ERROR_MSG) ;//EF_MEMB_1035
		}
	}

	@RequestMapping("/findPasswordVerifyMobileCode")
	@ResponseBody
	public AjaxResponse findPasswordVerifyMobileCode(String mobile, String verifyCode, HttpServletRequest request) throws Exception {
		try{
			mobile = MemberRegisterValidation.validate(mobile, MemberRegisterValidation.MOBILE);
		}catch(Exception e){
			return AjaxResponse.instance(e.getMessage(), e.getMessage()) ;
		}
		String serverVerfyCode = redisClient.get(String.format("%s:%s", MemberConstants.VERIFYCODE_FINDPASSWORD, mobile)) ;

		if(!verifyCode.equals(serverVerfyCode)){
			return AjaxResponse.instance(ResponseConstants.MEMB_VERIFYCODE_VERIFY_FAIL_CODE
					, ResponseConstants.MEMB_VERIFYCODE_VERIFY_FAIL_MSG) ;
		}else{
			redisClient.delete(String.format("%s:%s", MemberConstants.VERIFYCODE_FINDPASSWORD, mobile));
			MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;
			memberSessionBean.setMobile(mobile);
			SSOUtil.refreshMemberSessionBean(memberSessionBean);
			return AjaxResponse.instance(ResponseConstants.MEMB_FINDPASSWORD_BYMOBILE_SUCCESS_CODE
					, ResponseConstants.MEMB_FINDPASSWORD_BYMOBILE_SUCCESS_MSG) ;
		}
	}

	@RequestMapping(value = "/doRegister", method = RequestMethod.POST)
	@ResponseBody
	@RegWhilteListFilter
	public AjaxResponse doRegister(String mobile, String verifyCode, String password, HttpServletRequest request) {
		try{
			mobile = MemberRegisterValidation.validate(mobile, MemberRegisterValidation.MOBILE);
			MemberRegisterValidation.validateVerifyCode(verifyCode);
			MemberRegisterValidation.validatePassword(password);
		}catch(Exception e){
			return AjaxResponse.instance(e.getMessage(), e.getMessage()) ;
		}
		try{
			if(memberClientService.isMobileAlreadyExists(mobile)){
				return AjaxResponse.instance(ResponseConstants.MEMB_VERIFYMOBILE_ALREADYEXISTS_ERROR_CODE
						, ResponseConstants.MEMB_VERIFYMOBILE_ALREADYEXISTS_ERROR_MSG) ;
			}
			String serverVerfyCode = redisClient.get(String.format("%s:%s", MemberConstants.VERIFYCODE_REGISTER, mobile)) ;
			if(!verifyCode.equals(serverVerfyCode)){
				return AjaxResponse.instance(ResponseConstants.MEMB_VERIFYCODE_VERIFY_FAIL_CODE
						, ResponseConstants.MEMB_VERIFYCODE_VERIFY_FAIL_MSG) ;
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return AjaxResponse.instance(ResponseConstants.MEMB_VERIFYCODE_VERIFY_ERROR_CODE
					, ResponseConstants.MEMB_VERIFYCODE_VERIFY_ERROR_MSG) ;
		}

		try{
			ssoService.doRegister(mobile, password);
			return AjaxResponse.instance(ResponseConstants.MEMB_REGISTER_SUCCESS_CODE
					, ResponseConstants.MEMB_REGISTER_SUCCESS_MSG) ;
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return AjaxResponse.instance(ResponseConstants.MEMB_REGISTER_ERROR_CODE
					, ResponseConstants.MEMB_REGISTER_ERROR_MSG) ;
		}
	}

	@RequestMapping(value = "/checkVerifyCode", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse checkVerifyCode(String mobile, String verifyCode) {
		try{
			MemberRegisterValidation.validateVerifyCode(verifyCode);
		}catch(Exception e){
			return AjaxResponse.instance(e.getMessage(), e.getMessage()) ;
		}

		try{
			String serverVerfyCode = redisClient.get(String.format("%s:%s", MemberConstants.VERIFYCODE_REGISTER, mobile)) ;
			if(!verifyCode.equals(serverVerfyCode)){
				return AjaxResponse.instance(ResponseConstants.MEMB_VERIFYCODE_VERIFY_FAIL_CODE
						, ResponseConstants.MEMB_VERIFYCODE_VERIFY_FAIL_MSG) ;
			}
			return AjaxResponse.instance("success","success") ;
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return AjaxResponse.instance(ResponseConstants.MEMB_VERIFYCODE_VERIFY_ERROR_CODE
					, ResponseConstants.MEMB_VERIFYCODE_VERIFY_ERROR_MSG) ;
		}
	}

	@RequestMapping(value = "/checkVerifyCodeByGet")
	@ResponseBody
	public String checkVerifyCodeByGet(String callback,String mobile, String verifyCode) {
		try{
			MemberRegisterValidation.validateVerifyCode(verifyCode);
		}catch(Exception e){
			return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(e.getMessage(), e.getMessage())).toString() ;
		}

		try{
			String serverVerfyCode = redisClient.get(String.format("%s:%s", MemberConstants.VERIFYCODE_REGISTER, mobile)) ;
			if(!verifyCode.equals(serverVerfyCode)){
				return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_VERIFYCODE_VERIFY_FAIL_CODE
						, ResponseConstants.MEMB_VERIFYCODE_VERIFY_FAIL_MSG)).toString() ;
			}
			return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance("success","success")).toString() ;
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_VERIFYCODE_VERIFY_ERROR_CODE
					, ResponseConstants.MEMB_VERIFYCODE_VERIFY_ERROR_MSG)).toString() ;
		}
	}



	@RequestMapping(value = "/sendRegisterSms", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse sendRegisterSms(String mobile, String verifyCode, HttpServletRequest request) {
	    LOGGER.debug("发出请求的IP地址为:::{}", IpUtils.getIpFromRequest(request));
		try{
			mobile = MemberRegisterValidation.validate(mobile, MemberRegisterValidation.MOBILE);
		}catch(Exception e){
			return AjaxResponse.instance(e.getMessage(), e.getMessage()) ;
		}

		try{
			if(memberClientService.isMobileAlreadyExists(mobile)){
				return AjaxResponse.instance(ResponseConstants.MEMB_VERIFYMOBILE_ALREADYEXISTS_ERROR_CODE
						, ResponseConstants.MEMB_VERIFYMOBILE_ALREADYEXISTS_ERROR_MSG) ;
			}
            String serverVerifyCode = SSOUtil.getLoginVerifyCode() ;
            if (serverVerifyCode == null || !serverVerifyCode.equals(verifyCode)) {
                return AjaxResponse.instance(ResponseConstants.MEMB_LOGIN_VERIFYCODEERROR_CODE
                        , ResponseConstants.MEMB_LOGIN_VERIFYCODEERROR_MSG) ;
            }

			String randomVerifyCode = RandomVerificationUtil.getVerificationNumCode() ;
			redisClient.set(String.format("%s:%s", MemberConstants.VERIFYCODE_REGISTER, mobile), randomVerifyCode, 10 * 60);
			memberClientService.sendRegisterSms(mobile, randomVerifyCode) ;

			return AjaxResponse.instance(ResponseConstants.MEMB_VERIFYCODE_SENDSMS_SUCCESS_CODE
					, ResponseConstants.MEMB_VERIFYCODE_SENDSMS_SUCCESS_MSG) ;
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);

			return AjaxResponse.instance(ResponseConstants.MEMB_VERIFYCODE_SENDSMS_ERROR_CODE
					, ResponseConstants.MEMB_VERIFYCODE_SENDSMS_ERROR_MSG) ;
		}
	}

	@RequestMapping(value = "/sendRegisterSmsByGet")
	@ResponseBody
	public String sendRegisterSmsByGet(String callback,String mobile, HttpServletRequest request) {
		try{
			mobile = MemberRegisterValidation.validate(mobile, MemberRegisterValidation.MOBILE);
		}catch(Exception e){
			return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(e.getMessage(), e.getMessage())).toString() ;
		}
		try{
			if(memberClientService.isMobileAlreadyExists(mobile)){
				return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_VERIFYMOBILE_ALREADYEXISTS_ERROR_CODE
						, ResponseConstants.MEMB_VERIFYMOBILE_ALREADYEXISTS_ERROR_MSG)).toString() ;
			}
			String randomVerifyCode = RandomVerificationUtil.getVerificationNumCode() ;
			redisClient.set(String.format("%s:%s", MemberConstants.VERIFYCODE_REGISTER, mobile), randomVerifyCode, 10 * 60);
			memberClientService.sendRegisterSms(mobile, randomVerifyCode) ;
			return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_VERIFYCODE_SENDSMS_SUCCESS_CODE
					, ResponseConstants.MEMB_VERIFYCODE_SENDSMS_SUCCESS_MSG)).toString() ;
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_VERIFYCODE_SENDSMS_ERROR_CODE
					, ResponseConstants.MEMB_VERIFYCODE_SENDSMS_ERROR_MSG)).toString() ;
		}
	}

	@RequestMapping(value = "/sendFindPasswordSms", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse sendFindPasswordSms(String mobile, HttpServletRequest request) {
		try{
			mobile = MemberRegisterValidation.validate(mobile, MemberRegisterValidation.MOBILE);
		}catch(Exception e){
			return AjaxResponse.instance(e.getMessage(), e.getMessage()) ;
		}
		try{
			if(memberClientService.isMobileAlreadyExists(mobile)){
				String randomVerifyCode = RandomVerificationUtil.getVerificationNumCode() ;
				redisClient.set(String.format("%s:%s", MemberConstants.VERIFYCODE_FINDPASSWORD, mobile), randomVerifyCode, 10 * 60);
				memberClientService.sendFindPasswordSms(mobile, randomVerifyCode) ;
				return AjaxResponse.instance(ResponseConstants.MEMB_VERIFYCODE_SENDSMS_SUCCESS_CODE
						, ResponseConstants.MEMB_VERIFYCODE_SENDSMS_SUCCESS_MSG) ;
			}else{
				return AjaxResponse.instance(ResponseConstants.MEMB_MOBILE_NOT_FOUND_ERROR_CODE
						, ResponseConstants.MEMB_MOBILE_NOT_FOUND_ERROR_MSG) ;
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return AjaxResponse.instance(ResponseConstants.MEMB_VERIFYCODE_SENDSMS_ERROR_CODE
					, ResponseConstants.MEMB_VERIFYCODE_SENDSMS_ERROR_MSG) ;
		}
	}

	@RequestMapping(value = "/isMobileAlreadyExists", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse isMobileAlreadyExists(String mobile){
		try{
			mobile = MemberRegisterValidation.validate(mobile, MemberRegisterValidation.MOBILE);
		}catch(Exception e){
			return AjaxResponse.instance(e.getMessage(), e.getMessage()) ;
		}
		try{
			if(memberClientService.isMobileAlreadyExists(mobile)){
				return AjaxResponse.instance(ResponseConstants.MEMB_VERIFYMOBILE_ALREADYEXISTS_ERROR_CODE
						, ResponseConstants.MEMB_VERIFYMOBILE_ALREADYEXISTS_ERROR_MSG) ;
			}else{
				return AjaxResponse.instance(ResponseConstants.MEMB_VERIFYMOBILE_SUCCESS_CODE
						, ResponseConstants.MEMB_VERIFYMOBILE_SUCCESS_MSG) ;
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return AjaxResponse.instance(ResponseConstants.MEMB_VERIFYMOBILE_PROGRAM_ERROR_CODE
					, ResponseConstants.MEMB_VERIFYMOBILE_PROGRAM_ERROR_MSG) ;
		}
	}

	@RequestMapping(value = "/isMobileAlreadyExistsByGet")
	@ResponseBody
	public String isMobileAlreadyExistsByGet(String callback,String mobile){
		try{
			mobile = MemberRegisterValidation.validate(mobile, MemberRegisterValidation.MOBILE);
		}catch(Exception e){
			return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(e.getMessage(), e.getMessage())).toString() ;
		}
		try{
			if(memberClientService.isMobileAlreadyExists(mobile)){
				return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_VERIFYMOBILE_ALREADYEXISTS_ERROR_CODE
						, ResponseConstants.MEMB_VERIFYMOBILE_ALREADYEXISTS_ERROR_MSG)).toString() ;
			}else{
				return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_VERIFYMOBILE_SUCCESS_CODE
						, ResponseConstants.MEMB_VERIFYMOBILE_SUCCESS_MSG)).toString() ;
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_VERIFYMOBILE_PROGRAM_ERROR_CODE
					, ResponseConstants.MEMB_VERIFYMOBILE_PROGRAM_ERROR_MSG)).toString() ;
		}
	}

	@RequestMapping(value = "/selectByMobile", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse selectByMobile(String mobile){
		try{
			try{
				mobile = MemberRegisterValidation.validate(mobile, MemberRegisterValidation.MOBILE);
			}catch(Exception e){
				return AjaxResponse.instance(e.getMessage(), e.getMessage()) ;
			}
			if(mobile.equals(SSOUtil.getMemberSessionBean().getMobile())){
				return AjaxResponse.instance("fail","不能推荐本人") ;
			}
			String member = memberClientService.selectMemberByMobile(mobile) ;
			if(member != null){
				return AjaxResponse.instance(ResponseConstants.MEMB_VERIFYMOBILE_ALREADYEXISTS_ERROR_CODE
						, member) ;
			}else{
				return AjaxResponse.instance(ResponseConstants.MEMB_VERIFYMOBILE_SUCCESS_CODE
						, ResponseConstants.MEMB_VERIFYMOBILE_SUCCESS_MSG) ;
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return AjaxResponse.instance(ResponseConstants.MEMB_VERIFYMOBILE_PROGRAM_ERROR_CODE
					, ResponseConstants.MEMB_VERIFYMOBILE_PROGRAM_ERROR_MSG) ;
		}
	}

	@RequestMapping(value = "/isEmailAlreadyExists", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse isEmailAlreadyExists(String email){
		try{
			email = MemberRegisterValidation.validate(email, MemberRegisterValidation.EMAIL);
		}catch(Exception e){
			return AjaxResponse.instance(e.getMessage(), e.getMessage()) ;
		}
		try{
			if(memberClientService.isEmailAlreadyExists(email)){
				return AjaxResponse.instance(ResponseConstants.MEMB_EMAIL_ALREADYEXISTS_CODE
						, ResponseConstants.MEMB_EMAIL_ALREADYEXISTS_MSG) ;
			}else{
				return AjaxResponse.instance(ResponseConstants.MEMB_EMAIL_SUCCESS_CODE
						, ResponseConstants.MEMB_EMAIL_SUCCESS_MSG) ;
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return AjaxResponse.instance(ResponseConstants.MEMB_EMAIL_PROGRAM_ERROR_CODE
					, ResponseConstants.MEMB_EMAIL_PROGRAM_ERROR_CODE) ;
		}
	}

	@RequestMapping(value = "/findPasswordEmail", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse findPasswordEmail(String email, HttpServletRequest request){
		try{
			email = MemberRegisterValidation.validate(email, MemberRegisterValidation.EMAIL);
		}catch(Exception e){
			return AjaxResponse.instance(e.getMessage(), e.getMessage()) ;
		}
		try{
			if(memberClientService.isEmailAlreadyExists(email)){
				String sid = SignUtil.signSid(TokenHolder.get(), System.currentTimeMillis() + 60 * 60 * 1000, SignUtil.FRONT_SIGN_KEY) ;
				if(redisClient.exists(sid)){
					return AjaxResponse.instance(ResponseConstants.MEMB_MAIL_ALREADY_SEND_CODE
							, ResponseConstants.MEMB_MAIL_ALREADY_SEND_MSG) ;
				}
				redisClient.set(sid, email, 60 * 60);
				String url = WebEnv.get("server.host.ssoServer", "") + "/sso/findPasswordNewPasswordEmail?sid=" + sid ;
				Map<String, String> params = new HashMap<String, String>() ;
				params.put("URL", url) ;
				params.put("DATE", DateTime.now().toString("yyyy-MM-dd")) ;
				params.put("HOMEPAGE", WebEnv.get("server.path.memberServer", "")) ;
				params.put("MEMBERINFO", WebEnv.get("server.path.memberServer", "") + "/member/info") ;
				MailEntity entity = new MailEntity(email,null,MailEnums.ContentType.HTML,params,MailEnums.BizType.FINDPASSWORD) ;
				MailSender.send(entity);
				return AjaxResponse.instance(ResponseConstants.MEMB_MAIL_SEND_SUCCESS_CODE
						, ResponseConstants.MEMB_MAIL_SEND_SUCCESS_MSG) ;
			}else{
				return AjaxResponse.instance(ResponseConstants.MEMB_MAIL_NOT_FOUND_ERROR_CODE
						, ResponseConstants.MEMB_MAIL_NOT_FOUND_ERROR_MSG) ;
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return AjaxResponse.instance(ResponseConstants.MEMB_EMAIL_PROGRAM_ERROR_CODE
					, ResponseConstants.MEMB_EMAIL_PROGRAM_ERROR_CODE) ;
		}
	}

	@RequestMapping(value = "/improvePersonalData")
	public ModelAndView improvePersonalData(Model model, HttpServletRequest request) throws Exception{
		MemberSessionBean memberSessionBean = memberClientService.getMemberFromRedisBySID() ;
		String imageId = memberSessionBean.getImageId() ;
		String nickName = memberSessionBean.getNickName() ;
		try{
			if(imageId != null && imageId.length() > 0){
				model.addAttribute("randomDefaultImageId", imageId) ;
			}else {
				model.addAttribute("randomDefaultImageId", memberClientService.getRandomDefaultImageId()) ;
			}
			if(nickName != null && nickName.length() > 0){
				model.addAttribute("randomNickName", nickName) ;
			}else {
				model.addAttribute("randomNickName", memberClientService.getRandomNickName()) ;
			}
		}catch(Exception e){
			//捕获异常,不影响正常流程
			LOGGER.error(e.getMessage(), e);
		}
		return new ModelAndView("member/front/improve_personal_data") ;
	}

	@RequestMapping(value = "/refreshNickName")
	@ResponseBody
	public AjaxResponse refreshNickName(){
		String nickName = "" ;
		try{
			nickName = memberClientService.getRandomNickName() ;
		}catch(Exception e){
			//捕获异常,不影响正常流程
			LOGGER.error(e.getMessage(), e);
		}
		return AjaxResponse.instance(nickName, nickName) ;
	}

	@RequestMapping(value = "/refreshImage")
	@ResponseBody
	public List<String> refreshImage(){
		List<String> images = new LinkedList<String>() ;
		try{
			images = memberClientService.getHeadImages(10) ;
		}catch(Exception e){
			//捕获异常,不影响正常流程
			LOGGER.error(e.getMessage(), e);
		}
		return images ;
	}

	@RequestMapping(value = "/chooseHead")
	public String chooseHead(){
		return "member/front/choose_head" ;
	}

	@RequestMapping(value = "/saveHead", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse saveHeadUpload(
	        @RequestParam(value = "imageInputer", required = true) MultipartFile file,
	        HttpServletRequest request, Model model) throws Exception {

		MemberSessionBean memberSessionBean = memberClientService.getMemberFromRedisBySID() ;
		// 判断图片大小是否大于2M
	    if (file.getSize() > 2 * 1024 * 1024 || file.getSize() == 0) {
	    	ZtrBizException exception = ZtrBizException.instance(ResponseConstants.MEMB_IMAGE_TOOLARGE_ERROR_CODE, ResponseConstants.MEMB_IMAGE_TOOLARGE_ERROR_MSG);
	    	LOGGER.error(ResponseConstants.MEMB_IMAGE_TOOLARGE_ERROR_CODE, exception);
	        throw exception ;
	    }

	    // 获取图片的文件名
	    String fileName = file.getOriginalFilename();
	    // 获取图片的扩展名
		String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);

		if(MemberConstants.SAFE_IMAGE_EXT_NAME.indexOf(extensionName.toLowerCase()) == -1){
			ZtrBizException exception = ZtrBizException.instance(ResponseConstants.MEMB_IMAGE_WRONGTYPE_ERROR_CODE, ResponseConstants.MEMB_IMAGE_WRONGTYPE_ERROR_MSG);
			LOGGER.error(ResponseConstants.MEMB_IMAGE_WRONGTYPE_ERROR_CODE, exception);
	        throw exception ;
		}

		String newFileName = String.valueOf(System.currentTimeMillis()) + "." + extensionName ;


		String imageId = memberClientService.saveMemberHeadImage(memberSessionBean.getMemberId(), newFileName, file.getBytes(), 1);
		model.addAttribute("randomDefaultImageId", imageId) ;
		memberSessionBean.setImageId(imageId);
		SSOUtil.refreshMemberSessionBean(memberSessionBean);
		return new AjaxResponse("success", imageId) ;

	}

	@RequestMapping(value = "/saveHeadPickup", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse saveHeadPickup(String headImageId, HttpServletRequest request) throws Exception{
		MemberSessionBean memberSessionBean = memberClientService.getMemberFromRedisBySID() ;
		try{
			Assert.hasText(headImageId);
			memberClientService.saveMemberHeadImage(memberSessionBean.getMemberId(), headImageId, null, 0);
			memberSessionBean.setImageId(headImageId);
			SSOUtil.refreshMemberSessionBean(memberSessionBean);
	        return AjaxResponse.instance(ResponseConstants.MEMB_IMAGE_UPDATE_SUCCESS_CODE
					, ResponseConstants.MEMB_IMAGE_UPDATE_SUCCESS_MSG) ;
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
	        return AjaxResponse.instance(ResponseConstants.MEMB_IMAGE_UPDATE_ERROR_CODE
					, ResponseConstants.MEMB_IMAGE_UPDATE_ERROR_MSG) ;
		}
	}

	@RequestMapping(value = "/saveImproveData", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse saveImproveData(String nickName, String email, String recommender, String imageId, HttpServletRequest request) throws Exception {

		MemberSessionBean memberSessionBean = memberClientService.getMemberFromRedisBySID();

		if(recommender != null && recommender.equals(memberSessionBean.getMobile())){
			return AjaxResponse.instance(ResponseConstants.MEMB_CANTRECSELF_ERROR_CODE, ResponseConstants.MEMB_CANTRECSELF_ERROR_MSG) ;
		}

		try{
			nickName = MemberRegisterValidation.validate(nickName, MemberRegisterValidation.NICKNAME);
			if(email != null && email.length() > 0){
				email = MemberRegisterValidation.validate(email, MemberRegisterValidation.EMAIL);
			}
			if(recommender != null && recommender.length() > 0){
				recommender = MemberRegisterValidation.validate(recommender, MemberRegisterValidation.MOBILE);
			}
		}catch(Exception e){
			return AjaxResponse.instance(e.getMessage(), e.getMessage()) ;
		}

		try{
			if(email != null && email.length() > 0 && memberClientService.isEmailAlreadyExists(email)){
				return AjaxResponse.instance(ResponseConstants.MEMB_EMAIL_ALREADYEXISTS_CODE
						, ResponseConstants.MEMB_EMAIL_ALREADYEXISTS_MSG) ;
			}
			if(recommender != null && recommender.length() > 0 && !memberClientService.isMobileAlreadyExists(recommender)){
				return AjaxResponse.instance(ResponseConstants.MEMB_MOBILE_NOT_FOUND_ERROR_CODE
						, ResponseConstants.MEMB_MOBILE_NOT_FOUND_ERROR_MSG) ;
			}
			String oldRc = memberClientService.getRecommender(memberSessionBean.getMemberId()) ;
			String rcForCoupon = oldRc ;//判断是否有推荐用户
			if(StringUtil.isEmpty(oldRc)){
				String idAndName = memberClientService.selectMemberByMobile(recommender) ;
				if(idAndName != null){
					oldRc = idAndName.split(":")[0] ;
					balanceDetailClientService.insert(oldRc, memberSessionBean.getMemberId(), 100 * 100l) ;
					systemNoticeClientServiceImpl.add(oldRc, NoticeType.REMAINTYPE, SystemNoticeContentUtil.getAbContent(nickName, 100d));
				}
			}
			memberClientService.saveMemberImproveData(memberSessionBean.getMemberId(), nickName, email, oldRc, imageId);
			if(StringUtil.isEmpty(rcForCoupon)){
				couponClientService.grantCoupon(memberSessionBean.getMemberId());
			}
			memberSessionBean.setImageId(imageId);
			memberSessionBean.setNickName(nickName);
			SSOUtil.refreshMemberSessionBean(memberSessionBean);
			return AjaxResponse.instance(ResponseConstants.MEMB_TAKEMEBACK_CODE, memberSessionBean.getUrl()) ;
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return AjaxResponse.instance(ResponseConstants.MEMB_IMPROVEDATA_ERROR_CODE
					, ResponseConstants.MEMB_IMPROVEDATA_ERROR_MSG) ;
		}
	}


	@RequestMapping("/isPasswordSame")
	@ResponseBody
	public String isPasswordSame(String callback,String account, String password) throws IOException {
		boolean result = false ;
		try {
			MemberRegisterValidation.validatePassword(password) ;
			if(MemberLoginValidation.validate(account)) {
				result = ssoService.isPasswordSame(account, password) ;
			} else {
				result = false ;
			}
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			result = false ;
		}
		if(result){
			return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance("success", "")).toString() ;
		}else{
			return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance("error", "")).toString() ;
		}
	}

	@RequestMapping(value = "/changeInfoParam")
	@ResponseBody
	public String changeInfoParam(String callback, String paramName, String paramValue, HttpServletResponse response) throws Exception{
		LOGGER.info(paramName+paramValue);
		if("mobile".equalsIgnoreCase(paramName)){
			JSONObject json = JSONObject.parseObject(paramValue);
			String newMobile = json.getString("newMobile");
			String clientVerifyCode = json.getString("verificationCode");
			String serverVerfyCode = redisClient.get(String.format("%s:%s", MemberConstants.VERIFYCODE_RESETPASSWORD, newMobile)) ;
			if(StringUtils.isBlank(clientVerifyCode) || StringUtils.isBlank(serverVerfyCode) || !clientVerifyCode.equals(serverVerfyCode)){
				return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance("error","验证码验证失败")).toString() ;
			}
			if(!memberClientService.checkMobileOnly(newMobile)){
				return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance("error", "["+newMobile+"]该号码已被注册")).toString() ;
			}
		}
		if("bindMobile".equalsIgnoreCase(paramName)){
            JSONObject json = JSONObject.parseObject(paramValue);
            String bindMobile = json.getString("bindMobile");
            String clientVerifyCode = json.getString("verificationCode");
            String serverVerfyCode = redisClient.get(String.format("%s:%s", MemberConstants.VERIFYCODE_RESETPASSWORD, bindMobile)) ;
            if(StringUtils.isBlank(clientVerifyCode) || StringUtils.isBlank(serverVerfyCode) || !clientVerifyCode.equals(serverVerfyCode)){
                return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance("error","验证码验证失败")).toString() ;
            }
            if(!memberClientService.checkMobileOnly(bindMobile)){
                return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance("error", "["+bindMobile+"]该号码已被注册")).toString() ;
            }
        }
		if("email".equalsIgnoreCase(paramName)){
			JSONObject json = JSONObject.parseObject(paramValue);
			String newEmail = json.getString("newEmail");
			if(memberClientService.isEmailAlreadyExists(newEmail)){
				return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance("error", "["+newEmail+"]该邮箱已存在")).toString() ;
			}
		}
		MemberSessionBean memberSessionBean = memberClientService.getMemberFromRedisBySID() ;
		if(memberSessionBean == null || StringUtils.isBlank(memberSessionBean.getMemberId())){
			response.setStatus(ResponseConstants.MEMBE_AJAX_CALL_TIME_OUT_STATUS);
			return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance("error", "更新个人资料失败：session失效")).toString() ;
		}
		try {
			memberClientService.updateMemberBasicParam(paramName, paramValue, memberSessionBean.getMemberId());
			if("nickName".equalsIgnoreCase(paramName)){
				memberSessionBean.setNickName(paramValue);
				SSOUtil.refreshMemberSessionBean(memberSessionBean);
			}
		} catch (Exception e) {
			LOGGER.error("更新个人资料失败："+paramName+"["+paramValue+"]", e);
			return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance("error", "更新个人资料失败：" +e.getMessage())).toString() ;
		}
		return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance("success","更新个人资料成功")).toString() ;
	}

	@RequestMapping("/bindAndLogin")
    @ResponseBody
    public String bindAndLogin(String callback,String account, String password, String openId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;
        int loginFailCount = memberSessionBean.getLoginFailCount() ;
        try {
            SSOBasicEntity ssoBasicEntity = ssoService.selectMemberByOpenId(openId);
            if (ssoBasicEntity != null && ssoBasicEntity.getMid() != null) {
                if (ssoBasicEntity.getMobile() != null) {
                    return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.OPENID_BINDED_MOBILE_CODE
                            , ResponseConstants.OPENID_BINDED_MOBILE_MSG)).toString() ;
                }
                return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.OPENID_REGISTERED_CODE
                        , ResponseConstants.OPENID_REGISTERED_MSG)).toString() ;
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
                            Cookie cookie = WebUtils.getCookie(request, "token");
                            redisClient.delete("scan_login:"+cookie.getValue());
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

	@RequestMapping("/registerAndLogin")
    @ResponseBody
    public String registerAndLogin(String callback,String openId, String nickName, String headImgUrl, HttpServletRequest request, HttpServletResponse response) throws IOException {

        MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;
        int loginFailCount = memberSessionBean.getLoginFailCount() ;
        try {
            SSOBasicEntity entity = ssoService.doRegisterAndLoginByWx(openId, nickName, headImgUrl);

            Cookie cookie = WebUtils.getCookie(request, "token");
            redisClient.delete("scan_login:"+cookie.getValue());
            if(entity.getIsLogin() && entity.getIsActive()) {
                try{
                    ssoService.updateLastLoginDate(entity.getId());
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
        }catch (Exception e){
            return AjaxJsonpResponse.getInstance(callback,AjaxResponse.instance(ResponseConstants.MEMB_LOGIN_ERROR_CODE
                    , ResponseConstants.MEMB_LOGIN_ERROR_MSG)).toString() ;
        }
    }

	@RequestMapping(value="/unloginLongPoll",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject unLoginLongPoll(String callback, Model model,HttpServletRequest request){
		JSONObject response = new JSONObject();
		response.put("res_code", ResponseConstants.WEB_LONG_POLL_FAILED_CODE);
		Cookie cookie = WebUtils.getCookie(request, "token");
		if(null != cookie){
		    if(SSOUtil.isMemberExists() && SSOUtil.isLogin()) {
		        MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;
                response.put("res_code", ResponseConstants.WEB_LONG_POLL_USER_LOGINED_CODE);
                response.put("member", JSONObject.toJSONString(memberSessionBean));
            } else {
                JSONObject scanStatusObject = null ;
                scanStatusObject = JSONObject.parseObject(redisClient.get("scan_login:"+cookie.getValue()));
                if(null != scanStatusObject){
                    String status  = scanStatusObject.getString("status");
                    if(status.equals("old")){//老用户扫码，直接登录
                        String memberId = scanStatusObject.getString("memberId");
                        response.put("res_code", ResponseConstants.WEB_LONG_POLL_OLD_USER_LOGIN_CODE);
                        response.put("memberId", memberId);
                        SSOBasicEntity entity = ssoService.doLoginByMemberId(memberId);
                        response.put("member", JSONObject.toJSONString(entity));
                    }else if(status.equals("new")){//新用户，跳转到绑定登录页
                        response.put("res_code", ResponseConstants.WEB_LONG_POLL_NEW_USER_LOGIN_CODE);
                        response.put("nickName", scanStatusObject.get("nickName"));
                        response.put("openId", scanStatusObject.get("openId"));
                        response.put("headImgUrl", scanStatusObject.get("headImgUrl"));
                    }
                    redisClient.delete("scan_login:"+cookie.getValue());
                }
            }
		}
		return response;
	}

}
