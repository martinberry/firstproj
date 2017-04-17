package com.ztravel.product.back.activity.service;

import java.util.List;
import java.util.Map;

import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.product.back.activity.entity.Activity;
import com.ztravel.product.back.activity.entity.Coupon;
import com.ztravel.product.back.activity.vo.ActivityVo;

public interface IActivityService {

	Map<String,Object> searchActivities(Map<String,String> params,Integer pageNo,Integer pageSize) throws Exception;

	AjaxResponse toEffect(ActivityVo activityVo) throws Exception;

	AjaxResponse toTerminate(String id) throws Exception;

	String saveActivity(ActivityVo activityVo) throws Exception;

	ActivityVo convert2Vo(Activity activity)throws Exception;

	Activity getActivityById(String activityId) throws Exception;

	AjaxResponse addCoupons(String activityId,List<Coupon> coupons) throws Exception;

	AjaxResponse removeCoupon(String couponItemId,String activityId) throws Exception;

	Coupon getCoupon(String activityId,String couponItemId) throws Exception;

	AjaxResponse updateCoupon(String activityId,Coupon coupon) throws Exception;

	AjaxResponse teminateCoupon(String activityId,String couponItemId) throws Exception;

	AjaxResponse updateCouponNum(String activityId,String couponItemId,Integer totalNum,Integer unit) throws Exception;

	Map<String,Coupon> getCouponsByActivityId(String activityId) throws Exception;

	AjaxResponse sendCoupon(String activityId) throws Exception;

	AjaxResponse checkMobiles(String mobiles) throws Exception;

}
