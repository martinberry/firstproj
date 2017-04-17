package com.ztravel.weixin.user.dao;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.weixin.entity.WxUserEntity;


public interface IWxUserDao extends BaseDao<WxUserEntity> {

	void lock(String openId);

}
