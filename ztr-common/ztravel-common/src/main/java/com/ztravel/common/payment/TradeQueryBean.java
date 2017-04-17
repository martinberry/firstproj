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
public class TradeQueryBean{
    private DateTime conditionTradeFrom;
    private DateTime conditionTradeTo;
    private String memberId;
    private String orderId;
    private String traceNum;
    private OrderType orderType;
    private TradeType tradeType;
    private ProductType productType;
    private PaymentType paymentType;
    private TradeStatus tradeStatus;
    private int pageNo = -1;
    private int pageSize = -1;
    public DateTime getConditionTradeFrom() {
        return conditionTradeFrom;
    }
    public void setConditionTradeFrom(DateTime conditionTradeFrom) {
        this.conditionTradeFrom = conditionTradeFrom;
    }
    public DateTime getConditionTradeTo() {
        return conditionTradeTo;
    }
    public void setConditionTradeTo(DateTime conditionTradeTo) {
        this.conditionTradeTo = conditionTradeTo;
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
    public String getTraceNum() {
        return traceNum;
    }
    public void setTraceNum(String traceNum) {
        this.traceNum = traceNum;
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
    public PaymentType getPaymentType() {
        return paymentType;
    }
    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
    public TradeStatus getTradeStatus() {
        return tradeStatus;
    }
    public void setTradeStatus(TradeStatus tradeStatus) {
        this.tradeStatus = tradeStatus;
    }
    public int getPageNo() {
        return pageNo;
    }
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
