package com.ztravel.common.enums;

public enum VoucherOrderStatus {

	UNPAY("unpay","UNPAY","待支付"),
	CANCELED("canceled","CANCELED","已取消"),
	PAYED("payed","PAYED","支付成功"),
	AUDITING("auditing","AUDITING","待审核"),
	REFUNDED("refunded","REFUNDED","已退款"),
	REFUNDFAILED("refundfailed","REFUNDFAILED","退款失败"),
	REFUNDING("refunding","REFUNDING","退款中");

	private final String value;
	private final String code ;
	private final String desc ;

	VoucherOrderStatus(String value,String code,String desc){
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

	public static VoucherOrderStatus getByCode(String code){
		for(VoucherOrderStatus status : VoucherOrderStatus.values()){
			if( status.getCode().equals(code) ){
				return status;
			}
		}
		return null;
	}

	public static String getDescByCode(String code){
		for(VoucherOrderStatus status : VoucherOrderStatus.values()){
			if(status.getCode().equals(code)){
				return status.getDesc();
			}
		}
		return null;
	}

}
