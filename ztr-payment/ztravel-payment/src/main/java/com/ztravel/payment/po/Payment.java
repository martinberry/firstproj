/**
 * 
 */
package com.ztravel.payment.po;

import com.ztravel.common.entity.AbstractBase;

/**
 * 订单支付记录表
 * @author zuoning.shen
 *
 */
public class Payment extends AbstractBase{
    private String paymentId;
    private String orderId;
    private String paymentType;
    private long payAmount;
    private String traceNum;
    private String bankPaymentTime;
    private String couponItemId;
    private String paymentStatus;
    public String getPaymentId() {
        return paymentId;
    }
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getPaymentType() {
        return paymentType;
    }
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
    public long getPayAmount() {
        return payAmount;
    }
    public void setPayAmount(long payAmount) {
        this.payAmount = payAmount;
    }
    public String getTraceNum() {
        return traceNum;
    }
    public void setTraceNum(String traceNum) {
        this.traceNum = traceNum;
    }
    public String getBankPaymentTime() {
        return bankPaymentTime;
    }
    public void setBankPaymentTime(String bankPaymentTime) {
        this.bankPaymentTime = bankPaymentTime;
    }
    public String getCouponItemId() {
        return couponItemId;
    }
    public void setCouponItemId(String couponItemId) {
        this.couponItemId = couponItemId;
    }
    public String getPaymentStatus() {
        return paymentStatus;
    }
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
