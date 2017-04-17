package com.ztravel.product.timming.dao;

import java.util.List;

import com.ztravel.common.enums.ActivityStatus;
import com.ztravel.common.enums.CouponStatus;
import com.ztravel.product.back.activity.entity.Activity;


public interface IActivityTimmingDao {

	List<Activity> getAllActivity() throws Exception;

	List<Activity> getAllEndActivity() throws Exception;

	Boolean updateActivityStatus(String id, ActivityStatus newStatus);

	Boolean updateCouponStauts(String activityId, String couponId,CouponStatus status) throws Exception;



}
