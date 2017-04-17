package com.ztravel.member.client.service;

import java.util.List;

import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.member.po.WxUserEntity;
import com.ztravel.member.po.MemberTimeEntity;



/**
 * @author wanhaofan
 * */
public interface IMemberClientService {

	/**
	 * 手机是否存在
	 * @param mobile
	 * @return
	 */
	Boolean isMobileAlreadyExists(String mobile) ;

	Boolean isEmailAlreadyExists(String email) ;

	MemberTimeEntity getMemberRegisterTimeById(String id) throws Exception ;

	String getRandomNickName() ;

	String getRandomDefaultImageId() ;

	void updatePasswordById(String memberId, String newPassword) throws Exception ;

	MemberSessionBean getMemberFromRedisBySID() throws Exception ;

	void updateMemberPasswordByMobile(String mobile, String password) throws Exception ;

	void updateMemberPasswordByEmail(String email, String password) throws Exception ;

	void saveMemberImproveData(String id, String nickName, String email,
			String recommender, String imageId) throws Exception ;

	void sendRegisterSms(String mobile, String verifyCode) ;

	void sendFindPasswordSms(String mobile, String verifyCode) ;

	List<String> getHeadImages(int count);

	String saveMemberHeadImage(String id, String imageNameOrId, byte[] bytes, int type) throws Exception ;

	String selectMemberByMobile(String mobile) ;

	Integer countAll() ;

	String getMinMemberById(String id);

	String getMemberIdByMid(String mid);

	String getMemberByMid(String mid);

	String getNickNameByMid(String mid);

	String getRecommender(String id) ;

    String getPassword(String id) ;

    boolean checkMobileOnly(String mobile) throws Exception;

    void updateMemberBasicParam(String paramName, String paramValue, String id)throws Exception ;

    Boolean isWxLogined(String memberId);

    WxUserEntity selectWxUserByOpenId(String openId);

    WxUserEntity insertWxUserByOpenId(String openId) throws Exception;

    String getMidById(String id) throws Exception;

    void bindMobileToMember(String id, String mobile, String password) throws Exception ;

    String getMobileById(String id);

    String selectMemberIdByOpenId(String openId);
}
