package com.ztravel.weixin.activity.service;

import java.util.List;

import com.travelzen.framework.core.wrapper.Pagination;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.weixin.activity.entity.GameRecordQueryBean;
import com.ztravel.weixin.activity.entity.NewYearUserAwardRecord;
import com.ztravel.weixin.activity.enums.ActivityRecordStatus;
import com.ztravel.weixin.activity.vo.ActivityUserVo;
import com.ztravel.weixin.activity.vo.AwardRecordVo;
import com.ztravel.weixin.activity.vo.GameRecordVo;


public interface INewYearUserAwardRecordService {

    /**
     * 根据openId随机生成获取奖品记录
     * @param openId
     * @return
     */
    public AjaxResponse randomCreateAwardRecordByOpenId(String openId);

    /**
     * 生成获取奖品记录
     * @param openId
     * @param awardCode
     * @return
     */
    void createAwardRecordByOpenIdAndAwardCode(NewYearUserAwardRecord userAwardRecord) throws Exception;


    public ActivityRecordStatus selectAwardStatus(ActivityUserVo activityuservo);

    public List<AwardRecordVo> searchAwardRecordList();

    public Pagination<GameRecordVo> searchAwardRecordListByMap(GameRecordQueryBean queryBean) throws Exception;

    public NewYearUserAwardRecord selectNewYearUserAwardRecordByRecordId(String recordId);

}
