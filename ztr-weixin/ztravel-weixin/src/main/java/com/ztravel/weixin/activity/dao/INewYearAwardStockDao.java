package com.ztravel.weixin.activity.dao;

import java.util.Map;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.weixin.activity.entity.NewYearAwardStock;

public interface INewYearAwardStockDao extends BaseDao<NewYearAwardStock> {

    public Integer selectLeftAmountByAwardCode(String awardCode);

    public Integer updateAwardStock(Map<String, Object> params);

    public Integer updateAwardStockMinusOne(String awardCode);

    public Integer updateAwardStockPlusOne(String awardCode);

    public Integer updateStockByChangeNum(Map<String, Object> params);

}
