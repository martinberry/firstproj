package com.ztravel.common.enums;

public enum CouponStatus {
	NOSENDDING("未发放"),
	FINISHED("已发送"),
	SENDDING("发放中"),
	TERMINATED("发放终止"),
	EXPIRED("已过期");

	private final String desc;
	CouponStatus(String desc) {
		this.desc = desc;
	}
	public String getDesc(){
		return desc;
	}
}
