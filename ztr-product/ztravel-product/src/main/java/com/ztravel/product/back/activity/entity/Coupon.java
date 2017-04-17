package com.ztravel.product.back.activity.entity;

import java.util.List;

import org.joda.time.DateTime;

import com.ztravel.common.enums.CouponStatus;
import com.ztravel.common.enums.CouponType;
import com.ztravel.common.enums.ProductRangeType;

public class Coupon {

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
	 * 支持产品
	 * */
	private List<String> supportProducts;
	/**
	 * 代金券的发放时间
	 * */
	private DateTime grantTime;
	/**
	 * 代金券有效起始时间
	 * */
	private DateTime validTimeFrom;
	/**
	 * 代金券有效结束时间
	 * */
	private DateTime validTimeTo;
	/**
	 * 代金券创建时间
	 * */
	private DateTime created;
	/**
	 * 代金券更新时间
	 * */
	private DateTime updated;
	/**
	 * 代金券类型
	 * */
	private CouponType couponType;
	/**
	 * 代金券发放状态
	 * */
	private CouponStatus status;
	/**
	 * 代金券创建人
	 * */
	private String createdBy;
	/**
	 * 代金券最近更新人
	 * */
	private String updatedby;
	/**
	 * 产品范围
	 * */
	private ProductRangeType productRangeType;

	/**
	 * 使用说明
	 * */
	private String description;
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

	/**
	 * 代金券逻辑删除标识,true:删除;false:未删除
	 * */
	private Boolean isDelete = false;

	/**
	 * 售价
	 * */
	private Long price;

	public DateTime getGrantTime() {
		return grantTime;
	}
	public void setGrantTime(DateTime grantTime) {
		this.grantTime = grantTime;
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
	public List<String> getSupportProducts() {
		return supportProducts;
	}
	public void setSupportProducts(List<String> supportProducts) {
		this.supportProducts = supportProducts;
	}
	public DateTime getValidTimeFrom() {
		return validTimeFrom;
	}
	public void setValidTimeFrom(DateTime validTimeFrom) {
		this.validTimeFrom = validTimeFrom;
	}
	public DateTime getValidTimeTo() {
		return validTimeTo;
	}
	public void setValidTimeTo(DateTime validTimeTo) {
		this.validTimeTo = validTimeTo;
	}
	public DateTime getCreated() {
		return created;
	}
	public void setCreated(DateTime created) {
		this.created = created;
	}
	public DateTime getUpdated() {
		return updated;
	}
	public void setUpdated(DateTime updated) {
		this.updated = updated;
	}
	public CouponType getCouponType() {
		return couponType;
	}
	public void setCouponType(CouponType couponType) {
		this.couponType = couponType;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedby() {
		return updatedby;
	}
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}
	public ProductRangeType getProductRangeType() {
		return productRangeType;
	}
	public void setProductRangeType(ProductRangeType productRangeType) {
		this.productRangeType = productRangeType;
	}
	public CouponStatus getStatus() {
		return status;
	}
	public void setStatus(CouponStatus status) {
		this.status = status;
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
	public Long getOrderLeast() {
		return orderLeast;
	}
	public void setOrderLeast(Long orderLeast) {
		this.orderLeast = orderLeast;
	}
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
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
	public Boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
}
