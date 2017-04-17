package com.ztravel.common.entity;

import com.ztravel.common.enums.ProductRangeType;


public class CouponSnapshot {

	private String activityId ;
	private long amount ;
	private String description ;
	private String name ;
	private String couponId ;

	//yyyy-MM-dd HH:mm:ss
	private String validDateFrom ;

	//yyyy-MM-dd HH:mm:ss
	private String validDateTo ;

	/**
     * 产品范围
     * */
    private ProductRangeType productRangeType;
    /**
     * 使用条件,订单满多少消费才可使用
     * */
    private Long orderLeast;

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String getValidDateFrom() {
		return validDateFrom;
	}

	public void setValidDateFrom(String validDateFrom) {
		this.validDateFrom = validDateFrom;
	}

	public String getValidDateTo() {
		return validDateTo;
	}

	public void setValidDateTo(String validDateTo) {
		this.validDateTo = validDateTo;
	}

    public ProductRangeType getProductRangeType() {
        return productRangeType;
    }

    public void setProductRangeType(ProductRangeType productRangeType) {
        this.productRangeType = productRangeType;
    }

    public Long getOrderLeast() {
        return orderLeast;
    }

    public void setOrderLeast(Long orderLeast) {
        this.orderLeast = orderLeast;
    }



}
