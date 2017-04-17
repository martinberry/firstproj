package com.ztravel.product.back.freetravel.enums;


/**
 * @author wanhaofan
 * 自由行产品供应商结算周期
 * */
public enum SettlementPeriod {
	ORDER("order","一单一结", 0),
	WEEK("week","周结", 1) ,
	HALF_MONTH("half_month","半月结", 2) ,
	MONTH("month","月结", 3) ,
	QUARTER("quarter","季结", 4) ;

	private final String name ;

	private final String desc ;

	private final int index ;


	SettlementPeriod(String name, String desc, int index){
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
