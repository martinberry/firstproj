package com.ztravel.product.back.freetravel.enums;


/**
 * @author wanhaofan
 * 自由行某日产品销售状态
 * */
public enum SaleStatus {
	RELEASED("released","上线"),
	CLOSED("closed","关闭") ,
	NOT_SCHEDULED("not_scheduled","未开放") ;
	

	private final String name ;

	private final String desc ;

	SaleStatus(String name, String desc){
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
