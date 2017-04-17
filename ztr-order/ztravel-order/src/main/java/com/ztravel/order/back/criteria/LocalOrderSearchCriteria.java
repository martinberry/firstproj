package com.ztravel.order.back.criteria;

public class LocalOrderSearchCriteria extends OrderSearchCriteria{
	private String purchaseTimeFrom;
	private String purchaseTimeTo;
	private String productNature;
	private String payStatus;
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getProductNature() {
		return productNature;
	}
	public void setProductNature(String productNature) {
		this.productNature = productNature;
	}	
	public String getPurchaseTimeFrom() {
		return purchaseTimeFrom;
	}
	public void setPurchaseTimeFrom(String purchaseTimeFrom) {
		this.purchaseTimeFrom = purchaseTimeFrom;
	}
	public String getPurchaseTimeTo() {
		return purchaseTimeTo;
	}
	public void setPurchaseTimeTo(String purchaseTimeTo) {
		this.purchaseTimeTo = purchaseTimeTo;
	}

}
