package com.ztravel.member.enums;


/**
 * @author wanhaofan
 * */
public enum TravelStatus {

	REGISTER("已注册"),
	TRAVEL("已出游");

	private final String value ;

	private TravelStatus(String value){
		this.value = value ;
	}

	public String getValue() {
		return value;
	}
}
