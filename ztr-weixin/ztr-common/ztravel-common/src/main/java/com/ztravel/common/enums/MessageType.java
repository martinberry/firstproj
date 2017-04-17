package com.ztravel.common.enums;

public enum MessageType {
	ORDER("订单通知",1),
	EVALUATION("评价通知",2);

	private final String desc;
	private final int index;

	private MessageType(String desc, int index) {
		this.desc = desc;
		this.index = index;
	}

	public String getDesc() {
		return desc;
	}

	public int getIndex() {
		return index;
	}

	public static MessageType valueOfIndex(int index){
		for(MessageType t: MessageType.values()){
			if(t.getIndex() == index)return t;
		}
		return null;
	}

}
