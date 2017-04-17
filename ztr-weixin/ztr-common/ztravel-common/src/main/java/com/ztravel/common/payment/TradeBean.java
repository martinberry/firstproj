/**
 *
 */
package com.ztravel.common.payment;

import org.joda.time.DateTime;

import com.ztravel.common.enums.OrderType;
import com.ztravel.common.enums.PaymentType;
import com.ztravel.common.enums.ProductType;
import com.ztravel.common.enums.TradeStatus;
import com.ztravel.common.enums.TradeType;

/**
 * @author zuoning.shen
 *
 */
public class TradeBean {
    private String tradeId;
    private String memberId;
    private String orderId;
    private OrderType orderType;
    private TradeType tradeType;
    private ProductType productType;
    private long orderAmount;
    private long tradeAmount;
    private PaymentType paymentType;
    private String comment;
    private DateTime tradeDate;
    private TradeStatus tradeStatus;
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
    public OrderType getOrderType() {
        return orderType;
    }
    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }
    public TradeType getTradeType() {
        return tradeType;
    }
    public void setTradeType(TradeType tradeType) {
        this.tradeType = tradeType;
    }
    public ProductType getProductType() {
        return productType;
    }
    public void setProductType(ProductType productType) {
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
    public PaymentType getPaymentType() {
        return paymentType;
    }
    public void setPaymentType(PaymentType paymentType) {
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
    public TradeStatus getTradeStatus() {
        return tradeStatus;
    }
    public void setTradeStatus(TradeStatus tradeStatus) {
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
