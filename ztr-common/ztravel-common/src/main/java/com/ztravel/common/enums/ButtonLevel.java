package com.ztravel.common.enums;

public enum ButtonLevel {
	PARENTLEVEL(1,"PARENTLEVEL") ,
	CHILDLEVEL(2,"CHILDLEVEL") ;

	private final int level ;
	private final String desc ;

	private ButtonLevel(int level , String desc) {
		this.level = level ;
		this.desc = desc ;
	}

	public int getLevel() {
		return level;
	}

	public String getDesc() {
		return desc;
	}
}
