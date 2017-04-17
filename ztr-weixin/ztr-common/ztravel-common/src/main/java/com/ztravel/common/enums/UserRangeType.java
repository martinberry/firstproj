package com.ztravel.common.enums;

public enum UserRangeType {

	CURRENTALL("现有用户"),

	NEWUSER("活动期间新增用户"),

	NEWANDSHARED("活动期内新增且参与真旅行分享计划"),

	DN("大宁活动");

	private String desc;

	UserRangeType(String desc) {
		this.desc = desc;
	}

	public String getDesc(){
		return desc;
	}
}
