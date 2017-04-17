/**
 *
 */
package com.ztravel.common.payment;

import com.ztravel.common.enums.OrderPayStatus;

/**
 * @author zuoning.shen
 *
 */
public class OrderPayInfoBean {
    private String orderId;
    private boolean isMobile;
    private String memberId;
    private long orderAmount;
    private long payAmount;
    private long useRewarPoint;
    private long useCoupon;
    private String couponItemId;
    private long paidAmount;
    private OrderPayStatus orderPayStatus;
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public boolean isMobile() {
        return isMobile;
    }
    public void setMobile(boolean isMobile) {
        this.isMobile = isMobile;
    }
    public String getMemberId() {
        return memberId;
    }
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    public long getOrderAmount() {
        return orderAmount;
    }
    public void setOrderAmount(long orderAmount) {
        this.orderAmount = orderAmount;
    }
    public long getPayAmount() {
        return payAmount;
    }
    public void setPayAmount(long payAmount) {
        this.payAmount = payAmount;
    }
    public long getUseRewarPoint() {
        return useRewarPoint;
    }
    public void setUseRewarPoint(long useRewarPoint) {
        this.useRewarPoint = useRewarPoint;
    }
    public long getUseCoupon() {
        return useCoupon;
    }
    public void setUseCoupon(long useCoupon) {
        this.useCoupon = useCoupon;
    }
    public String getCouponItemId() {
        return couponItemId;
    }
    public void setCouponItemId(String couponItemId) {
        this.couponItemId = couponItemId;
    }
    public long getPaidAmount() {
        return paidAmount;
    }
    public void setPaidAmount(long paidAmount) {
        this.paidAmount = paidAmount;
    }
    public OrderPayStatus getOrderPayStatus() {
        return orderPayStatus;
    }
    public void setOrderPayStatus(OrderPayStatus orderPayStatus) {
        this.orderPayStatus = orderPayStatus;
    }


}
