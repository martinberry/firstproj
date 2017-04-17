package com.ztravel.member.front.controller;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Producer;
import com.travelzen.framework.core.util.StringUtil;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.ResponseConstants;
import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.holder.TokenHolder;
import com.ztravel.common.security.RandomVerificationUtil;
import com.ztravel.common.security.SignUtil;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.member.client.service.FrontCommonService;
import com.ztravel.reuse.member.entity.MemberConstants;
import com.ztravel.member.front.vo.BindRequest;
import com.ztravel.member.po.Member;
import com.ztravel.reuse.member.validation.MemberRegisterValidation;
import com.ztravel.member.front.service.MemberService;
import com.ztravel.member.front.util.MemberConvert;
import com.ztravel.sso.client.service.SSOClientService;
import com.ztravel.sso.po.SSOBasicEntity;

/**
 * C端注册
 * @author haofan.wan
 */

@Controller
@RequestMapping("/member")
public class MemberController {

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(MemberController.class);

	private static final RedisClient redisClient = RedisClient.getInstance();

	@Resource
	private MemberService memberServiceImpl ;

	@Resource
	Producer captchaProducer;

	@Resource
	private FrontCommonService frontCommonService;

    @Resource
	SSOClientService ssoClientServiceImpl;

	@RequestMapping(value = "/info")
	public String memberInfo( HttpServletRequest request, Model model) throws Exception{
		try {
			MemberSessionBean memberSessionBean = memberServiceImpl.getMemberFromRedisBySID() ;
			Member member = null;
			String ticket  = null;
			member = memberServiceImpl.getMemberById(memberSessionBean.getMemberId());
			model.addAttribute("member", member);
			if (StringUtils.isNotEmpty(member.getOpenId())) {
			    String wxNickName = memberServiceImpl.getWxNickNameByOpenId(member.getOpenId());
			    model.addAttribute("wxNickName", wxNickName);
			    ticket  = frontCommonService.getUnbindTicket();
			} else {
			    ticket  = frontCommonService.getBindTicket();
			}
			model.addAttribute("ticket", ticket);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			if(e instanceof ZtrBizException && ((ZtrBizException) e).getRetCode().equals(ResponseConstants.MEMB_NOTFOUND_ERROR_CODE)){
				return "redirect:/home";
			}
		}
		return "member/front/memberInfo" ;
	}

	@RequestMapping("/resetPwdByOld")
	@ResponseBody
	public AjaxResponse resetPwdByOld(HttpServletRequest request, String password, String newPassword) {
		MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;
		password = SignUtil.signPassword(newPassword, SignUtil.FRONT_SIGN_KEY) ;
		try {
			if(memberServiceImpl.checkPasswordById(memberSessionBean.getMemberId(), password)){
				newPassword = SignUtil.signPassword(newPassword, SignUtil.FRONT_SIGN_KEY) ;
				memberServiceImpl.updateMemberPasswordById(memberSessionBean.getMemberId(), newPassword);
			}else{
				return AjaxResponse.instance("error", "原密码验证失败") ;
			}
		} catch (Exception e) {
			LOGGER.error("", e);
			return AjaxResponse.instance("error", "重置密码失败："+e.getMessage()) ;
		}
		return AjaxResponse.instance("success", "重置密码成功") ;
	}

