package com.ztravel.order.back.vo;



public class CommonOrderListVo {
	private String orderNoOrigin;
	private String orderNoVice;
	private String createDate;
	private String memberId;
	private Long price;
	private String status;
	private String remark;
	private String orderType;
	private String orderOriginStatus;
	private String orderId;
	private String orderIdOrigin;
	private String stateChangeHistory;
	private String travellerNames;

	public String getStateChangeHistory() {
		return stateChangeHistory;
	}
	public void setStateChangeHistory(String stateChangeHistory) {
		this.stateChangeHistory = stateChangeHistory;
	}
	public String getOrderIdOrigin() {
		return orderIdOrigin;
	}
	public void setOrderIdOrigin(String orderIdOrigin) {
		this.orderIdOrigin = orderIdOrigin;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderOriginStatus() {
		return orderOriginStatus;
	}
	public void setOrderOriginStatus(String orderOriginStatus) {
		this.orderOriginStatus = orderOriginStatus;
	}

	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderNoOrigin() {
		return orderNoOrigin;
	}
	public void setOrderNoOrigin(String orderNoOrigin) {
		this.orderNoOrigin = orderNoOrigin;
	}
	public String getOrderNoVice() {
		return orderNoVice;
	}
	public void setOrderNoVice(String orderNoVice) {
		this.orderNoVice = orderNoVice;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
    public String getTravellerNames() {
        return travellerNames;
    }
    public void setTravellerNames(String travellerNames) {
        this.travellerNames = travellerNames;
    }




}
