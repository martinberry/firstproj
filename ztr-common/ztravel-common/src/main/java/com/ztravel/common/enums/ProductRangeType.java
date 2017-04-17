package com.ztravel.common.enums;

public enum ProductRangeType {
	ALLPRODUCTS("所有产品"),
	MANUALADD("手动添加产品");

	private final String  desc;
	ProductRangeType(String desc) {
		this.desc = desc;
	}

	public String getDesc(){
		return desc;
	}
}
