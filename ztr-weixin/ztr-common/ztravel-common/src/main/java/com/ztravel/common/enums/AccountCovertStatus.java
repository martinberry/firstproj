package com.ztravel.common.enums;

public enum AccountCovertStatus {

	UNCONVERT("待兑换"), CONVERTED("已兑换");

	private final String description;

	AccountCovertStatus(String description) {
	        this.description = description;
	    }

	    public String getDescription() {
	        return description;
	    }
}
