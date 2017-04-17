package com.ztravel.common.entity;

import java.util.List;

import org.joda.time.DateTime;

import com.ztravel.common.enums.CouponBookStatus;

public class CouponBookVo {

    /**
     * 活动ID
     * */
    private String activityId;
    /**
     * 代金券ID
     * */
    private String couponId;
    /**
     * 代金券CODE
     * */
    private String couponCode;
    /**
     * 代金券名称
     */
    private String couponName;
    /**
     * 售价
     * */
    private long price;
    /**
     * 面额
     * */
    private long amount;
    /**
     * 预定数量
     * */
    private int bookAmount;
    /**
     * 可用数量
     * */
    private int leftAmount;
    /**
     * 手机号
     * */
    private String mobile;
    /**
     * 验证码
     * */
    private String verifyCode;
    /**
     * 每人限购张数
     */
    private int unit;
    /**
     * 售卖状态
     */
    private CouponBookStatus status;
    /**
     * 代金券有效起始时间
     * */
    private DateTime validTimeFrom;
    /**
     * 代金券有效结束时间
     * */
    private DateTime validTimeTo;
    /**
     * 使用说明
     * */
    private String description;

    private String couponSnapshot;

    private List<String> voucherIdList;

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public int getBookAmount() {
        return bookAmount;
    }

    public void setBookAmount(int bookAmount) {
        this.bookAmount = bookAmount;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public int getLeftAmount() {
        return leftAmount;
    }

    public void setLeftAmount(int leftAmount) {
        this.leftAmount = leftAmount;
    }

    public List<String> getVoucherIdList() {
        return voucherIdList;
    }

    public void setVoucherIdList(List<String> voucherIdList) {
        this.voucherIdList = voucherIdList;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public CouponBookStatus getStatus() {
        return status;
    }

    public void setStatus(CouponBookStatus status) {
        this.status = status;
    }

    public DateTime getValidTimeFrom() {
        return validTimeFrom;
    }

    public void setValidTimeFrom(DateTime validTimeFrom) {
        this.validTimeFrom = validTimeFrom;
    }

    public DateTime getValidTimeTo() {
        return validTimeTo;
    }

    public void setValidTimeTo(DateTime validTimeTo) {
        this.validTimeTo = validTimeTo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCouponSnapshot() {
        return couponSnapshot;
    }

    public void setCouponSnapshot(String couponSnapshot) {
        this.couponSnapshot = couponSnapshot;
    }

}
