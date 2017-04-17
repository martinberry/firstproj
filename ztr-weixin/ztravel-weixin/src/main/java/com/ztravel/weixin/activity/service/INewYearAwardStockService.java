package com.ztravel.weixin.activity.service;

import java.util.Map;

import com.ztravel.common.entity.AwardWeight;
import com.ztravel.weixin.activity.vo.ActivityAwardOptionsVo;

public interface INewYearAwardStockService {

    public boolean achieveAwardByAwardCode(String awardCode) throws Exception;

    public Map<String, ActivityAwardOptionsVo> getActivityAwardOptions();

    public void buildAwardWeight(AwardWeight awardWeight);

    public boolean updateStockByAwardCodeAndChangeNum(String awardCode, Integer changeNum);

}
