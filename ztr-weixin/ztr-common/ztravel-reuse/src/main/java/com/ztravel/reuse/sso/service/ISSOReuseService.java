package com.ztravel.reuse.sso.service;

import javax.servlet.http.Cookie;

import com.ztravel.sso.po.SSOBasicEntity;

public interface ISSOReuseService {
	SSOBasicEntity login(String account, String password) ;

	void updateLastLoginDate(String id) throws Exception ;

	Cookie buildRememberMeCookie(String account, String signPassword) ;
	
	SSOBasicEntity doLoginByMemberId(String memberId);
}
