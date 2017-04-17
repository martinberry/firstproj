package com.ztravel.weixin.activity.dao.impl;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.weixin.activity.dao.INewYearActivityUserDao;
import com.ztravel.weixin.activity.entity.NewYearActivityUser;

@Repository
public class NewYearActivityUserDaoImpl extends BaseDaoImpl<NewYearActivityUser> implements INewYearActivityUserDao {

    public static final String UPDATE_GAME_COUNT_PLUS_ONE = ".updateGameCountPlusOne";

    @Override
    public void addUserGameCount(String openId) {
        session.update(nameSpace + UPDATE_GAME_COUNT_PLUS_ONE, openId);
    }

}
