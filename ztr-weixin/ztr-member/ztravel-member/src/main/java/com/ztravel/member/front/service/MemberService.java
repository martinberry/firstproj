package com.ztravel.member.front.service;

import java.util.List;
import java.util.Map;

import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.member.po.Member;

/**
 * @author wanhaofan
 * */
public interface MemberService {

	Boolean isEmailAlreadyExists(String email) ;

	Boolean isMobileAlreadyExists(String mobile) ;

	String selectMemberByMobile(String mobile) ;

	void sendFindPasswordSms(String mobile, String verifyCode) ;

	void sendGrantCouponSms(String mobile, String verifyCode) ;

	void sendResetPasswordSms(String mobile, String verifyCode) ;

	String saveMemberHeadImage(Member member, String imageNameOrId, byte[] bytes, int type) throws Exception ;

	MemberSessionBean getMemberFromRedisBySID() throws Exception ;

	void updateMemberBasicParam(String paramName, String paramValue, String id)throws Exception ;

	boolean checkPasswordById(String id, String password)throws Exception;

	void updateMemberPasswordById(String id, String newPassword)throws Exception;

	Member getMemberById(String id)throws Exception;

	boolean checkMobileOnly(String mobile)throws Exception;

	List<String> getHeadImages(int count)throws Exception;
	Map<String, Object> getNickNameByPhone(String phone);

	AjaxResponse checkMobile(String mobile);

	String getWxNickNameByOpenId(String openId);

}
