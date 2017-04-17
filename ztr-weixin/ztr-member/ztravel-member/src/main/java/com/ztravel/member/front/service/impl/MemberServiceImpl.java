package com.ztravel.member.front.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.adapter.sms.MobileCaptchaEntity;
import com.ztravel.common.adapter.sms.SmsAdapter;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.ResponseConstants;
import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.member.dao.MemberDao;
import com.ztravel.member.front.service.MemberService;
import com.ztravel.member.po.Member;
import com.ztravel.reuse.member.service.IMemberReuseService;
import com.ztravel.reuse.member.validation.MemberRegisterValidation;

/**
 * @author wanhaofan
 * */
@Service
public class MemberServiceImpl implements MemberService {

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(MemberServiceImpl.class);


	@Resource
	private MemberDao memberDaoImpl ;
	
	@Resource
	private IMemberReuseService memberReuseService ;

	/**
	 * 邮箱是否注册
	 * */
	@Override
	public Boolean isEmailAlreadyExists(String email) {
		return memberReuseService.isEmailAlreadyExists(email) ;
	}

	@Override
	public String selectMemberByMobile(String mobile) {
		return memberReuseService.selectMemberByMobile(mobile) ;
	}

	/**
	 * 手机号码是否注册
	 * */
	@Override
	public Boolean isMobileAlreadyExists(String mobile) {
		return memberReuseService.isMobileAlreadyExists(mobile) ;
	}

	/**
	 * 向指定手机号码发送短信---找回密码
	 * */
	@Override
	public void sendFindPasswordSms(String mobile, String verifyCode) {
		memberReuseService.sendFindPasswordSms(mobile, verifyCode);
	}

	@Override
	public void sendResetPasswordSms(String mobile, String verifyCode) {
		LOGGER.debug(String.format("发送短信{手机号码: %s, 验证码: %s}", mobile, verifyCode));
		MobileCaptchaEntity mobileCaptchaEntity = new MobileCaptchaEntity() ;
		mobileCaptchaEntity.setContent(String.format("真旅行重置密码,验证码:%s", verifyCode));
		mobileCaptchaEntity.setMobileNum(mobile);
		mobileCaptchaEntity.setMsgType("重置找回");
		SmsAdapter.sendMessage(mobileCaptchaEntity);
	}

	/**
	 * 保存用户头像 type : 1 上传形式 type : 0 默认形式
	 * */
	@Override
	public String saveMemberHeadImage(Member member, String imageNameOrId, byte[] bytes, int type) throws Exception {
		return memberReuseService.saveMemberHeadImage(member.getId(), imageNameOrId, bytes, type) ;
	}

	/**
	 * 通过UUID取得MEMBER
	 * */
	@Override
	public MemberSessionBean getMemberFromRedisBySID() throws Exception {
		return memberReuseService.getMemberFromRedisBySID() ;
	}

	@Override
	public void updateMemberBasicParam(String paramName, String paramValue, String id)throws Exception {
		memberReuseService.updateMemberBasicParam(paramName, paramValue, id);
	}

	@Override
	public boolean checkPasswordById(String id, String password)throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("password", password);
		Integer count = memberDaoImpl.countByIdPassword(map);
		if(count == null || count <= 0){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public void updateMemberPasswordById(String id, String newPassword)throws Exception {
		Member member = new Member();
		member.setId(id);
		member.setPassword(newPassword);
		member.setUpdateTime(DateTime.now());
		memberDaoImpl.updateByExample(member);
	}

	@Override
	public Member getMemberById(String id) throws Exception {
		return memberDaoImpl.selectById(id);
	}

	@Override
	public boolean checkMobileOnly(String mobile) throws Exception {
		return memberReuseService.checkMobileOnly(mobile) ;
	}

	@Override
	public List<String> getHeadImages(int count) throws Exception{
		return memberReuseService.getHeadImages(count) ;
	}


	public Map<String, Object> getNickNameByPhone(String phone) {
		Map<String, Object> resultMap = Maps.newHashMap();
		String result = "success";
		String msg = "";
		String memberId = "";
		String idAndNickName = "";
		@SuppressWarnings("unused")
		String destMembeId = "" ;
		String id = "";

		Member destMember = new Member();
		try{
			idAndNickName =	memberDaoImpl.selectByMobile(phone);
		}catch(Exception e){
			result = "fail";
			msg="根据手机号查询用户失败";
			LOGGER.info("根据手机号查询用户失败",e);
		}

	    if(StringUtils.isNotEmpty(idAndNickName)){
	    	 id = idAndNickName.split(":")[0];
	    	 if(StringUtils.isNotEmpty(id)){
	    		 destMembeId = id;
	    		 destMember = memberDaoImpl.selectById(id);
	    	 }
	    }else{
	    	 result="fail";
	    	 msg="该好友未注册 ";
	    }
		if(StringUtils.isNotEmpty(memberId) && null!= destMember){
		      if(memberId.equals(id)){
		    	  result="fail";
		    	  msg="不能分享给自己";
		      }
		      if(StringUtils.isNotEmpty(idAndNickName)){
		    	  msg = idAndNickName.split(":")[1];
		      }

		}
		resultMap.put("msg", msg);
		resultMap.put("result", result);
		resultMap.put("destMember", destMember);


		return resultMap;
	}

	@Override
	public void sendGrantCouponSms(String mobile, String verifyCode) {
		LOGGER.debug(String.format("发送短信{手机号码: %s, 验证码: %s}", mobile, verifyCode));
		MobileCaptchaEntity mobileCaptchaEntity = new MobileCaptchaEntity() ;
		mobileCaptchaEntity.setContent(String.format("真旅行领券,验证码:%s", verifyCode));
		mobileCaptchaEntity.setMobileNum(mobile);
		mobileCaptchaEntity.setMsgType("领券");
		SmsAdapter.sendMessage(mobileCaptchaEntity);
	}

	@Override
	public AjaxResponse checkMobile(String mobile) {
		try{
			mobile = MemberRegisterValidation.validate(mobile, MemberRegisterValidation.MOBILE);
		}catch(Exception e){
			return AjaxResponse.instance(e.getMessage(), e.getMessage()) ;
		}
		if(isMobileAlreadyExists(mobile)){
			return AjaxResponse.instance(ResponseConstants.MEMB_VERIFYMOBILE_ALREADYEXISTS_ERROR_CODE
					, ResponseConstants.MEMB_VERIFYMOBILE_ALREADYEXISTS_ERROR_MSG) ;
		}
		return null;
	}

    @Override
    public String getWxNickNameByOpenId(String openId) {
        return memberDaoImpl.selectWxNickNameByOpenId(openId);
    }

}
