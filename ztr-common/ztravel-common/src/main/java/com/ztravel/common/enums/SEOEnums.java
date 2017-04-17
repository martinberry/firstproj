package com.ztravel.common.enums;

public enum SEOEnums {
	LIST_TITLE(".LIST.TITLE") ,
	LIST_DESC(".LIST.DESC") ,
	LIST_KEYWORDS(".LIST.KEYWORDS") ,
	DETAIL_TITLE(".DETAIL.TITLE") ,
	DETAIL_DESC(".DETAIL.KEYWORDS") ,
	DETAIL_KEYWORDS(".DETAIL.DESC") ;
	
	private final String value ;
	
	SEOEnums(String value){
		this.value = value ;
	}
	
	public String getValue() {
		return value;
	}
}
