package com.ztravel.common.enums;

public enum CouponBookStatus {
    READY("即将开售"),
    SELLING("售卖中"),
    SOLDOUT("已售完"),
    FINISHED("已结束");

    private final String desc;
    CouponBookStatus(String desc) {
        this.desc = desc;
    }
    public String getDesc(){
        return desc;
    }
}
