package com.ztravel.common.enums;

public enum NoticeType {
	ORDERTYPE("订单",1),
	COUPONTYPE("代金券",2),
	REMAINTYPE("余额",3);

	private String desc;
	private int index;

	NoticeType(String desc, int index){
		this.desc = desc;
		this.index = index;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
