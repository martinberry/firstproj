package com.ztravel.weixin.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.weixin.entity.WxUserEntity;
import com.ztravel.weixin.user.dao.IWxUserDao;


@Repository
public class WxUserDaoImpl extends BaseDaoImpl<WxUserEntity>  implements IWxUserDao {
	
	@Override
	public void lock(String openId){
		session.selectOne(nameSpace + ".lock", openId) ;
	}
}
