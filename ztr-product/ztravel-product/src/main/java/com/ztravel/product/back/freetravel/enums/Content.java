package com.ztravel.product.back.freetravel.enums;


/**
 * @author wanhaofan
 * 自由行产品内容
 * */
public enum Content {
	FLIGHT("flight", "机票") ,
	HOTEL("hotel", "酒店") ,
	SHUTTLE("shuttle", "接送机") ,
	ZENBOOK("zenbook", "真旅本子") ,
	WIFI("wifi", "WIFI") ,
	OTHER("other", "其他") ;

	private final String name ;

	private final String desc ;


	Content(String name, String desc){
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
