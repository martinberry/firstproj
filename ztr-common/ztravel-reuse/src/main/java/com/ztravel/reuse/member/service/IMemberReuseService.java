package com.ztravel.reuse.member.service;

import java.util.List;

import com.ztravel.common.entity.MemberSessionBean;

public interface IMemberReuseService {
	Boolean isEmailAlreadyExists(String email) ;

	Boolean isMobileAlreadyExists(String mobile) ;
	
	MemberSessionBean getMemberFromRedisBySID() throws Exception ;
	
	void sendFindPasswordSms(String mobile, String verifyCode) ;
	
	List<String> getHeadImages(int count);
	
	String saveMemberHeadImage(String id, String imageNameOrId, byte[] bytes, int type) throws Exception ;
	
	String selectMemberByMobile(String mobile) ;
	
	boolean checkMobileOnly(String mobile)throws Exception;
	
	void updateMemberBasicParam(String paramName, String paramValue, String id)throws Exception ;
	
	String getNickNameByMid(String mid) ;
}
