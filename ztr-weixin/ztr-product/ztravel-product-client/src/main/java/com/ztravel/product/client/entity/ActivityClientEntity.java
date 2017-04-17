package com.ztravel.product.client.entity;

import java.util.List;

public class ActivityClientEntity {

	private String activityId;

	private List<CouponClientEntity>coupons;

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public List<CouponClientEntity> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<CouponClientEntity> coupons) {
		this.coupons = coupons;
	}


}
