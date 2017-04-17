package com.ztravel.common.enums;

public enum CareerType {
	
	STUDENT("student", "在校学生") ,
	EMPLOYED("employed", "在职") ,
	FREE("free", "自由职业者") ,
	RETIRE("retire", "退休") ,
	CHILD("child", "学龄前儿童") ;

	private final String name ;

	private final String desc ;
	
	CareerType(String name, String desc){
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
