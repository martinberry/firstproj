package com.ztravel.weixin.activity.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.weixin.activity.dao.INewYearAwardStockDao;
import com.ztravel.weixin.activity.entity.NewYearAwardStock;

@Repository
public class NewYearAwardStockDaoImpl extends BaseDaoImpl<NewYearAwardStock> implements INewYearAwardStockDao {

    public static final String SELECT_LEFT_AMOUNT_BY_AWARD_CODE = ".selectLeftAmountByAwardCode";

    public static final String UPDATE_STOCK = ".updateStock";

    public static final String UPDATE_STOCK_MINUS_ONE = ".updateStockMinusOne";

    public static final String UPDATE_STOCK_PLUS_ONE = ".updateStockPlusOne";

    public static final String UPDATE_STOCK_BY_CHANGE_NUM = ".updateStockByChangeNum";

    @Override
    public Integer selectLeftAmountByAwardCode(String awardCode) {
        return session.selectOne(nameSpace + SELECT_LEFT_AMOUNT_BY_AWARD_CODE, awardCode);
    }

    @Override
    public Integer updateAwardStock(Map<String, Object> params) {
        return session.update(nameSpace + UPDATE_STOCK, params);
    }

    @Override
    public Integer updateAwardStockMinusOne(String awardCode) {
        return session.update(nameSpace + UPDATE_STOCK_MINUS_ONE, awardCode);
    }

    @Override
    public Integer updateAwardStockPlusOne(String awardCode) {
        return session.update(nameSpace + UPDATE_STOCK_PLUS_ONE, awardCode);
    }

    @Override
    public Integer updateStockByChangeNum(Map<String, Object> params) {
        return session.update(nameSpace + UPDATE_STOCK_BY_CHANGE_NUM, params);
    }

}
