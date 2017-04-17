package com.ztravel.common.enums;

public enum PieceType {

	TICKET("ticket","门票"),
	LOCAL("local","当地酷玩") ,
	TRAFFIC("traffic","交通接驳") ,
	WIFI("wifi","wifi通讯") ,
	HOTELUP("hotelup","酒店升级") ,
	CHARTER("charter","包车服务") ,
	INTELTAXI("inteltaxi","国际租车") ,
	VISA("visa","旅游签证") ;
	
	private final String name ;

	private final String desc ;

	PieceType(String name, String desc){
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
