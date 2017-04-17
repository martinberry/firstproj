package com.ztravel.weixin.activity.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.weixin.activity.dao.INewYearUserAwardRecordDao;
import com.ztravel.weixin.activity.entity.NewYearUserAwardRecord;
import com.ztravel.weixin.activity.vo.AwardRecordVo;
import com.ztravel.weixin.activity.vo.GameRecordVo;

@Repository
public class NewYearUserAwardRecordDaoImpl extends BaseDaoImpl<NewYearUserAwardRecord> implements INewYearUserAwardRecordDao {

    public static final String COUNT_ACHIEVE_AWARD_RECORD_BY_OPEN_ID = ".countAchieveAwardRecordByOpenId";

    public static final String SEARCH_AWARD_RECORD_LIST = ".searchAwardRecordList";

    public static final String SEARCH_AWARD_RECORD_LIST_BY_MAP = ".searchAwardRecordListByMap";

    public static final String COUNT_AWARD_RECORD_LIST_BY_MAP = ".countAwardRecordListByMap";


    @Override
    public Integer countAchieveAwardRecordByOpenId(String openId) {
        return session.selectOne(nameSpace + COUNT_ACHIEVE_AWARD_RECORD_BY_OPEN_ID, openId);
    }

    @Override
    public List<AwardRecordVo> searchAwardRecordList() {
        return session.selectList(nameSpace + SEARCH_AWARD_RECORD_LIST);
    }

	public List<GameRecordVo> searchAwardRecordListByMap(Map<String, Object> searchParams) {
		return session.selectList(nameSpace + SEARCH_AWARD_RECORD_LIST_BY_MAP, searchParams);
	}

	@Override
	public Integer countAwardRecordListByMap(Map<String, Object> searchParams) {
		return session.selectOne(nameSpace + COUNT_AWARD_RECORD_LIST_BY_MAP, searchParams);
	}

}
