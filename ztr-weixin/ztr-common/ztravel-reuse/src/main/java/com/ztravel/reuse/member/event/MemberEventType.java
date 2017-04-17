package com.ztravel.reuse.member.event;


public enum MemberEventType {

	LOGIN("LOGIN"),
	REGISTER("REGISTER"),
	RANDL("RANDL");

	private final String value ;

	private MemberEventType(String value){
		this.value = value ;
	}

	public String getValue() {
		return value;
	}
}
