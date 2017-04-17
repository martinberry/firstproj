package com.ztravel.common.enums;

public enum ProductStatus {
	NEW("new","草稿"),
	RELEASED("released","上线") ,
	OFFLINE("offline","关闭") ,
	EXPIRED("expired","过期"); 

	private final String name ;

	private final String desc ;

	ProductStatus(String name, String desc){
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
