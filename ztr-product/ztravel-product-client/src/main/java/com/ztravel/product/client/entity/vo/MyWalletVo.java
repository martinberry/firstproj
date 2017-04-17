package com.ztravel.product.client.entity.vo;

import com.ztravel.common.enums.VoucherType;


public class MyWalletVo {

	/**
	 * 代金券ID
	 * */
	private String couponItemId;
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
	 * 使用说明
	 * */
	private String description;

	private long price;

	private String status;
	
	private String productRange ;
	
	private long orderLeast ;
	
	private VoucherType voucherType ;

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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getCouponItemId() {
		return couponItemId;
	}
	public void setCouponItemId(String couponItemId) {
		this.couponItemId = couponItemId;
	}
	public String getProductRange() {
		return productRange;
	}
	public void setProductRange(String productRange) {
		this.productRange = productRange;
	}
	public long getOrderLeast() {
		return orderLeast;
	}
	public void setOrderLeast(long orderLeast) {
		this.orderLeast = orderLeast;
	}
	public VoucherType getVoucherType() {
		return voucherType;
	}
	public void setVoucherType(VoucherType voucherType) {
		this.voucherType = voucherType;
	}

}
