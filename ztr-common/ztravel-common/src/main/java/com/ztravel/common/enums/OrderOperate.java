package com.ztravel.common.enums;

public enum OrderOperate {

	CREATE("CREATE","填写订单"),
	PAY("PAY","支付订单"),
	PAYSUCCESS("PAYSUCCESS","支付成功"),
	GIFTBOX("GIFTBOX","心意盒子"),
	OUTNOTICE("OUTNOTICE","出行通知"),
	OUTTING("OUTTING","出行中"),
	COMPLETED("COMPLETED","已完成"),
	ZTMANAGER("ZTMANAGER","真旅管家"),
	CANCLE("CANCLE","取消"),
	REFUNDING("REFUNDING","退款中"),
	REFUNDED("REFUNDED","完成退款"),
	REFUNDFAILED("REFUNDFAILED","退款失败"),
	EVALATE("EVALATE","出行评价"),
	MAKING("MAKING","制作中"),
	MAKED("MAKED","制作完成"),
	MATERIALSEND("MATERIALSEND","材料送出"),
	LOCALCONFIRM("LOCALCONFIRM","当地游后台确认"),
	OPCONFIRM("OPCONFIRM","Op确认");
	


	private final String code ;
	private final String desc ;

	OrderOperate(String code,String desc){
		this.code = code ;
		this.desc = desc ;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}
