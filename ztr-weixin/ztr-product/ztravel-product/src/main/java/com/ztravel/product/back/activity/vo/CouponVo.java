package com.ztravel.product.back.activity.vo;

import com.ztravel.common.enums.CouponType;

public class CouponVo {
	String activityId;
	/**
	 * 代金券ID
	 * */
	private String couponId;
	/**
	 * 券码
	 * */
	private String couponCode;
	/**
	 * 代金券名称
	 * */
	private String name;
	/**
	 * 代金券面额
	 * */
	private Long amount;
	/**
	 * 代金券售价
	 * */
	private Long price;

	/**
	 * 支持产品
	 * */
	private String supportProducts;
	/**
	 * 代金券有效起始时间
	 * */
	private String validTimeFrom;

	/**
	 * 代金券状态
	 * */
	private String status;
	/**
	 * 代金券状态描述
	 * */
	private String statusDesc;
	/**
	 * 代金券有效结束时间
	 * */
	private String validTimeTo;
	/**
	 * 代金券有效起始时间 HH
	 * */
	private String validHourFrom;
	/**
	 * 代金券有效结束时间 HH
	 * */
	private String validHourTo;
	/**
	 * 代金券有效起始时间 HH
	 * */
	private String validMinFrom;
	/**
	 * 代金券有效结束时间 HH
	 * */
	private String validMinTo;
	/**
	 * 代金券类型
	 * */
	private CouponType couponType;
	/**
	 * 代金券类型说明
	 * */
	private String couponTypeDesc;
	/**
	 * 使用说明
	 * */
	private String description;
	/**
	 * 产品范围
	 * */
	private String  productRangeType;

	/**
	 * 代金券总的发放量
	 * */
	private Integer totalNum;
	/**
	 * 代金券已发放量
	 * */
	private Integer usedNum;
	/**
	 * 使用条件,订单满多少消费才可使用
	 * */
	private Long orderLeast;
	/***
	 * n张每人
	 * */
	private Integer unit;

	private String productRange;

	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}

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
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getSupportProducts() {
		return supportProducts;
	}
	public void setSupportProducts(String supportProducts) {
		this.supportProducts = supportProducts;
	}
	public String getValidTimeFrom() {
		return validTimeFrom;
	}
	public void setValidTimeFrom(String validTimeFrom) {
		this.validTimeFrom = validTimeFrom;
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
	public String getValidTimeTo() {
		return validTimeTo;
	}
	public void setValidTimeTo(String validTimeTo) {
		this.validTimeTo = validTimeTo;
	}
	public CouponType getCouponType() {
		return couponType;
	}
	public void setCouponType(CouponType couponType) {
		this.couponType = couponType;
	}
	public String getCouponTypeDesc() {
		return couponTypeDesc;
	}
	public void setCouponTypeDesc(String couponTypeDesc) {
		this.couponTypeDesc = couponTypeDesc;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	public Integer getUsedNum() {
		return usedNum;
	}
	public void setUsedNum(Integer usedNum) {
		this.usedNum = usedNum;
	}
	public void setProductRangeType(String productRangeType) {
		this.productRangeType = productRangeType;
	}
	public String getProductRangeType() {
		return productRangeType;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public Long getOrderLeast() {
		return orderLeast;
	}
	public void setOrderLeast(Long orderLeast) {
		this.orderLeast = orderLeast;
	}
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public Integer getUnit() {
		return unit;
	}
	public void setUnit(Integer unit) {
		this.unit = unit;
	}
	public String getProductRange() {
		return productRange;
	}
	public void setProductRange(String productRange) {
		this.productRange = productRange;
	}
	public String getValidHourFrom() {
		return validHourFrom;
	}
	public void setValidHourFrom(String validHourFrom) {
		this.validHourFrom = validHourFrom;
	}
	public String getValidHourTo() {
		return validHourTo;
	}
	public void setValidHourTo(String validHourTo) {
		this.validHourTo = validHourTo;
	}
	public String getValidMinFrom() {
		return validMinFrom;
	}
	public void setValidMinFrom(String validMinFrom) {
		this.validMinFrom = validMinFrom;
	}
	public String getValidMinTo() {
		return validMinTo;
	}
	public void setValidMinTo(String validMinTo) {
		this.validMinTo = validMinTo;
	}
}
