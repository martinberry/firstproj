package com.ztravel.common.enums;

public enum ActivityStatus {
	DRAFT("草稿"),
	EFFECTIVE("已生效"),
	TERMINATED("已终止"),
	EDN("已结束");
	private final String desc;

	ActivityStatus(String desc) {
		this.desc = desc;
	}
	public String getDesc(){
		return desc;
	}
}
