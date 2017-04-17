package com.ztravel.common.enums;

public enum Gender{
    MALE("男"), FEMALE("女");

    private final String desc;

    Gender(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

}
