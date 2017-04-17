package com.ztravel.member.client.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ztravel.common.enums.NoticeType;
import com.ztravel.common.payment.CouponGrantBean;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.common.util.SystemNoticeContentUtil;
import com.ztravel.member.po.MemberActivityEntity;
import com.ztravel.member.po.MemberTimeEntity;
import com.ztravel.member.client.service.ICouponClientService;
import com.ztravel.member.client.service.IMemberClientService;
import com.ztravel.member.client.service.ISystemNoticeClientService;
import com.ztravel.member.client.service.MemberActivityService;
import com.ztravel.payment.service.ICouponService;
import com.ztravel.product.client.entity.ActivityClientEntity;
import com.ztravel.product.client.entity.CouponClientEntity;
import com.ztravel.product.client.service.IActivityClientService;

/**
 * @author wanhaofan
 * */
@Service
public class CouponClientService implements ICouponClientService{

	private final static Logger logger = LoggerFactory.getLogger(CouponClientService.class);

	@Resource
	private IMemberClientService memberClientService ;

	@Resource
	private IActivityClientService activityClientService ;

	@Resource
	private MemberActivityService memberActivityService ;

	@Resource
	private ICouponService couponService ;

	@Resource
	private ISystemNoticeClientService systemNoticeClientService ;

	@Override
	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void grantCoupon(String memberId) {
		try{
			logger.info("取得匹配活动,memberId: {}", memberId);
			MemberTimeEntity timeEntity = memberClientService.getMemberRegisterTimeById(memberId) ;
			List<ActivityClientEntity> activityClientEntities = activityClientService.getAllCouponsByMemberId(memberId, timeEntity.getCreateTime()) ;
			logger.info("取得匹配活动结果: {}", "成功");

			logger.info("发放活动,memberId: {}", memberId);
			DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
			for(ActivityClientEntity activity : activityClientEntities){
				CouponGrantBean bean = new CouponGrantBean() ;
				bean.setActivityId(activity.getActivityId());
				bean.setMemberId(memberId);
				List<CouponClientEntity> coupons = activity.getCoupons() ;
				if(coupons != null && coupons.size() > 0){
					for(CouponClientEntity coupon : coupons){
						bean.setAmount(coupon.getAmount());
						bean.setDescription(coupon.getDescription());
						bean.setName(coupon.getName());
						bean.setCouponCode(coupon.getCouponId());
						bean.setValidDateFrom(DateTime.parse(coupon.getValidTimeFrom(), format));
						bean.setValidDateTo(DateTime.parse(coupon.getValidTimeTo(), format));
						int count = memberActivityService.count(memberId, activity.getActivityId(), coupon.getCouponId()) ;
						for(int i=0;i<coupon.getUnit()-count;i++){
							CommonResponse response = couponService.grant(bean) ;
							if(response.isSuccess()){
								MemberActivityEntity entity = new MemberActivityEntity() ;
								entity.setMemberId(memberId);
								entity.setCouponId(coupon.getCouponId());
								entity.setActivityId(activity.getActivityId());
								memberActivityService.insert(entity);
								systemNoticeClientService.add(memberId, NoticeType.COUPONTYPE, SystemNoticeContentUtil.getCpContent(coupon.getName())) ;
								activityClientService.updateCouponSendNum(activity.getActivityId(), coupon.getCouponId()) ;
							}
							logger.info("发放券{}结果: {}", coupon.getCouponId(), response.isSuccess() ? "成功" : response.getErrMsg());
						}
					}
				}else{
					logger.info("activityid: {} empty coupons", activity.getActivityId());
				}
			}
		}catch(Exception e){
			logger.error("login event error..", e) ;
		}
	}

}
