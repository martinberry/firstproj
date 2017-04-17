package com.ztravel.common.enums;

public enum ActivityAwardType {

    COUPON1("1512301615573201"),COUPON2("1512301616313202"),COUPON3("1512301617123203"),BAG("包"),CALENDARY("台历");

    private final String description;

    ActivityAwardType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
