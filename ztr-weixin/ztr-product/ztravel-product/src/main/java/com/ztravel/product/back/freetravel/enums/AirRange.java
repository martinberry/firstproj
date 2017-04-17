package com.ztravel.product.back.freetravel.enums;



/**
 * @author wanhaofan
 * 航班航程
 * */
public enum AirRange {
	GO("go", "去程") ,
	MIDDLE("middle", "中间程") ,
	BACK("back", "返程") ;

	private final String name ;

	private final String desc ;


	AirRange(String name, String desc){
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
