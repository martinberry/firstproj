package com.ztravel.payment.event;

import com.ztravel.common.enums.PaymentType;

/**
 * @author zuoning.shen
 *
 */
public class PaymentResultEvent {
    private String orderId;
    private PaymentType paymentType;
    private String thirdpartOrderId;
    private String thirdpartOrderDate;

    public PaymentResultEvent(String orderId, PaymentType paymentType, String thirdpartOrderId, String thirdpartOrderDate) {
        this.orderId = orderId;
        this.paymentType = paymentType;
        this.thirdpartOrderId= thirdpartOrderId;
        this.thirdpartOrderDate= thirdpartOrderDate;
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
}
