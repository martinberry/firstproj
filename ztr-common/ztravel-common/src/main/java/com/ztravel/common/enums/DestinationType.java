package com.ztravel.common.enums;

/**
 * 目的地类型，国际或国内
 * @author xujunhui
 *
 */
public enum DestinationType {
	DOMESTIC("domestic", "国内") ,
	INTERNATIONAL("international", "国际");

	private final String name ;
	private final String desc ;

	DestinationType(String name, String desc){
		this.name = name;
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

}
