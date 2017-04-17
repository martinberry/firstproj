package com.ztravel.common.enums;

public enum GrantType {

	BATCHCONFIG("按条件批量设置"),

	MANUALADD("手动添加用户");

	private final String desc;

	GrantType(String desc) {
		this.desc = desc;
	}

	public String getDesc(){
		return desc;
	}
}
