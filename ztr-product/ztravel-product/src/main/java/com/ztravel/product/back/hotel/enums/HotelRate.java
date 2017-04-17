package com.ztravel.product.back.hotel.enums;


/**
 * @author tengmeilin
 * 酒店星级
 * */
public enum HotelRate {
	SUPER_FIVE_STAR("super-five-star","超五星/奢华","5") ,
	FIVE_STAR("five-star","五星/豪华","5") ,
	QUASI_FIVE_STAR("quasi-five-star","准五星/高档","4-5") ,
	FOUR_STAR("four-star","四星/轻奢","4") ,
	QUASI_FOUR_STAR("quasi-four-star","准四星/品质","3-5") ,
	THREE_STAR("three-star","三星/舒适","3") ,
	QUASI_THREE_STAR("quasi-three-star","准三星/性价比","2-5") ,
	TWO_STAR("two-star","二星/经济","2") ;

	private final String name ;

	private final String desc ;

	private final String num ;


	HotelRate(String name, String desc, String num){
		this.name = name ;
		this.desc = desc ;
		this.num = num ;
	}


	public String getName() {
		return name;
	}


	public String getDesc() {
		return desc;
	}

	public String getNum() {
		return num;
	}

}
