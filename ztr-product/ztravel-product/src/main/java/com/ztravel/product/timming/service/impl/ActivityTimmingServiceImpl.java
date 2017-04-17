package com.ztravel.product.timming.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.enums.ActivityStatus;
import com.ztravel.common.enums.CouponStatus;
import com.ztravel.common.enums.UserRangeType;
import com.ztravel.product.back.activity.entity.Activity;
import com.ztravel.product.back.activity.entity.Coupon;
import com.ztravel.product.timming.dao.impl.ActivityTimmingDaoImpl;
import com.ztravel.product.timming.service.IActivityTimmingService;
import com.ztravel.product.timming.service.IVoucherTimmingService;

@Service
public class ActivityTimmingServiceImpl implements IActivityTimmingService{

	@Resource
	private ActivityTimmingDaoImpl activityTimmingDaoImpl;

	@Resource
	private IVoucherTimmingService voucherTimmingService ;

	private static final Logger LOGGER  = RequestIdentityLogger.getLogger(ActivityTimmingServiceImpl.class);

	@Override
	public void setActivityAutoExpired() throws Exception {
		List<Activity> activityList = activityTimmingDaoImpl.getAllActivity();
		Integer totalNum = 0;
		if(!CollectionUtils.isEmpty(activityList)){
			for(Activity activity : activityList){
				long nowTime = new DateTime().getMillis();
				if(activity.getEndTime() != null && activity.getEndTime().getMillis() < nowTime){
					try {
						activityTimmingDaoImpl.updateActivityStatus(activity.getId().toString(), ActivityStatus.EDN);
						totalNum ++ ;
						LOGGER.info("====活动[{}]已刷为[结束]状态====",activity.getCode());
					} catch (Exception e) {
						LOGGER.info("====定时器更新活动[{}]为[结束]状态操作失败====",activity.getCode());
					}
				}
			}
			LOGGER.info("====[{}]个活动被刷为[结束]状态====",totalNum);
		}

	}

	@Override
	public void setCouponAutoExpired() throws Exception {
		List<Activity> activityList = activityTimmingDaoImpl.getAllActivity();
		Integer totalNum = 0;
		if(!CollectionUtils.isEmpty(activityList)){
			for(Activity activity : activityList){
				Map<String,Coupon> coupons = activity.getCoupons();
				if(!CollectionUtils.isEmpty(coupons)){
					long nowTime  = new DateTime().getMillis();
					for(Entry<String, Coupon> entry : coupons.entrySet()){
						Coupon coupon = entry.getValue();
						try {
							if(coupon.getValidTimeTo().getMillis() < nowTime){
								Boolean result = activityTimmingDaoImpl.updateCouponStauts(activity.getId().toString(), coupon.getCouponId(), CouponStatus.EXPIRED);

								if(activity.getUserRangType() == UserRangeType.DN){
									boolean voucherResult = voucherTimmingService.setVoucherExpired(coupon.getCouponId().trim()) ;
									LOGGER.info("====voucher已刷[过期]状态====response:::{}",voucherResult);
								}

								if(result){
									totalNum ++ ;
									LOGGER.info("====活动[{}]的券[{}]券已刷[过期]状态====",activity.getCode(),coupon.getCouponId());
								}
							}
						}catch(Exception e){
							LOGGER.error("====定时器更新活动[{}]的券[{}]为[过期]状态操作失败====",activity.getCode(),coupon.getCouponId(),e);
						}
					}
				}
			}
			LOGGER.info("====[{}]张券被刷为[过期]状态====",totalNum);
		}
	}

	/**
	 * 对新增用户的活动,如果券的状态已变为发放中,且当前时间已超过活动有效截止时间,则代金券更新为已发放状态
	 * */
	@Override
	public void setSendingToFinished() throws Exception {
		List<Activity> activityList = activityTimmingDaoImpl.getAllEndActivity();
		Integer totalNum = 0;
		if(!CollectionUtils.isEmpty(activityList)){
			for(Activity activity : activityList){
				if(activity.getUserRangType() != null && (activity.getUserRangType().name().equals(UserRangeType.NEWUSER.name()) || activity.getUserRangType().name().equals(UserRangeType.NEWANDSHARED.name()))){
					long activityEndTime = activity.getEndTime().getMillis();//活动有效期的截止时间
					Map<String,Coupon> coupons = activity.getCoupons();
					if(!CollectionUtils.isEmpty(coupons)){
						long nowTime  = new DateTime().getMillis();
						for(Entry<String, Coupon> entry : coupons.entrySet()){
							Coupon coupon = entry.getValue();
							if(activityEndTime < nowTime && coupon.getStatus().name().equals(CouponStatus.SENDDING.name())){
								try {
									Boolean result = activityTimmingDaoImpl.updateCouponStauts(activity.getId().toString(), coupon.getCouponId(), CouponStatus.FINISHED);
									if(result){
										totalNum ++ ;
										LOGGER.info("====活动[{}]的券[{}]券已刷为[已发放]状态====",activity.getCode(),coupon.getCouponId());
									}
								}catch(Exception e){
									LOGGER.error("====定时器更新活动[{}]的券[{}]为[已发放]状态操作失败====",activity.getCode(),coupon.getCouponId(),e);
								}
							}
						}
					}
				}
			}
			LOGGER.info("====[{}]张券被刷为[已发放]状态====",totalNum);
		}
	}

}
