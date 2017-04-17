package com.ztravel.common.enums;

public enum AccountConvertContent {

	MOBILECARD("手机充值卡");

    private final String description;

    AccountConvertContent(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
