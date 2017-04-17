package com.ztravel.weixin.activity.enums;

public enum ActivityRecordStatus {

    INIT("初始占用"),
    RELEASED("已释放") ,
    FINISHED("已完成");

    private String desc;

    private ActivityRecordStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

}
