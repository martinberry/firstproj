package com.ztravel.sso.client.service;

import javax.servlet.http.Cookie;

import com.ztravel.sso.client.entity.SSOClientEntity;
import com.ztravel.sso.po.SSOBasicEntity;

public interface SSOClientService {

	SSOBasicEntity login(String account, String password) ;

	void updateLastLoginDate(String id) throws Exception ;

	Cookie buildRememberMeCookie(String account, String signPassword) ;

	void doRegisterAndSendPassword(SSOClientEntity entity, boolean autoLogin) throws Exception ;

	/**
     * 通过openId查询会员信息
	 * @param openId
	 * @return
	 */
    SSOBasicEntity selectMemberByOpenId(String openId) ;

    /**
     * 使用memberId登陆会员
     * @param memberId
     * @return
     */
    SSOBasicEntity doLoginByMemberId(String memberId);

    /**
     * 使用个人微信信息注册会员
     * @param openId
     * @return
     */
    SSOBasicEntity doRegisterByWx(String openId, String nickName, String headImgUrl) throws Exception;

    /**
     * 通过memberId查询会员信息
     * @param memberId
     * @return
     */
    SSOBasicEntity selectMemberByMemberId(String memberId);

    boolean unbindOpenIdFromMember(String memberId) throws Exception;

    boolean bindOpenIdToMember(String memberId, String openId) throws Exception;

}
