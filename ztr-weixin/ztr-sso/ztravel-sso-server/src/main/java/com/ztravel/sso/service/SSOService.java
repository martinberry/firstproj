package com.ztravel.sso.service;

import javax.servlet.http.Cookie;

import com.ztravel.sso.po.SSOBasicEntity;

public interface SSOService {

	SSOBasicEntity login(String account, String password) ;

	void updateLastLoginDate(String id) throws Exception ;

	Cookie buildRememberMeCookie(String account, String signPassword) ;

	void doRegister(String account, String password) throws Exception  ;

	boolean isPasswordSame(String account, String password) ;

	void doRegisterByGet(String account, String password,String email,String realName,String province,String city,String county,String area) throws Exception;

	/**
	 * 使用获得的微信用户信息注册并登陆会员
	 * @param openid
	 * @param nickname
	 * @param province
	 * @param city
	 * @param country
	 * @param headimgurl
	 * @throws Exception
	 */
	SSOBasicEntity doRegisterAndLoginByWx(String openId, String nickName, String headImgUrl) throws Exception;

	/**
	 * 将微信会员与网站用户进行绑定
	 * @param openId
	 * @param memberId
	 */
	void doBindOpenIdToMember(String openId, String memberId) throws Exception ;

	SSOBasicEntity doLoginByMemberId(String memberId) ;

	SSOBasicEntity selectMemberByAccountAndPassword(String account, String password) ;

	/**
     * 通过openId查询会员信息
     * @param String
     * @return
     */
    SSOBasicEntity selectMemberByOpenId(String openId) ;

}
