package com.ztravel.product.back.hotel.enums;

/**
 * @author tengmeilin
 * 酒店类型
 */
public enum HotelType {

	TYPE_1("type1","顶级奢华酒店"),
	TYPE_2("type2","度假休闲酒店"),
	TYPE_3("type3","青年旅社") ,
	TYPE_4("type4","酒店式公寓") ,
	TYPE_5("type5","家庭旅馆") ,
	TYPE_6("type6","精品客栈") ,
	TYPE_7("type7","商务会务酒店") ,
	TYPE_8("type8","连锁品牌") ;

	private final String name ;

	private final String desc ;


	HotelType(String name, String desc){
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
