/**
 *
 */
package com.ztravel.payment.event;

import com.ztravel.common.enums.PaymentType;

/**
 * @author zuoning.shen
 *
 */
public class OrderCancelledEvent {
    private String orderId;
    private boolean refunded;
    private PaymentType paymentType;
    private String thirdpartOrderId;
    private String thirdpartOrderDate;

    public OrderCancelledEvent(String orderId, boolean refunded, PaymentType paymentType, String thirdpartOrderId, String thirdpartOrderDate){
        this.orderId = orderId;
        this.refunded = refunded;
        this.paymentType= paymentType;
        this.thirdpartOrderId = thirdpartOrderId;
        this.thirdpartOrderDate = thirdpartOrderDate;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public boolean isRefunded() {
        return refunded;
    }
    public void setRefunded(boolean refunded) {
        this.refunded = refunded;
    }
	public PaymentType getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}
	public String getThirdpartOrderId() {
		return thirdpartOrderId;
	}
	public void setThirdpartOrderId(String thirdpartOrderId) {
		this.thirdpartOrderId = thirdpartOrderId;
	}
	public String getThirdpartOrderDate() {
		return thirdpartOrderDate;
	}
	public void setThirdpartOrderDate(String thirdpartOrderDate) {
		this.thirdpartOrderDate = thirdpartOrderDate;
	}
}
