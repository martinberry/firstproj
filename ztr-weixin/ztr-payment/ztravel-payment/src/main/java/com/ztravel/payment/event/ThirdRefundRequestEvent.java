package com.ztravel.payment.event;


/**
 * @author zuoning.shen
 *
 */
public class ThirdRefundRequestEvent {
	private String refundOrderId;
	private String orderGroupId;
	private long refundAmount;
	
	public String getRefundOrderId() {
		return refundOrderId;
	}
	
	public String getOrderGroupId(){
		return orderGroupId;
	}
	
	public long getRefundAmount(){
		return refundAmount;
	}
	
	public void setRefundAmount(long refundAmount){
		this.refundAmount = refundAmount;
	}
	
	public ThirdRefundRequestEvent(String refundOrderId, String orderGroupId){
		this.refundOrderId = refundOrderId;
		this.orderGroupId = orderGroupId;
	}
}
