package com.ztravel.weixin.activity.service;

import com.ztravel.weixin.activity.vo.ActivityUserVo;

public interface INewYearActivityUserService {

    public boolean validateAndSaveActivityUser(ActivityUserVo activityUserVo);

    public void saveGuestInfo(ActivityUserVo activityUserVo) throws Exception;

    public void addUserGameCount(String openId);

}
