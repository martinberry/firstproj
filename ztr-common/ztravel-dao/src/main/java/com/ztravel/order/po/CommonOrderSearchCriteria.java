package com.ztravel.order.po;

import com.ztravel.common.entity.PaginationEntity;

public class CommonOrderSearchCriteria extends PaginationEntity{
	private String orderNo;
	private String orderNoVice;
	private String memberId;
	private String createDateFrom;
	private String createDateTo;
	private String status;
	private String orderType;
	private String orderId;
	private String travellerNames;

	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderNoVice() {
		return orderNoVice;
	}
	public void setOrderNoVice(String orderNoVice) {
		this.orderNoVice = orderNoVice;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getCreateDateFrom() {
		return createDateFrom;
	}
	public void setCreateDateFrom(String createDateFrom) {
		this.createDateFrom = createDateFrom;
	}
	public String getCreateDateTo() {
		return createDateTo;
	}
	public void setCreateDateTo(String createDateTo) {
		this.createDateTo = createDateTo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    public String getTravellerNames() {
        return travellerNames;
    }
    public void setTravellerNames(String travellerNames) {
        this.travellerNames = travellerNames;
    }

}
