package com.ztravel.common.enums;

	public enum OrderFrom {
	    WEIXIN("微信"), WEB("web");

	    private final String description;

	    OrderFrom(String description) {
	        this.description = description;
	    }

	    public String getDescription() {
	        return description;
	    }
	}
