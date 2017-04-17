package com.ztravel.common.enums;


/**
 * @author wanhaofan
 * 产品性质
 * */
public enum Nature {
	
	//自由行
	PACKAGE("package", "打包") ,
	COMBINATION("combination", "自组") ,
	//非自由行
	VISA("visa", "签证") ,
	UNVISA("unvisa", "当地游") ;

	private final String name ;

	private final String desc ;


	Nature(String name, String desc){
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
