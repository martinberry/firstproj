package com.ztravel.order.wx.vo;

import java.util.Map;

public class OrderVo {
	/**
	 * 订单编号
	 * */
	private String orderCode;
	/**
	 * 产品名称
	 * */
	private String productName;
    /**
	 * visa价格种类
	 * */
	private String costPriceName;
	/**
	 * 订单Id
	 * */
	private String orderId;
	/**
	 * 订单金额
	 * */
	private String payment;
	/**
	 * 出发日期
	 * */
	private String departDate;

	/**
	 * 预订日期
	 * */
	private String createDate;
	/**
	 * 订单状态
	 * */
	private String status;
	/**
	 * 订单状态描述
	 * */
	private String statusDesc;

	/**
	 * 判断订单是否可评价
	 * */
	private Boolean isComment;

	private String payAmount;

	/**
	 * 订单操作进程
	 * */
	private Map<String, String> orderOperate;

	private Map<String, String> additionalInfos;

	/**
	 * 补款订单id
	 */
	private String commonOrderId;

	/**
	 * 补款订单金额
	 */
	private String commonOrderPrice;

	/**
	 *
	 *补款订单状态
	 */
	private String commonOrderStatus;
	
	private String productNature;
	

	public String getProductNature() {
		return productNature;
	}
	public void setProductNature(String productNature) {
		this.productNature = productNature;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getDepartDate() {
		return departDate;
	}
	public void setDepartDate(String departDate) {
		this.departDate = departDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public Boolean getIsComment() {
		return isComment;
	}
	public void setIsComment(Boolean isComment) {
		this.isComment = isComment;
	}
	public Map<String, String> getAdditionalInfos() {
		return additionalInfos;
	}
	public void setAdditionalInfos(Map<String, String> additionalInfos) {
		this.additionalInfos = additionalInfos;
	}
	public String getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}
	public Map<String, String> getOrderOperate() {
		return orderOperate;
	}
	public void setOrderOperate(Map<String, String> orderOperate) {
		this.orderOperate = orderOperate;
	}
	public String getCommonOrderId() {
		return commonOrderId;
	}
	public void setCommonOrderId(String commonOrderId) {
		this.commonOrderId = commonOrderId;
	}
	public String getCommonOrderPrice() {
		return commonOrderPrice;
	}
	public void setCommonOrderPrice(String commonOrderPrice) {
		this.commonOrderPrice = commonOrderPrice;
	}
	public String getCommonOrderStatus() {
		return commonOrderStatus;
	}
	public void setCommonOrderStatus(String commonOrderStatus) {
		this.commonOrderStatus = commonOrderStatus;
	}
	public String getCostPriceName() {
		return costPriceName;
	}
	public void setCostPriceName(String costPriceName) {
		this.costPriceName = costPriceName;
	}
}
