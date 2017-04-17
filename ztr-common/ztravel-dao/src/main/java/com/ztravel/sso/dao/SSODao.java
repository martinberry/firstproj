package com.ztravel.sso.dao;

import java.util.Map;

import org.joda.time.DateTime;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.sso.po.SSOBasicEntity;

public interface SSODao extends BaseDao<SSOBasicEntity> {
	SSOBasicEntity selectByMobilePassword(@SuppressWarnings("rawtypes") Map params) ;

	SSOBasicEntity selectByEmailPassword(@SuppressWarnings("rawtypes") Map params) ;

	Integer updateMemberLastLoginDate(String id, DateTime now) ;

	/**
	 * 通过openId查询会员信息
	 * @param String
	 * @return
	 */
    SSOBasicEntity selectByOpenId(String openId) ;

    /**
     * 更新用户的memberId：绑定或解绑微信用户
     * @param id
     * @return
     */
    Integer updateMemberOpenIdById(String id, String openId);

}
