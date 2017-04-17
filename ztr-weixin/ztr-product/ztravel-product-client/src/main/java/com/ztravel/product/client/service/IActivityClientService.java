package com.ztravel.product.client.service;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.ztravel.common.entity.CouponBookVo;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.product.client.entity.ActivityClientEntity;
import com.ztravel.product.client.entity.CouponClientEntity;

public interface IActivityClientService {

	CouponClientEntity getCouponById(String activityId,String couponId);

	List<ActivityClientEntity> getAllCouponsByMemberId(String memberId,DateTime registerTime);

	Boolean updateCouponSendNum(String activityId,String couponId);

    Boolean updateCouponNum(String activityId,String couponId, Integer num);

	CouponClientEntity getCouponByIdWithProductName(String activityId, String couponId);

	 DateTime getActivityEndTimeById(String activityId);

	String  getActivityStatusById(String activityId) throws Exception;

	List<CouponBookVo> getDNActivityList(Map<String,String> params);

	CommonResponse validateDNActivityAvailable(CouponBookVo couponBookVo);

}
