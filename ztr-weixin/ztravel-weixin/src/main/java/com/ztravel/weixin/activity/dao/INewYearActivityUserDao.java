package com.ztravel.weixin.activity.dao;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.weixin.activity.entity.NewYearActivityUser;

public interface INewYearActivityUserDao extends BaseDao<NewYearActivityUser> {

    public void addUserGameCount(String openId);

}
