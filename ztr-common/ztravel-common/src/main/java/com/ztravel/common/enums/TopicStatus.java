package com.ztravel.common.enums;

public enum TopicStatus {
	
	NEW("new","草稿"),
	RELEASED("released","上线") ,
	OFFLINE("offline","关闭") ;
	
	private final String name ;

	private final String desc ;

	TopicStatus(String name, String desc){
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
