package com.ztravel.common.enums;

public enum ActivityType {

	SYSTEMSEND("系统送券活动"),

	CUSTOMERGET("用户领券活动");

	private final String desc;

	ActivityType(String desc) {
		this.desc = desc;
	}
	public String getDesc() {
		return desc;
	}
}
