package com.ztravel.common.enums;

public enum VoucherType {

	SYSTEM("system","SYSTEM","系统占用"),
	NORMAL("normal","NORMAL","非占用");


	private final String value;
	private final String code ;
	private final String desc ;

	VoucherType(String value,String code,String desc){
		this.value = value ;
		this.code = code ;
		this.desc = desc ;
	}

	public String getValue() {
		return value;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static VoucherType getByCode(String code){
		for(VoucherType status : VoucherType.values()){
			if( status.getCode().equals(code) ){
				return status;
			}
		}
		return null;
	}

	public static String getDescByCode(String code){
		for(VoucherType status : VoucherType.values()){
			if(status.getCode().equals(code)){
				return status.getDesc();
			}
		}
		return null;
	}

}
