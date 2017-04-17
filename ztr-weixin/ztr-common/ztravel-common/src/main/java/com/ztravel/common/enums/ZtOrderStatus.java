package com.ztravel.common.enums;

public enum ZtOrderStatus {

	UNPAY("unpay","UNPAY","待支付"),
	PAYED("payed","PAYED","支付成功"),
	OPCONFIRM("confirm","CONFIRM","OP确认"),
	GIFTSEND("giftsend","GIFTSEND","礼盒发放"),
	OUTNOTICE("outnotice","OUTNOTICE","出行通知"),
	OUTTING("outting","OUTTING","出行中"),
	COMPLETED("completed","COMPLETED","已完成"),
	CANCELED("canceled","CANCELED","已取消"),
	REFUNDFAILED("refundfailed","REFUNDFAILED","退款失败"),
	REFUNDING("refunding","REFUNDING","退款中"),
	MAKED("maked","MAKED","制作完成"),
	MATERIALSEND("materialsend","MATERIALSEND","材料送回");


	private final String value;
	private final String code ;
	private final String desc ;

	ZtOrderStatus(String value,String code,String desc){
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

	public static ZtOrderStatus getByCode(String code){
		for(ZtOrderStatus status : ZtOrderStatus.values()){
			if( status.getCode().equals(code) ){
				return status;
			}
		}
		return null;
	}

	public static String getDescByCode(String code){
		for(ZtOrderStatus status : ZtOrderStatus.values()){
			if(status.getCode().equals(code)){
				return status.getDesc();
			}
		}
		return null;
	}

}
