package com.ztravel.weixin.timming.dao;

import java.util.List;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.weixin.activity.entity.NewYearUserAwardRecord;
import com.ztravel.weixin.activity.vo.AwardRecordVo;

public interface NewYearTimmingUserAwardRecordDao extends BaseDao<NewYearUserAwardRecord>{
	/**
	 * 将发放礼物状态改为released
	 * */
	
	
	
	
	
    List<AwardRecordVo> searchAwardRecordList() ;
}
