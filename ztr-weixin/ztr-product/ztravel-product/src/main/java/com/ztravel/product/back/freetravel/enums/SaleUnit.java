package com.ztravel.product.back.freetravel.enums;


/**
 * @author wanhaofan
 * 自由行产品产品销售单位
 * */
public enum SaleUnit {
	SINGLE("single","单人份") ;

	private final String name ;

	private final String desc ;

	SaleUnit(String name, String desc){
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
