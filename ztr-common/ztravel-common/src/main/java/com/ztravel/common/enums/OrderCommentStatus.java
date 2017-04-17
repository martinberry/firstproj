package com.ztravel.common.enums;

public enum OrderCommentStatus {

	COMMITED("commited","待审核"),
	PUBLISHED("published","已通过"),
	RETURN("return","未通过"),
	ALL("all","全部");

	private final String value;

	private final String desc;

	OrderCommentStatus(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public String getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}



}
