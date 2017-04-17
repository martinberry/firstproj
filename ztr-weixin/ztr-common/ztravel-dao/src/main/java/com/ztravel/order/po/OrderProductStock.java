package com.ztravel.order.po;

public class OrderProductStock {
    private String lockFlag;

    public String getLockFlag() {
        return lockFlag;
    }

    public void setLockFlag(String lockFlag) {
        this.lockFlag = lockFlag == null ? null : lockFlag.trim();
    }
}