package com.ztravel.weixin.timming.dao.impl;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.weixin.activity.entity.NewYearUserAwardRecord;
import com.ztravel.weixin.activity.vo.AwardRecordVo;
import com.ztravel.weixin.timming.dao.NewYearTimmingUserAwardRecordDao;


@Repository
public class NewYearTimmingUserAwardRecordDaoImpl extends BaseDaoImpl<NewYearUserAwardRecord> implements NewYearTimmingUserAwardRecordDao{
	
	public static final String SEARCH_AWARD_RECORD_LIST = ".searchAwardRecordList";
	
	@Override
    public List<AwardRecordVo> searchAwardRecordList() {
        return session.selectList(nameSpace + SEARCH_AWARD_RECORD_LIST);
    }

}
