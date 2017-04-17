package com.ztravel.test.gen.po;

import java.util.Date;

public class Order {
    private String orderId;

    private String orderNo;

    private String orderType;

    private Date createDate;

    private String creator;

    private Date updateDate;

    private String operator;

    private String state;

    private String stateChangeHistory;

    private Integer progress;

    private String discountCouponId;

    private Integer integral;

    private Long totalPrice;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getStateChangeHistory() {
        return stateChangeHistory;
    }

    public void setStateChangeHistory(String stateChangeHistory) {
        this.stateChangeHistory = stateChangeHistory == null ? null : stateChangeHistory.trim();
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public String getDiscountCouponId() {
        return discountCouponId;
    }

    public void setDiscountCouponId(String discountCouponId) {
        this.discountCouponId = discountCouponId == null ? null : discountCouponId.trim();
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }
}