package com.ztravel.sso.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.sso.dao.SSODao;
import com.ztravel.sso.po.SSOBasicEntity;

@Repository
public class SSODaoImpl extends BaseDaoImpl<SSOBasicEntity> implements SSODao{

	private static final String SELECT_MEMBER_BY_EMAIL_PWD = ".selectByEmailPassword" ;

	private static final String SELECT_MEMBER_BY_MOBILE_PWD = ".selectByMobilePassword" ;

    private static final String SELECT_MEMBER_BY_OPENID = ".selectByOpenId" ;

	private static final String UPDATE_MEMBER_LAST_LOGIN_DATE = ".updateMemberLastLoginDate" ;

    private static final String UPDATE_MEMBER_OPENID = ".updateMemberOpenId" ;

	@Override
	public SSOBasicEntity selectByMobilePassword(@SuppressWarnings("rawtypes") Map params) {
		return session.selectOne(nameSpace + SELECT_MEMBER_BY_MOBILE_PWD, params) ;
	}

	@Override
	public SSOBasicEntity selectByEmailPassword(@SuppressWarnings("rawtypes") Map params) {
		return session.selectOne(nameSpace + SELECT_MEMBER_BY_EMAIL_PWD, params) ;
	}

	@Override
	public Integer updateMemberLastLoginDate(String id, DateTime now) {
		Map<String, Object> params = new HashMap<String, Object>() ;
		params.put("id", id) ;
		params.put("lastLoginDate", now) ;
		return session.update(nameSpace + UPDATE_MEMBER_LAST_LOGIN_DATE, params);
	}

    @Override
    public SSOBasicEntity selectByOpenId(String openId) {
        return session.selectOne(nameSpace + SELECT_MEMBER_BY_OPENID, openId) ;
    }

    @Override
    public Integer updateMemberOpenIdById(String id, String openId) {
        Map<String, Object> params = new HashMap<String, Object>() ;
        params.put("id", id) ;
        params.put("openId", openId) ;
        return session.update(nameSpace + UPDATE_MEMBER_OPENID, params);
    }
}
