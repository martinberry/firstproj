package com.ztravel.common.enums;


/**
 * @author wanhaofan
 * 自由行产品附加条款
 * */
public enum AdditionalRule {
	FEE_INCLUDE("fee_include", "费用包含") ,
	FEE_NOT_INCLUDE("fee_not_include", "费用不含") ,
	GIFT_ITEMS("gift_items", "真旅行赠送项目") ,
	VISA("visa", "签证须知") ,
	REFUND_POLICY("refund_policy","退改政策"),
	BOOKING("booking", "预订须知") ,
	FEE_DETAIL("fee_detail","费用说明"),
    FEATURES("features","产品介绍"),
    INTRODUCTIONS("introductions","使用说明");

	private final String name ;

	private final String desc ;


	AdditionalRule(String name, String desc){
		this.name = name ;
		this.desc = desc ;
	}


	public String getName() {
		return name;
	}


	public String getDesc() {
		return desc;
	}

}
