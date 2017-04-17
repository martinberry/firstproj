package com.ztravel.common.enums;

public enum MessageTitleType {
	PAYSUCCESS("支付成功",1),
	CANCLE("取消订单",1),
	GIFTBOX("礼盒发放",1),
	OUTNOTICE("出行通知",1),
	REFUNDED("订单退款",1),
	REFUNDFAILED("订单退款",1),
	EVALATE("新评价",2);

	private final String desc ;
	private final int findex;

	private MessageTitleType(String desc,int findex) {
		this.desc = desc;
		this.findex = findex;
	}

	public String getDesc() {
		return desc;
	}

	public int getFindex() {
		return findex;
	}

}
