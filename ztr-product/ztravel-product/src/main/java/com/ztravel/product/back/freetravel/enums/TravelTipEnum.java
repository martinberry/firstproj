package com.ztravel.product.back.freetravel.enums;

public enum TravelTipEnum {
	/**
	 * 	气候（气候、衣着）
		消费（货比、当地消费、小费……）
		通信（网络、电话、常用电话）
		当地风俗（风俗须知、节日提醒）
		其他（针对产品本身的特殊提醒，比如参观时间这种）
	 */
	WEATHER("气候"),
	EXPEND("消费"),
	COMMUNICATION("通信"),
	CUSTOM("当地风俗"),
	OTHER("其他");

	private final String desc;

	TravelTipEnum(String desc){
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
