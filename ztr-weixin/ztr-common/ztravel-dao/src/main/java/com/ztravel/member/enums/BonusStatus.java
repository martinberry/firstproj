package com.ztravel.member.enums;

/**
 * @author wanhaofan
 * */
public enum BonusStatus {

	ISSUED("已发放"),
	RESERVE("预备发放");

	private final String value ;

	private BonusStatus(String value){
		this.value = value ;
	}

	public String getValue() {
		return value;
	}
}
