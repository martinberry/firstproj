package com.ztravel.product.client.entity;


public class CouponClientEntity {

	String accountCouponId;
	/**
	 * 代金券ID
	 * */
	private String couponId;
	/**
	 * 代金券名称
	 * */
	private String name;
	 /**
	  * * 券码
	 * */
	private String couponCode;
	/**
	 * 代金券面额
	 * */
	private Long amount;
	/**
	 * 代金券有效起始时间
	 * */
	private String validTimeFrom;
	/**
	 * 代金券有效结束时间
	 * */
	private String validTimeTo;
	/**
	 *支持的产品范围
	 * */
	private String productRange;

	/**
	 * 使用说明
	 * */
	private String description;
	/**
	 * 使用条件,订单满多少消费才可使用
	 * */
	private Long orderLeast;

	private String productRangeType;

	private Integer unit;

	private Boolean isDelete;

	private Integer totalNum;

	private long price;

	private String status;


	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getValidTimeFrom() {
		return validTimeFrom;
	}
	public void setValidTimeFrom(String validTimeFrom) {
		this.validTimeFrom = validTimeFrom;
	}
	public String getValidTimeTo() {
		return validTimeTo;
	}
	public void setValidTimeTo(String validTimeTo) {
		this.validTimeTo = validTimeTo;
	}
	public String getProductRange() {
		return productRange;
	}
	public void setProductRange(String productRange) {
		this.productRange = productRange;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getOrderLeast() {
		return orderLeast;
	}
	public void setOrderLeast(Long orderLeast) {
		this.orderLeast = orderLeast;
	}
	public String getProductRangeType() {
		return productRangeType;
	}
	public void setProductRangeType(String productRangeType) {
		this.productRangeType = productRangeType;
	}
	public Integer getUnit() {
		return unit;
	}
	public void setUnit(Integer unit) {
		this.unit = unit;
	}
	public String getAccountCouponId() {
		return accountCouponId;
	}
	public void setAccountCouponId(String accountCouponId) {
		this.accountCouponId = accountCouponId;
	}
	public Boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
