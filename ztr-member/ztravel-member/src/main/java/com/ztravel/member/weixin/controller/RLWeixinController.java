package com.ztravel.member.weixin.controller;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.travelzen.framework.core.util.StringUtil;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.adapter.sms.SMSType;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.ResponseConstants;
import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.common.enums.NoticeType;
import com.ztravel.common.holder.TokenHolder;
import com.ztravel.common.mail.MailEntity;
import com.ztravel.common.mail.MailEnums;
import com.ztravel.common.security.RandomVerificationUtil;
import com.ztravel.common.security.SignUtil;
import com.ztravel.common.util.MailSender;
import com.ztravel.common.util.OpenIdUtil;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.common.util.SystemNoticeContentUtil;
import com.ztravel.common.util.WebEnv;
import com.ztravel.member.client.service.IBalanceDetailClientService;
import com.ztravel.member.client.service.ICouponClientService;
import com.ztravel.member.client.service.ISystemNoticeClientService;
import com.ztravel.reuse.member.entity.MemberConstants;
import com.ztravel.reuse.member.validation.MemberRegisterValidation;
import com.ztravel.member.weixin.service.IWxMemberService;


@Controller
@RequestMapping("/rl")
public class RLWeixinController {


	private static final Logger LOGGER = RequestIdentityLogger.getLogger(RLWeixinController.class);

	@Resource
	private IWxMemberService wxMemberService ;

	@Resource
	private IBalanceDetailClientService balanceDetailClientService ;

	@Resource
	private ICouponClientService couponClientService ;

	@Resource
	private ISystemNoticeClientService systemNoticeClientServiceImpl;

	@Resource
	Producer captchaProducer;

	private static final RedisClient redisClient = RedisClient.getInstance();

	@RequestMapping(value = "/torl")
	public String toRl(String backUrl, String toLogin, Model model){
		model.addAttribute("backUrl", backUrl) ;
		model.addAttribute("toLogin", toLogin) ;
		if (OpenIdUtil.getOpenId() == null) {
	        model.addAttribute("existsOpenId", false) ;
		} else {
            model.addAttribute("existsOpenId", true) ;
		}
		return "member/weixin/rl/register_login" ;
	}

