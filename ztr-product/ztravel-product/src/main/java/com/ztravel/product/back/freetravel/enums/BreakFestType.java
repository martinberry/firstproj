package com.ztravel.product.back.freetravel.enums;


/**
 * @author wanhaofan
 * 自由行产品酒店早餐类型
 * */
public enum BreakFestType {
	DOUBLE("double","双早", 0),
	SINGLE("single","单早", 1) ,
	NONE("none","无早", 2) ,
	MORE("more","更多", 3) ;

	private final String name ;

	private final String desc ;

	private final int index ;


	BreakFestType(String name, String desc, int index){
		this.name = name ;
		this.desc = desc ;
		this.index = index ;
	}


	public String getName() {
		return name;
	}


	public String getDesc() {
		return desc;
	}


	public int getIndex() {
		return index;
	}

}
