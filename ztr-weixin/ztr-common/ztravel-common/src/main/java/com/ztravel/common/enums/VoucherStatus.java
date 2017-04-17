package com.ztravel.common.enums;

public enum VoucherStatus {
	 INIT("新建"),RECIEVED("已领取"), USED("已使用"), EXPIRED("已过期"),LOCK("锁定"),INVALID("已失效");

	private final String desc;
	VoucherStatus(String desc) {
		this.desc = desc;
	}
	public String getDesc(){
		return desc;
	}
}
