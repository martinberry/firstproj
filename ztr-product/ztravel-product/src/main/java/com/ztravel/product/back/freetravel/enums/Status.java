package com.ztravel.product.back.freetravel.enums;


/**
 * @author wanhaofan
 * 自由行产品状态
 * */
public enum Status {
	NEW("new","草稿"),
	RELEASED("released","上线") ,
	OFFLINE("offline","关闭") ,
	EXPIRED("expired","过期") ;

	private final String name ;

	private final String desc ;

	Status(String name, String desc){
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