	@RequestMapping(value = "/toBind")
    public String toBind(String backUrl, String toLogin, Model model){
        model.addAttribute("backUrl", "/rl/torl") ;
        model.addAttribute("backOrigUrl", backUrl) ;
	    return "member/weixin/rl/bind_login" ;
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

	@RequestMapping(value = "/sendRegisterSms", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse sendRegisterSms(String mobile, HttpServletRequest request) {
		try{
			AjaxResponse reponse = wxMemberService.checkMobile(mobile) ;
			if(reponse == null){
				String randomVerifyCode = RandomVerificationUtil.getVerificationNumCode() ;
				redisClient.set(String.format("%s:%s", MemberConstants.VERIFYCODE_REGISTER, mobile), randomVerifyCode, 10 * 60);
				wxMemberService.sendSMS(mobile, randomVerifyCode, SMSType.REGISTER) ;
				return AjaxResponse.instance(ResponseConstants.MEMB_VERIFYCODE_SENDSMS_SUCCESS_CODE
						, ResponseConstants.MEMB_VERIFYCODE_SENDSMS_SUCCESS_MSG) ;
			}else{
				return reponse ;
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return AjaxResponse.instance(ResponseConstants.MEMB_VERIFYCODE_SENDSMS_ERROR_CODE
					, ResponseConstants.MEMB_VERIFYCODE_SENDSMS_ERROR_MSG) ;
		}
	}

	@RequestMapping(value = "/sendCommonSms", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse sendCommonSms(String mobile, HttpServletRequest request) {
        try{
            try{
                mobile = MemberRegisterValidation.validate(mobile, MemberRegisterValidation.MOBILE);
            }catch(Exception e){
                return AjaxResponse.instance(e.getMessage(), e.getMessage()) ;
            }
            String randomVerifyCode = RandomVerificationUtil.getVerificationNumCode() ;
            redisClient.set(String.format("%s:%s", MemberConstants.VERIFYCODE_COMMON, mobile), randomVerifyCode, 10 * 60);
            wxMemberService.sendSMS(mobile, randomVerifyCode, SMSType.COMMON) ;
            return AjaxResponse.instance(ResponseConstants.MEMB_VERIFYCODE_SENDSMS_SUCCESS_CODE
                    , ResponseConstants.MEMB_VERIFYCODE_SENDSMS_SUCCESS_MSG) ;
        }catch(Exception e){
            LOGGER.error(e.getMessage(), e);
            return AjaxResponse.instance(ResponseConstants.MEMB_VERIFYCODE_SENDSMS_ERROR_CODE
                    , ResponseConstants.MEMB_VERIFYCODE_SENDSMS_ERROR_MSG) ;
        }
    }

	@RequestMapping(value = "/improvePersonalData")
	public ModelAndView improvePersonalData(Model model, HttpServletRequest request) throws Exception{
		MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;
		String imageId = memberSessionBean.getImageId() ;
		String nickName = memberSessionBean.getNickName() ;
		try{
			if(imageId != null && imageId.length() > 0){
				model.addAttribute("randomDefaultImageId", imageId) ;
			}else {
				model.addAttribute("randomDefaultImageId", wxMemberService.getRandomDefaultImageId()) ;
			}
			if(nickName != null && nickName.length() > 0){
				model.addAttribute("randomNickName", nickName) ;
			}else {
				model.addAttribute("randomNickName", wxMemberService.getRandomNickName()) ;
			}
		}catch(Exception e){
			//捕获异常,不影响正常流程
			LOGGER.error(e.getMessage(), e);
		}
		return new ModelAndView("member/weixin/rl/improve_personal_data") ;
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
			if(wxMemberService.isEmailAlreadyExists(email)){
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

	@RequestMapping(value = "/saveImproveData", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse saveImproveData(String nickName, String email, String recommender, HttpServletRequest request) throws Exception {

		MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;

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
			if(email != null && email.length() > 0 && wxMemberService.isEmailAlreadyExists(email)){
				return AjaxResponse.instance(ResponseConstants.MEMB_EMAIL_ALREADYEXISTS_CODE
						, ResponseConstants.MEMB_EMAIL_ALREADYEXISTS_MSG) ;
			}
			if(recommender != null && recommender.length() > 0 && !wxMemberService.isMobileAlreadyExists(recommender)){
				return AjaxResponse.instance(ResponseConstants.MEMB_MOBILE_NOT_FOUND_ERROR_CODE
						, ResponseConstants.MEMB_MOBILE_NOT_FOUND_ERROR_MSG) ;
			}
			String oldRc = wxMemberService.getRecommender(memberSessionBean.getMemberId()) ;
			String rcForCoupon = oldRc ;//判断是否有推荐用户
			if(StringUtil.isEmpty(oldRc)){
				String idAndName = wxMemberService.selectMemberByMobile(recommender) ;
				if(idAndName != null){
					oldRc = idAndName.split(":")[0] ;
					balanceDetailClientService.insert(oldRc, memberSessionBean.getMemberId(), 100 * 100l) ;
					systemNoticeClientServiceImpl.add(oldRc, NoticeType.REMAINTYPE, SystemNoticeContentUtil.getAbContent(nickName, 100d));
				}
			}
			wxMemberService.saveMemberImproveData(memberSessionBean.getMemberId(), nickName, email, oldRc);
			if(StringUtil.isEmpty(rcForCoupon)){
				String idAndName = wxMemberService.selectMemberByMobile(recommender) ;
				if(idAndName != null){
					couponClientService.grantCoupon(memberSessionBean.getMemberId());
				}
			}
			memberSessionBean.setNickName(nickName);
			SSOUtil.refreshMemberSessionBean(memberSessionBean);
			return AjaxResponse.instance(ResponseConstants.MEMB_TAKEMEBACK_CODE, memberSessionBean.getUrl()) ;
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return AjaxResponse.instance(ResponseConstants.MEMB_IMPROVEDATA_ERROR_CODE
					, ResponseConstants.MEMB_IMPROVEDATA_ERROR_MSG) ;
		}
	}

	@RequestMapping(value = "/findPassword")
	public String findPassword(String url, Model model){
	    if (!StringUtil.isTrimEmpty(url)) {
	        model.addAttribute("backUrl", url) ;
	    } else {
	        model.addAttribute("backUrl", "/rl/torl") ;
	    }
		return "member/weixin/rl/find_password" ;
	}

	@RequestMapping(value = "/findPasswordByMobile")
	public String findPasswordByModel(){
		return "member/weixin/rl/find_password_by_mobile" ;
	}

	@RequestMapping(value = "/findPasswordByMail")
	public String findPasswordByMail(){
		return "member/weixin/rl/find_password_by_mail" ;
	}

	@RequestMapping(value = "/resetPasswordByMobile")
	public String resetPassword(){
		return "member/weixin/rl/reset_password_by_mobile" ;
	}

	@RequestMapping("/checkFindPasswordVerifyCode")
	@ResponseBody
	public AjaxResponse checkFindPasswordVerifyCode(String mobile, String verifyCode, HttpServletRequest request) throws Exception {
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

	@RequestMapping(value = "/sendFindPasswordSms", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse sendFindPasswordSms(String mobile, HttpServletRequest request) {
		try{
			mobile = MemberRegisterValidation.validate(mobile, MemberRegisterValidation.MOBILE);
		}catch(Exception e){
			return AjaxResponse.instance(e.getMessage(), e.getMessage()) ;
		}
		try{
			if(wxMemberService.isMobileAlreadyExists(mobile)){
				String randomVerifyCode = RandomVerificationUtil.getVerificationNumCode() ;
				redisClient.set(String.format("%s:%s", MemberConstants.VERIFYCODE_FINDPASSWORD, mobile), randomVerifyCode, 10 * 60);
				wxMemberService.sendSMS(mobile, randomVerifyCode, SMSType.FIND_PASSWORD) ;
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

	@RequestMapping(value = "/findPasswordMail", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse findPasswordMail(String email, HttpServletRequest request){
		try{
			email = MemberRegisterValidation.validate(email, MemberRegisterValidation.EMAIL);
		}catch(Exception e){
			return AjaxResponse.instance(e.getMessage(), e.getMessage()) ;
		}
		try{
			if(wxMemberService.isEmailAlreadyExists(email)){
				String sid = SignUtil.signSid(TokenHolder.get(), System.currentTimeMillis() + 60 * 60 * 1000, SignUtil.FRONT_SIGN_KEY) ;
				if(redisClient.exists(sid)){
					return AjaxResponse.instance(ResponseConstants.MEMB_MAIL_ALREADY_SEND_CODE
							, ResponseConstants.MEMB_MAIL_ALREADY_SEND_MSG) ;
				}
				redisClient.set(sid, email, 60 * 60);
				String url = WebEnv.get("server.path.wxServer", "") + "/rl/findPasswordNewPasswordEmail?sid=" + sid ;
				Map<String, String> params = new HashMap<String, String>() ;
				params.put("URL", url) ;
				params.put("DATE", DateTime.now().toString("yyyy-MM-dd")) ;
				params.put("HOMEPAGE", WebEnv.get("server.path.wxServer", "")) ;
				params.put("MEMBERINFO", WebEnv.get("server.path.wxServer", "")) ;
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

	@RequestMapping("/findPasswordNewPasswordEmail")
	public ModelAndView findPasswordNewPassword(String sid, Model model) {
		String email = redisClient.get(sid) ;
		if(email != null){
			model.addAttribute("sid", sid) ;
			return new ModelAndView("member/weixin/rl/reset_password_by_mail") ;
		}else{
			model.addAttribute("errorHint", "链接已失效，请重新获取") ;
			return new ModelAndView("member/weixin/rl/find_password_by_mail") ;
		}

	}


	@RequestMapping(value = "/jumpToSharePlan")
	public String jumpToSharePlan(){
		return "member/weixin/rl/share_plan" ;
	}
}
