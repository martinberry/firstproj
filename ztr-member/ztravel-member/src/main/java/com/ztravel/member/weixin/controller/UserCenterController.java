package com.ztravel.member.weixin.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.travelzen.framework.core.util.StringUtil;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.Const;
import com.ztravel.common.constants.ProductBookConstans;
import com.ztravel.common.constants.WxMembConst;
import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.security.RandomVerificationUtil;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.common.valid.BasicValidator;
import com.ztravel.common.valid.ValidResult;
import com.ztravel.member.po.TravelerEntity;
import com.ztravel.member.po.TravelerEntity.Credentials;
import com.ztravel.member.front.util.TravelerInfoValidation;
import com.ztravel.reuse.member.validation.MemberRegisterValidation;
import com.ztravel.member.weixin.service.IWxMemberService;
import com.ztravel.member.weixin.service.IWxTravellerService;
import com.ztravel.member.weixin.vo.MemberInfoParam;
import com.ztravel.member.weixin.vo.WMemberVO;
import com.ztravel.sraech.client.service.IAutoComplete;

/**
 * 微信项目用户中心
 * @author MH
 */
@Controller
@RequestMapping("/usercenter/weixin")
public class UserCenterController {

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(UserCenterController.class);

	private static final RedisClient redisClient = RedisClient.getInstance();

	@Resource(name="tThriftAutoComplete")
	IAutoComplete autoComplete;

	@Resource
	private IWxMemberService memberService;

	@Resource
	private IWxTravellerService travellerService;

