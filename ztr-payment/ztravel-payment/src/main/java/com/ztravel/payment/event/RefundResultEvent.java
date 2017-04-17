package com.ztravel.payment.event;


/**
 * @author zuoning.shen
 *
 */
public class RefundResultEvent {
	private String relatedOrderId;
	private String refundOrderId;
	private long refundAmount;
	private String thirdpartOrderId;

	public RefundResultEvent(String relatedOrderId, String refundOrderId, long refundAmount, String thirdpartOrderId) {
		this.relatedOrderId = relatedOrderId;
		this.refundOrderId = refundOrderId;
		this.refundAmount = refundAmount;
		this.thirdpartOrderId = thirdpartOrderId;
	}

	public String getRefundOrderId() {
		return refundOrderId;
	}

	public String getRelatedOrderId() {
		return relatedOrderId;
	}

	public long getRefundAmount() {
		return refundAmount;
	}

	public String getThirdpartOrderId() {
		return thirdpartOrderId;
	}
}
