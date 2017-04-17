package com.ztravel.product.dao;

import java.util.List;
import java.util.Map;

import com.ztravel.common.enums.ActivityStatus;
import com.ztravel.common.enums.CouponStatus;
import com.ztravel.product.back.activity.entity.Activity;
import com.ztravel.product.back.activity.entity.Coupon;

public interface IActivityDao {

	/**
	 *根据活动ID查询活动
	 * */
	Activity getActivityById(String id);
	/**
	 *根据活动号查询活动
	 * */
	Activity getActivityByCode(String code);
	/**
	 *更新活动状态
	 * */
	Boolean updateActivityStatus(String id , ActivityStatus newStatus);

	Boolean updateActivityWithOutCoupons(Activity activity);
	/**
	 *更新活动的代金券列表
	 * */
	Boolean updateCoupons(String id, Map<String,Coupon> couponsMap,String couponNameString);
	/**
	 *更新活动:如果活动存在,则更新;否则新建一个活动
	 * */
	String updateActivity(Activity activity);
	/**
	 *根据条件查询活动
	 * */
	List<Activity> findByConditions(Map<String, String> pConditions, Integer pPage, Integer pPageSize);
	/**
	 *获取表中所有活动总数
	 * */
	long getCount();
	/**
	 *获取符合查询条件的活动总数
	 * */
	Long getCountByConditions(Map<String, String> pConditions);
	/**
	 *删除活动
	 * */
	Boolean deleteActivity(String id);
	/**
	 *
	 * */
	Coupon getCouponById(String activityId,String couponItemId) throws Exception;

	Boolean deleteCouponById(String activityId,String couponItemId) throws Exception;

	Integer updateCoupon(String activityId,Coupon coupon) throws Exception;

	Integer updateCouponStauts(String activityId,String couponId,CouponStatus status) throws Exception;

	/**
	 * 更新代金券已发放数量
	 * */
	Boolean updateCouponUsedNum(String activityId, String couponId,Integer newSendNum)throws Exception;

	Integer updateCouponNum(String activityId, String couponId,Integer totalNum,Integer unit)throws Exception;

	Map<String,Coupon> getCoupons(String activityId) throws Exception;

	/**
	 * 查询所有有效的系统送券活动
	 * */
	List<Activity> getAllEffecSysActivity(String memberId) throws Exception;
    /**
     *根据条件查询活动
     * */
    List<Activity> findByConditions(Map<String, String> pConditions);
}
