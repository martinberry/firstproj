package com.ztravel.common.adapter.sms;

public enum SMSType {

    REGISTER("注册"), FIND_PASSWORD("找回密码"), COMMON("普通"), VOUCHERBINDMOBILE("代金券绑定电话");

    private final String description;

    SMSType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
