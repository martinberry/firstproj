package com.ztravel.weixin.activity.dao;

import java.util.List;
import java.util.Map;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.weixin.activity.entity.NewYearUserAwardRecord;
import com.ztravel.weixin.activity.vo.AwardRecordVo;
import com.ztravel.weixin.activity.vo.GameRecordVo;

public interface INewYearUserAwardRecordDao extends BaseDao<NewYearUserAwardRecord> {

	public Integer countAchieveAwardRecordByOpenId(String openId);

	public List<AwardRecordVo> searchAwardRecordList();

	public List<GameRecordVo> searchAwardRecordListByMap(Map<String, Object> searchParams);

	public Integer countAwardRecordListByMap(Map<String, Object> searchParams);

}
