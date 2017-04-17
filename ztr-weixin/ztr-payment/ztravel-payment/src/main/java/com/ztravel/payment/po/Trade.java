/**
 * 
 */
package com.ztravel.payment.po;

import org.joda.time.DateTime;

import com.ztravel.common.entity.AbstractBase;

/**
 * @author zuoning.shen
 *
 */
public class Trade extends AbstractBase {
    private String tradeId;
    private String memberId;
    private String orderId;
    private String orderType;
    private String tradeType;
    private String productType;
    private long orderAmount;
    private long tradeAmount;
    private String paymentType;
    private String comment;
    private DateTime tradeDate;
    private String tradeStatus;
    private String traceNum;
    private String bankPaymentTime;
    private String couponItemId;
    public String getTradeId() {
        return tradeId;
    }
    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }
    public String getMemberId() {
        return memberId;
    }
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getOrderType() {
        return orderType;
    }
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
    public String getTradeType() {
        return tradeType;
    }
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }
    public String getProductType() {
        return productType;
    }
    public void setProductType(String productType) {
        this.productType = productType;
    }
    public long getOrderAmount() {
        return orderAmount;
    }
    public void setOrderAmount(long orderAmount) {
        this.orderAmount = orderAmount;
    }
    public long getTradeAmount() {
        return tradeAmount;
    }
    public void setTradeAmount(long tradeAmount) {
        this.tradeAmount = tradeAmount;
    }
    public String getPaymentType() {
        return paymentType;
    }
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public DateTime getTradeDate() {
        return tradeDate;
    }
    public void setTradeDate(DateTime tradeDate) {
        this.tradeDate = tradeDate;
    }
    public String getTradeStatus() {
        return tradeStatus;
    }
    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
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

}