	//********************用户中心主页面********************//
	@RequestMapping("/index")
	public ModelAndView userCenter(String src, Model model){
		try {
			String memberId = SSOUtil.getMemberId();
			WMemberVO member = memberService.getMemberInfoWithTravellers(memberId);
			model.addAttribute("member", member);
			String wxNickName = memberService.getWxNickNameById(memberId);
			if (wxNickName != null) {
			    model.addAttribute("wxNickName", wxNickName);
			}
			if (src != null && "self".equals(src) && StringUtil.isEmpty(member.getMobile())) {
			    model.addAttribute("showTip", true);
			} else {
			    model.addAttribute("showTip", false);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return new ModelAndView("member/weixin/userCenter/userCenter");
	}

	@RequestMapping(value="/deleteTraveller", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse deleteTraveller(@RequestBody String travellerId){
		AjaxResponse ajaxResponse = null;
		try {
			travellerService.deleteTravellerById(travellerId);
			ajaxResponse = AjaxResponse.instance(WxMembConst.MEMB_DELETE_TRAVELLER_SUCCESS_CODE, WxMembConst.MEMB_DELETE_TRAVELLER_SUCCESS_MSG);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			ajaxResponse = AjaxResponse.instance(WxMembConst.MEMB_DELETE_TRAVELLER_ERROR_CODE, WxMembConst.MEMB_DELETE_TRAVELLER_ERROR_MSG);
		}
		return ajaxResponse;
	}

	//********************编辑用户信息********************//
	@RequestMapping(value="/editMember", method=RequestMethod.GET)
	public ModelAndView openEditMemberInfoPage(Model model){
		try {
			MemberSessionBean memberSessionBean = memberService.getMemberSessionBean();
			WMemberVO member = memberService.getMemberInfoWithoutTravellers(memberSessionBean.getMemberId());
			model.addAttribute("member", member);
            String wxNickName = memberService.getWxNickNameById(memberSessionBean.getMemberId());
            if (wxNickName != null) {
                model.addAttribute("wxNickName", wxNickName);
            }
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return new ModelAndView("member/weixin/userCenter/editMemberInfo");
	}

	@RequestMapping(value="/getVerificationCode", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse getVerificationCode(@RequestBody String mobile){
		try {
			mobile = MemberRegisterValidation.validate(mobile, MemberRegisterValidation.MOBILE);
		} catch (Exception e) {
			return AjaxResponse.instance(WxMembConst.MEMB_INVALID_MOBILE_NUMBER_ERROR_CODE, WxMembConst.MEMB_INVALID_MOBILE_NUMBER_ERROR_MSG);
		}
		try {
			Boolean isMobileExist = memberService.isMobileAlreadyExists(mobile);
			if( isMobileExist ){
				return AjaxResponse.instance(WxMembConst.MEMB_MOBILE_ALREADY_EXISTS_ERROR_CODE, WxMembConst.MEMB_MOBILE_ALREADY_EXISTS_ERROR_MSG);
			}
			String verificationCode = RandomVerificationUtil.getVerificationNumCode();   //验证码10分钟有效
			redisClient.set(String.format("%s:%s", WxMembConst.REDIS_KEY_VERIFYCODE_CHANGE_MOBILE, mobile), verificationCode, 10 * 60);
			memberService.sendChangeMobileSms(mobile, verificationCode);
			return AjaxResponse.instance(WxMembConst.MEMB_SEND_VERIFICATION_CODE_SUCCESS_CODE, WxMembConst.MEMB_SEND_VERIFICATION_CODE_SUCCESS_MSG);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return AjaxResponse.instance(WxMembConst.MEMB_SEND_VERIFICATION_CODE_ERROR_CODE, WxMembConst.MEMB_SEND_VERIFICATION_CODE_ERROR_MSG);
		}
	}

	@RequestMapping(value="/saveMemberInfo", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse saveMemberInfo(@RequestBody MemberInfoParam memberInfo){
		//参数校验
		ValidResult validResult = BasicValidator.valid(memberInfo);
		if( !validResult.isSuccess() ){
			return AjaxResponse.instance(WxMembConst.MEMB_MODIFY_MEMBER_INFO_VALIDATION_ERROR_CODE, WxMembConst.MEMB_MODIFY_MEMBER_INFO_VALIDATION_ERROR_MSG);
		}
		//若修改手机号,检查是否已注册,校验验证码
		if( StringUtils.isNotBlank(memberInfo.getNewMobile()) ){
			Boolean isMobileExist = memberService.isMobileAlreadyExists(memberInfo.getNewMobile());
			if( isMobileExist ){
				return AjaxResponse.instance(WxMembConst.MEMB_MOBILE_ALREADY_EXISTS_ERROR_CODE, WxMembConst.MEMB_MOBILE_ALREADY_EXISTS_ERROR_MSG);
			}
			String inputVerifyCode = memberInfo.getVerificationCode();
			String correctVerifyCode = redisClient.get(String.format("%s:%s", WxMembConst.REDIS_KEY_VERIFYCODE_CHANGE_MOBILE, memberInfo.getNewMobile()));
			if( StringUtils.isBlank(inputVerifyCode) || StringUtils.isBlank(correctVerifyCode) || !inputVerifyCode.equals(correctVerifyCode) ){
				return AjaxResponse.instance(WxMembConst.MEMB_VERIFICATION_CODE_VERIFY_ERROR_CODE, WxMembConst.MEMB_VERIFICATION_CODE_VERIFY_ERROR_MSG);
			}
		}
		//若修邮箱,校验邮箱是否已存在
		if( StringUtils.isNotBlank(memberInfo.getNewEmail())){
			Boolean isEmailExist = memberService.isEmailAlreadyExists(memberInfo.getNewEmail());
			if( isEmailExist ){
				return AjaxResponse.instance(WxMembConst.MEMB_EMAIL_ALREADY_EXISTS_ERROR_CODE, WxMembConst.MEMB_EMAIL_ALREADY_EXISTS_ERROR_MSG);
			}
		}
		try {
			MemberSessionBean memberSessionBean = memberService.getMemberSessionBean();
			memberService.updateMemberInfo(memberSessionBean.getMemberId(), memberInfo);
		} catch (ZtrBizException e) {
			LOGGER.error(e.getRetMsg(), e);
			return AjaxResponse.instance(WxMembConst.MEMB_UPDATE_MEMBER_INFO_ERROR_CODE, WxMembConst.MEMB_UPDATE_MEMBER_INFO_ERROR_MSG);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return AjaxResponse.instance(WxMembConst.MEMB_UPDATE_MEMBER_INFO_ERROR_CODE, WxMembConst.MEMB_UPDATE_MEMBER_INFO_ERROR_MSG);
		}
		return AjaxResponse.instance(WxMembConst.MEMB_UPDATE_MEMBER_INFO_SUCCESS_CODE, WxMembConst.MEMB_UPDATE_MEMBER_INFO_SUCCESS_MSG);
	}

	//********************编辑常旅客信息********************//
	@RequestMapping(value="/editTraveller/{travellerId}", method=RequestMethod.GET)
	public ModelAndView openEditTravellerInfoPage(@PathVariable String travellerId, Model model){
		try {
			TravelerEntity traveller = travellerService.getTravellerById(travellerId);
			model.addAttribute("traveller", traveller);
		} catch (ZtrBizException e) {
			LOGGER.error(e.getRetMsg(), e);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return new ModelAndView("member/weixin/userCenter/editTravellerInfo");
	}

	@RequestMapping(value="/saveTravellerInfo", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse saveTravellerInfo(@RequestBody TravelerEntity traveler){
		AjaxResponse ajaxResponse = null;
		try {
			//参数校验
			if( !isTravellerInfoValid(traveler) ){
				return AjaxResponse.instance(WxMembConst.MEMB_MODIFY_TRAVELLER_INFO_VALIDATION_ERROR_CODE, WxMembConst.MEMB_MODIFY_TRAVELLER_INFO_VALIDATION_ERROR_MSG);
			}
			if(StringUtil.isEmpty(traveler.getNationality())){
				traveler.setNationality(Const.DEFAULT_COUNTRY);
			}
			travellerService.updateTraveller(traveler);
			ajaxResponse = AjaxResponse.instance(WxMembConst.MEMB_UPDATE_TRAVELLER_INFO_SUCCESS_CODE, WxMembConst.MEMB_UPDATE_TRAVELLER_INFO_SUCCESS_MSG);
		} catch (ZtrBizException  zte) {
			if(zte.getRetCode().equals(ProductBookConstans.PASSENGER_NAME_ERROE_ORDER_FAILURE)){
				ajaxResponse = AjaxResponse.instance(ProductBookConstans.PASSENGER_NAME_ERROE_ORDER_FAILURE, "旅客姓名格式错误");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			ajaxResponse = AjaxResponse.instance(WxMembConst.MEMB_UPDATE_TRAVELLER_INFO_ERROR_CODE, WxMembConst.MEMB_UPDATE_TRAVELLER_INFO_ERROR_MSG);
		}
		return ajaxResponse;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/countryAutoComplete", method=RequestMethod.POST)
	@ResponseBody
	public String[] countryAutoComplete(String query){
		List<String> countrys = autoComplete.countryAutoComplete(query, 5);
		Map countryMap = Maps.newHashMap();
     	int size = countrys.size();
     	String[] countryNames = new String[size];
 	   	for(int i = 0; i < countrys.size(); i++){
 	   		countryMap =  JSON.parseObject(countrys.get(i));
 	   		countryNames[i] = (String) countryMap.get("nameCn");
     	}
		return countryNames;
	}

	//********************修改密码********************//
	@RequestMapping("/changePassword")
	public ModelAndView openChangePasswordPage(){
		return new ModelAndView("member/weixin/userCenter/changePassword");
	}

	@RequestMapping(value="/submitPwdChange", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse submitPasswordChange(String oldPwd, String newPwd, String newPwdRetype){
		MemberSessionBean memberSessionBean;
		try {
			memberSessionBean = memberService.getMemberSessionBean();
			Boolean isPwdCorrect = memberService.checkPasswordByMemberId(memberSessionBean.getMemberId(), oldPwd);
			if( !isPwdCorrect ){
				return AjaxResponse.instance(WxMembConst.MEMB_CHANGE_PASSWORD_OLDPWD_WRONG_ERROR_CODE, WxMembConst.MEMB_CHANGE_PASSWORD_OLDPWD_WRONG_ERROR_MSG);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return AjaxResponse.instance(WxMembConst.MEMB_CHANGE_PASSWORD_OLDPWD_CHECK_FAILED_CODE, WxMembConst.MEMB_CHANGE_PASSWORD_OLDPWD_CHECK_FAILED_MSG);
 		}
		//校验新密码格式
		try {
			MemberRegisterValidation.validatePassword(newPwd);
		} catch (Exception e) {
			return AjaxResponse.instance(WxMembConst.MEMB_CHANGE_PASSWORD_NEWPWD_INVALID_ERROR_CODE, WxMembConst.MEMB_CHANGE_PASSWORD_NEWPWD_INVALID_ERROR_MSG);
		}

		if( !newPwd.equals(newPwdRetype) ){
			return AjaxResponse.instance(WxMembConst.MEMB_CHANGE_PASSWORD_NEWPWD_NOT_EQUAL_ERROR_CODE, WxMembConst.MEMB_CHANGE_PASSWORD_NEWPWD_NOT_EQUAL_ERROR_MSG);
		}

		try {
			memberService.updatePasswordById(memberSessionBean.getMemberId(), newPwd);
			return AjaxResponse.instance(WxMembConst.MEMB_CHANGE_PASSWORD_SUCCESS_CODE, WxMembConst.MEMB_CHANGE_PASSWORD_SUCCESS_MSG);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return AjaxResponse.instance(WxMembConst.MEMB_CHANGE_PASSWORD_FAILED_CODE, WxMembConst.MEMB_CHANGE_PASSWORD_FAILED_MSG);
		}

	}

	private boolean isTravellerInfoValid(TravelerEntity traveller) throws Exception{
		String wholeEnNameReg = "^[a-zA-z]{1,60}$";
		String wholeChNameReg = "^[\\u4E00-\\u9FA5]{1,40}$";
		String name = traveller.getFirstNameCn() + traveller.getLastNameCn();
		TravelerInfoValidation.validate(traveller.getFirstNameCn(), TravelerInfoValidation.FIRSTNAME);
		TravelerInfoValidation.validate(traveller.getLastNameCn(), TravelerInfoValidation.LASTNAME);

		if(!name.matches(wholeEnNameReg) && !name.matches(wholeChNameReg)){
			throw ZtrBizException.instance(ProductBookConstans.PASSENGER_NAME_ERROE_ORDER_FAILURE, "旅客姓名不正确");
		}

		if(StringUtils.isNotEmpty(traveller.getEmail())){
			TravelerInfoValidation.validate(traveller.getEmail(), TravelerInfoValidation.EMAIL);
		}
		if(StringUtils.isNotEmpty(traveller.getPhoneNum())){
			TravelerInfoValidation.validate(traveller.getPhoneNum(), TravelerInfoValidation.MOBILE);
		}
		if(StringUtils.isNotEmpty(traveller.getNationality())){
			return isExistNationalName(traveller.getNationality());
		}
		if( traveller.getCredentials() != null ){
			for(Credentials credential : traveller.getCredentials()){
				switch(credential.getType()){
				case "IDCARD":
					TravelerInfoValidation.validate(credential.getNumber(), TravelerInfoValidation.CREDIT);
					break;
				case "PASSPORT":
					TravelerInfoValidation.validate(credential.getNumber(), TravelerInfoValidation.PASSPORT);
					break;
				case "GANGAOPASS":
					TravelerInfoValidation.validate(credential.getNumber(), TravelerInfoValidation.GANGAOPASS);
					break;
				}
			}
		}
		return true;
	}


   public boolean  isExistNationalName(String nationalName) throws Exception {
    	boolean isExist = false;
		List<String> nationalitysSearchFromLucene = autoComplete.countryAutoComplete(nationalName, 5);
		@SuppressWarnings("rawtypes")
		Map nationalMap = Maps.newHashMap();
     	String nationalNameSearchFromLucene	= "";
 	   	for(int i=0;i<nationalitysSearchFromLucene.size();i++){
     	   	nationalMap =  JSON.parseObject(nationalitysSearchFromLucene.get(i));
     	   nationalNameSearchFromLucene=(String) nationalMap.get("nameCn");
     	    if(StringUtils.isNotBlank(nationalNameSearchFromLucene)){
     	    	if(nationalNameSearchFromLucene.equals(nationalName)){
     	    		isExist = true;
     	    		break;
     	    	}
     	    }
     	}
    	return isExist;
    }
}
