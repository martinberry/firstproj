/**
 *
 */
package com.ztravel.common.order;

import com.ztravel.common.enums.PaymentType;

/**
 * @author zuoning.shen
 *
 */
public class OrderPaidBean {
    private String orderId;
    private PaymentType paymentType;
    /**
     * 三方支付流水号
     */
    private String traceNum;
    private String bankPaymentTime;
    
    private long tradeAmount ;

    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public PaymentType getPaymentType() {
        return paymentType;
    }
    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
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
	public long getTradeAmount() {
		return tradeAmount;
	}
	public void setTradeAmount(long tradeAmount) {
		this.tradeAmount = tradeAmount;
	}
}
