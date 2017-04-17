package com.ztravel.weixin.activity.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.travelzen.framework.core.util.StringUtil;
import com.travelzen.framework.logger.core.TZMarkers;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.weixin.activity.dao.INewYearActivityUserDao;
import com.ztravel.weixin.activity.dao.INewYearUserAwardRecordDao;
import com.ztravel.weixin.activity.entity.NewYearActivityUser;
import com.ztravel.weixin.activity.entity.NewYearUserAwardRecord;
import com.ztravel.weixin.activity.enums.ActivityRecordStatus;
import com.ztravel.weixin.activity.service.INewYearActivityUserService;
import com.ztravel.weixin.activity.vo.ActivityUserVo;

@Service
public class NewYearActivityUserServiceImpl implements INewYearActivityUserService {

    private static Logger logger = RequestIdentityLogger.getLogger(NewYearActivityUserServiceImpl.class);

    @Resource
    private INewYearActivityUserDao newYearActivityUserDao;
    @Resource
    private INewYearUserAwardRecordDao newYearUserAwardRecordDao;

    @Override
    @Transactional(rollbackFor=RuntimeException.class, propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED)
    public boolean validateAndSaveActivityUser(ActivityUserVo activityUserVo) {
        //如果活动用户或用户openId不存，验证失败
        if (activityUserVo == null || StringUtil.isEmpty(activityUserVo.getOpenId())) {
            return false;
        }

        NewYearActivityUser newYearActivityUser = newYearActivityUserDao.selectById(activityUserVo.getOpenId());
        if (newYearActivityUser == null) {
            newYearActivityUser = createNewYearActivityUserByActivityUserVo(activityUserVo);
            try {
                newYearActivityUserDao.insert(newYearActivityUser);
            } catch (Exception e) {
                logger.error(TZMarkers.p2, "插入元旦活动参与用户信息异常", e);
                return false;
            }
        } else {
            //游戏次数加1
            addUserGameCount(activityUserVo.getOpenId());
        }

        return true;
    }

	@Override
	@Transactional(rollbackFor=RuntimeException.class, propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED)
	public void saveGuestInfo(ActivityUserVo activityUserVo) throws Exception{
		NewYearActivityUser newYearActivityUser=new NewYearActivityUser();
		newYearActivityUser.setOpenId(activityUserVo.getOpenId());
		newYearActivityUser.setRealName(activityUserVo.getRealName());
		newYearActivityUser.setMobile(activityUserVo.getMobile());
		newYearActivityUser.setDetailAddress(activityUserVo.getDetailAddress());
		newYearActivityUserDao.update(newYearActivityUser);
		NewYearUserAwardRecord awardrecord=new NewYearUserAwardRecord();
		awardrecord.setStatus(ActivityRecordStatus.FINISHED);
		awardrecord.setRecordId(activityUserVo.getRecordId());
		newYearUserAwardRecordDao.update(awardrecord);

	}

    private NewYearActivityUser createNewYearActivityUserByActivityUserVo(ActivityUserVo activityUserVo) {
        NewYearActivityUser newYearActivityUser = new NewYearActivityUser();
        newYearActivityUser.setOpenId(activityUserVo.getOpenId());
        newYearActivityUser.setHeadImageUrl(activityUserVo.getHeadImageUrl());
        newYearActivityUser.setNickName(activityUserVo.getNickName());
        newYearActivityUser.setGameCount(1);
        return newYearActivityUser;
    }

    @Override
    public void addUserGameCount(String openId) {
        newYearActivityUserDao.addUserGameCount(openId);
    }

}
