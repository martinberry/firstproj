package com.ztravel.payment.model;

import com.ztravel.common.enums.OrderType;
import com.ztravel.common.enums.PaymentType;
import com.ztravel.common.enums.TradeType;

/**
 * @author wanhaofan
 *
 */
public class PaymentResultEntity {
    private String orderId;
    private PaymentType paymentType;
    private String thirdpartOrderId;
    private String thirdpartOrderDate;
    private OrderType orderType;
    private TradeType tradeType;
    private long tradeAmount ;

    public PaymentResultEntity(String orderId, PaymentType paymentType, String thirdpartOrderId, String thirdpartOrderDate, OrderType orderType, TradeType tradeType) {
    	this(orderId, paymentType, thirdpartOrderId, orderType, tradeType) ;
    	this.thirdpartOrderDate= thirdpartOrderDate;
    }
    
    public PaymentResultEntity(String orderId, PaymentType paymentType, String thirdpartOrderId, OrderType orderType, TradeType tradeType) {
        this.orderId = orderId;
        this.paymentType = paymentType;
        this.orderType = orderType;
        this.tradeType = tradeType;
        this.thirdpartOrderId= thirdpartOrderId;
    }

	public String getOrderId() {
		return orderId;
	}

	public String getThirdpartOrderId() {
		return thirdpartOrderId;
	}
	
	public String getThirdpartOrderDate() {
		return thirdpartOrderDate;
	}

    public PaymentType getPaymentType() {
        return paymentType;
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

	public long getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(long tradeAmount) {
		this.tradeAmount = tradeAmount;
	}
}
