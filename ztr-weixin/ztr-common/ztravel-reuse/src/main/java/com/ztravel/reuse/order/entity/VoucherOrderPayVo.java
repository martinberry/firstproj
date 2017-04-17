package com.ztravel.reuse.order.entity;

import com.ztravel.common.entity.CouponSnapshot;

public class VoucherOrderPayVo extends OrderPayVo {

    private Long itemPrice;

    private int num;

    private CouponSnapshot couponSnapShot;

    public Long getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Long itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public CouponSnapshot getCouponSnapShot() {
        return couponSnapShot;
    }

    public void setCouponSnapShot(CouponSnapshot couponSnapShot) {
        this.couponSnapShot = couponSnapShot;
    }

}
