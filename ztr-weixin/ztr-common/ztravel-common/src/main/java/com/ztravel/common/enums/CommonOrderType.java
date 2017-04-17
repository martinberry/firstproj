package com.ztravel.common.enums;

public enum CommonOrderType {
	
	OP_CONFIRM_REPAIR("OP确认-补款"),
	OP_CONFIRM_EQUAL("OP确认-等额"),
	OP_CONFIRM_REFUND("OP确认-退款");
	private final String description;
	
	CommonOrderType(String description) {
	   this.description = description;
	}
	
	public String getDescription() {
	   return description;
	}

}