	@RequestMapping(value = "/refreshImage")
	@ResponseBody
	public List<String> refreshImage(){
		List<String> images = new LinkedList<String>() ;
		try{
			images = memberServiceImpl.getHeadImages(10) ;
		}catch(Exception e){
			//捕获异常,不影响正常流程
			LOGGER.error(e.getMessage(), e);
		}
		return images ;
	}
	@RequestMapping(value = "/changeHead", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse changeHead(
			@RequestParam(value = "imageInputer", required = true) MultipartFile file,
	        HttpServletRequest request) throws Exception {
		MemberSessionBean memberSessionBean = memberServiceImpl.getMemberFromRedisBySID() ;
		// 判断图片大小是否大于2M
	    if (file.getSize() > 2 * 1024 * 1024 || file.getSize() == 0) {
	    	ZtrBizException exception = ZtrBizException.instance(ResponseConstants.MEMB_IMAGE_TOOLARGE_ERROR_CODE, ResponseConstants.MEMB_IMAGE_TOOLARGE_ERROR_MSG);
	    	LOGGER.error(ResponseConstants.MEMB_IMAGE_TOOLARGE_ERROR_CODE, exception);
	        return AjaxResponse.instance("error", ResponseConstants.MEMB_IMAGE_TOOLARGE_ERROR_MSG) ;
	    }
	    // 获取图片的文件名
	    String fileName = file.getOriginalFilename();
	    // 获取图片的扩展名
		String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
		if(MemberConstants.SAFE_IMAGE_EXT_NAME.indexOf(extensionName.toLowerCase()) == -1){
			ZtrBizException exception = ZtrBizException.instance(ResponseConstants.MEMB_IMAGE_WRONGTYPE_ERROR_CODE, ResponseConstants.MEMB_IMAGE_WRONGTYPE_ERROR_MSG);
			LOGGER.error(ResponseConstants.MEMB_IMAGE_WRONGTYPE_ERROR_CODE, exception);
			return AjaxResponse.instance("error", ResponseConstants.MEMB_IMAGE_WRONGTYPE_ERROR_MSG) ;
		}
		String newFileName = String.valueOf(System.currentTimeMillis()) + "." + extensionName ;
		Member member = MemberConvert.MemberSessionBean2Po(memberSessionBean) ;
		String imageId = memberServiceImpl.saveMemberHeadImage(member, newFileName, file.getBytes(), 1);
		memberSessionBean.setImageId(imageId);
		SSOUtil.refreshMemberSessionBean(memberSessionBean);
		return AjaxResponse.instance("success", imageId) ;
	}

	@RequestMapping(value = "/saveHeadPickup", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse saveHeadPickup(String headImageId, HttpServletRequest request) throws Exception{
		MemberSessionBean memberSessionBean = memberServiceImpl.getMemberFromRedisBySID() ;
		try{
			Assert.hasText(headImageId);
			memberSessionBean.setImageId(headImageId);
			Member member = MemberConvert.MemberSessionBean2Po(memberSessionBean) ;
			memberServiceImpl.saveMemberHeadImage(member, headImageId, null, 0);
			SSOUtil.refreshMemberSessionBean(memberSessionBean);
	        return AjaxResponse.instance(ResponseConstants.MEMB_IMAGE_UPDATE_SUCCESS_CODE
					, ResponseConstants.MEMB_IMAGE_UPDATE_SUCCESS_MSG) ;
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
	        return AjaxResponse.instance(ResponseConstants.MEMB_IMAGE_UPDATE_ERROR_CODE
					, ResponseConstants.MEMB_IMAGE_UPDATE_ERROR_MSG) ;
		}
	}

	@RequestMapping(value = "/sendResetPasswordSms", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse sendResetPasswordSms(String mobile, HttpServletRequest request) {
		try{
			mobile = MemberRegisterValidation.validate(mobile, MemberRegisterValidation.MOBILE);
		}catch(Exception e){
			return AjaxResponse.instance(e.getMessage(), e.getMessage()) ;
		}
		try{
			String randomVerifyCode = RandomVerificationUtil.getVerificationNumCode() ;
			redisClient.set(String.format("%s:%s", MemberConstants.VERIFYCODE_RESETPASSWORD, mobile), randomVerifyCode, 10 * 60);
			memberServiceImpl.sendResetPasswordSms(mobile, randomVerifyCode) ;
			return AjaxResponse.instance(ResponseConstants.MEMB_VERIFYCODE_SENDSMS_SUCCESS_CODE
					, ResponseConstants.MEMB_VERIFYCODE_SENDSMS_SUCCESS_MSG) ;
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return AjaxResponse.instance(ResponseConstants.MEMB_VERIFYCODE_SENDSMS_ERROR_CODE
					, ResponseConstants.MEMB_VERIFYCODE_SENDSMS_ERROR_MSG) ;
		}
	}

	@RequestMapping(value = "/checkMobile", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse checkMobile(String mobile, HttpServletRequest request) {
		try{
			mobile = MemberRegisterValidation.validate(mobile, MemberRegisterValidation.MOBILE);
		}catch(Exception e){
			return AjaxResponse.instance("error", e.getMessage()) ;
		}
		try{
			if(memberServiceImpl.checkMobileOnly(mobile)){
				return AjaxResponse.instance("success", "") ;
			}else{
				return AjaxResponse.instance("fail", "["+mobile+"]该号码已被注册") ;
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return AjaxResponse.instance("error", e.getMessage()) ;
		}
	}

	@RequestMapping(value = "/recommenderRewardPlan")
	public String recommenderRewardPlan(String mobile, HttpServletRequest request) {
		return "/common/front/recommender_reward_plan" ;
	}

	/**
	 * 跳转到微信绑定登录页
	 * */
	@RequestMapping(value="/tobind",method=RequestMethod.POST)
	public String toBinRdPage(Model model,BindRequest bindRequest){

		return "member/front/bind_login" ;
	}

	@RequestMapping(value="/unloginLongPoll",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject unLoginLongPoll(String callback, Model model,HttpServletRequest request) throws Exception{
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
                        SSOBasicEntity entity = ssoClientServiceImpl.doLoginByMemberId(memberId);
                        if (entity.getIsActive()) {
                            ssoClientServiceImpl.updateLastLoginDate(entity.getId());
                        }
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

	@RequestMapping(value="/getTicket",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject getTicket(Model model,HttpServletRequest request){
		JSONObject response = new JSONObject();
		MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean();
		String ticket =frontCommonService.getTicket();
		if(null != memberSessionBean){
			if(StringUtils.isNotBlank(memberSessionBean.getMemberId())){
				response.put("isLogin", true);
				response.put("wxLogined", frontCommonService.isWxLogined(memberSessionBean.getMemberId()));
			}else{
				response.put("isLogin", false);
			}
			response.put("success", true);
		}else{
			response.put("success", false);
		}
		response.put("ticket", ticket);
		return response;
	}

	@RequestMapping(value="/isWxLogined",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject isWxLogined(Model model,HttpServletRequest request){
		JSONObject response = new JSONObject();
		Boolean wxLogined = false;
		MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean();
		if(null != memberSessionBean && StringUtils.isNotBlank(memberSessionBean.getMemberId())){
		    if (!memberSessionBean.getIsActive()) {
		        response.put("errorMsg", "账号异常");
		    }else {
		        wxLogined = frontCommonService.isWxLogined(memberSessionBean.getMemberId());
		    }
		}
		response.put("wxLogined", wxLogined);
		return response;
	}

	@RequestMapping(value="/memberInfoLongPoll",method=RequestMethod.GET)
    @ResponseBody
	public JSONObject memberInfoLongPoll(Model model,HttpServletRequest request) throws Exception {
	    JSONObject response = new JSONObject();
	    Boolean closeLongPoll = false;
	    String resultType = "";
	    MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean();
	    if (memberSessionBean == null) {
	        closeLongPoll = true;
	    } else {
	        String memberId = memberSessionBean.getMemberId();
            String mobile = memberSessionBean.getMobile();
            if (StringUtil.isEmpty(mobile)) {
                closeLongPoll = true;
            } else {
                JSONObject memberInfoJson = JSONObject.parseObject(redisClient.get("scan_memberInfo:" + memberId + "&" + TokenHolder.get()));
//                LOGGER.info("memberInfoJson redis key:::{}", memberId + "&" + TokenHolder.get());
                if (memberInfoJson != null) {
                    LOGGER.info("轮巡用户 [{}] 绑定或解绑微信二维码信息得到的信息为::{}", memberId, memberInfoJson);
                    closeLongPoll = memberInfoJson.getBoolean("result_code");
                    resultType = memberInfoJson.getString("result_type");
                    if (!closeLongPoll) {
                        String resultMsg = memberInfoJson.getString("result_msg");
                        response.put("resultMsg", resultMsg);
                    }
                    redisClient.delete("scan_memberInfo:" + memberId + "&" + TokenHolder.get());
                }
            }
	    }
        response.put("closeLongPoll", closeLongPoll);
        response.put("resultType", resultType);
	    return response;
	}

}
