package com.ztravel.member.front.controller;

import java.awt.image.BufferedImage;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.ResponseConstants;
import com.ztravel.common.holder.TokenHolder;
import com.ztravel.common.security.RandomVerificationUtil;
import com.ztravel.member.client.service.ICouponClientService;
import com.ztravel.reuse.member.entity.MemberConstants;
import com.ztravel.member.front.service.MemberService;
import com.ztravel.reuse.member.validation.MemberRegisterValidation;
import com.ztravel.sso.client.service.SSOClientService;
import com.ztravel.sso.client.entity.SSOClientEntity;

/**
 * 领代金券入口
 * @author haofan.wan
 */

@Controller
@RequestMapping("/grant")
public class GrantCouponController {

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(GrantCouponController.class);

	@Resource
	private SSOClientService ssoClientService ;

	@Resource
	private MemberService memberService ;

	@Resource
	private ICouponClientService couponClientService ;

	@Resource
	Producer captchaProducer;

	private static final RedisClient redisClient = RedisClient.getInstance();

	@RequestMapping("/coupon")
	public String coupon() {
		return "member/front/grant_coupon" ;
	}

	@RequestMapping("/grantCouponSuccess")
	public String grantCouponSuccess() {
		return "member/front/grant_coupon_success" ;
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


	@RequestMapping(value = "/sendCouponSms", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse sendCouponSms(String mobile, HttpServletRequest request) {
		try{
			mobile = MemberRegisterValidation.validate(mobile, MemberRegisterValidation.MOBILE);
		}catch(Exception e){
			return AjaxResponse.instance(e.getMessage(), e.getMessage()) ;
		}
		try{
			String randomVerifyCode = RandomVerificationUtil.getVerificationNumCode() ;
			redisClient.set(String.format("%s:%s", MemberConstants.VERIFYCODE_COUPON, mobile), randomVerifyCode, 10 * 60);
			memberService.sendGrantCouponSms(mobile, randomVerifyCode) ;
			return AjaxResponse.instance(ResponseConstants.MEMB_VERIFYCODE_SENDSMS_SUCCESS_CODE
					, ResponseConstants.MEMB_VERIFYCODE_SENDSMS_SUCCESS_MSG) ;
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return AjaxResponse.instance(ResponseConstants.MEMB_VERIFYCODE_SENDSMS_ERROR_CODE
					, ResponseConstants.MEMB_VERIFYCODE_SENDSMS_ERROR_MSG) ;
		}
	}


	@RequestMapping(value = "/grantCoupon", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse grant(String mobile, String verifyCode, HttpServletRequest request) {
		try{
			mobile = MemberRegisterValidation.validate(mobile, MemberRegisterValidation.MOBILE);
			MemberRegisterValidation.validateVerifyCode(verifyCode);
		}catch(Exception e){
			return AjaxResponse.instance(e.getMessage(), e.getMessage()) ;
		}

		try{
			String serverVerfyCode = redisClient.get(String.format("%s:%s", MemberConstants.VERIFYCODE_COUPON, mobile)) ;
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
			if(memberService.isMobileAlreadyExists(mobile)){
				String idNickName = memberService.selectMemberByMobile(mobile) ;
				if(idNickName != null){
					couponClientService.grantCoupon(idNickName.split(":")[0]);
				}
			}else{
				SSOClientEntity entity = new SSOClientEntity() ;
				entity.setMobile(mobile);
				ssoClientService.doRegisterAndSendPassword(entity, false);
			}
			return AjaxResponse.instance("success", "success") ;
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
			String serverVerfyCode = redisClient.get(String.format("%s:%s", MemberConstants.VERIFYCODE_COUPON, mobile)) ;
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

}
