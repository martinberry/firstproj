package com.ztravel.product.back.freetravel.enums;


/**
 * @author wanhaofan
 * 自由行产品酒店床型
 * */
public enum BedType {
	INDEFINITE("indefinite","大床/双床 不定", 0),
	BIG("big","大床", 1) ,
	DOUBLE("double","双床", 2) ,
	OTHERS("others","其他", 3) ;

	private final String name ;

	private final String desc ;

	private final int index ;


	BedType(String name, String desc, int index){
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
