package com.ztravel.weixin.timming.dao.impl;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.weixin.activity.entity.NewYearAwardStock;
import com.ztravel.weixin.timming.dao.NewYearTimmingStockDao;


@Repository
public class NewYearTimmingStockDaoImpl extends BaseDaoImpl<NewYearAwardStock> implements NewYearTimmingStockDao {
    public static final String UPDATE_STOCK_PLUS_ONE = ".updateStockPlusOne";

    @Override
    public Integer updateAwardStockPlusOne(String awardCode) {
        return session.update(nameSpace + UPDATE_STOCK_PLUS_ONE, awardCode);
    }

}
