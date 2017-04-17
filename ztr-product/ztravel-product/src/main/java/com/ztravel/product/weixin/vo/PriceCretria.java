package com.ztravel.product.weixin.vo;

public class PriceCretria {

	private String productId;

	private String bookDate;

	private String couponItemId;

	private String totalPrice;

	private String couponName;

	private Long couponAmount;

	private Boolean containCoupon;

    private Integer adultNum;

    private Integer childNum;

    private Integer singleNum;

    private String adultPrice;

    private String childPrice;

    private String singlePrice;

    private String totalAdultPrice;

    private String totalChildPrice;

    private String totalSinglePrice;

    private String packageId;

    private String packageName;

	private String packagePrice;

	private String totalPackagePrice;
	
	private String productNature;
	
	private String costPriceId;

	public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(String packagePrice) {
        this.packagePrice = packagePrice;
    }

    public String getTotalPackagePrice() {
        return totalPackagePrice;
    }

    public void setTotalPackagePrice(String totalPackagePrice) {
        this.totalPackagePrice = totalPackagePrice;
    }

    public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getBookDate() {
		return bookDate;
	}

	public void setBookDate(String bookDate) {
		this.bookDate = bookDate;
	}

	public String getCouponItemId() {
		return couponItemId;
	}

	public void setCouponItemId(String couponItemId) {
		this.couponItemId = couponItemId;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public Boolean getContainCoupon() {
		return containCoupon;
	}

	public void setContainCoupon(Boolean containCoupon) {
		this.containCoupon = containCoupon;
	}

	public Long getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(Long couponAmount) {
		this.couponAmount = couponAmount;
	}

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public Integer getAdultNum() {
        return adultNum;
    }

    public void setAdultNum(Integer adultNum) {
        this.adultNum = adultNum;
    }

    public Integer getChildNum() {
        return childNum;
    }

    public void setChildNum(Integer childNum) {
        this.childNum = childNum;
    }

    public Integer getSingleNum() {
        return singleNum;
    }

    public void setSingleNum(Integer singleNum) {
        this.singleNum = singleNum;
    }

    public String getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(String adultPrice) {
        this.adultPrice = adultPrice;
    }

    public String getChildPrice() {
        return childPrice;
    }

    public void setChildPrice(String childPrice) {
        this.childPrice = childPrice;
    }

    public String getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(String singlePrice) {
        this.singlePrice = singlePrice;
    }

    public String getTotalAdultPrice() {
        return totalAdultPrice;
    }

    public void setTotalAdultPrice(String totalAdultPrice) {
        this.totalAdultPrice = totalAdultPrice;
    }

    public String getTotalChildPrice() {
        return totalChildPrice;
    }

    public void setTotalChildPrice(String totalChildPrice) {
        this.totalChildPrice = totalChildPrice;
    }

    public String getTotalSinglePrice() {
        return totalSinglePrice;
    }

    public void setTotalSinglePrice(String totalSinglePrice) {
        this.totalSinglePrice = totalSinglePrice;
    }

	public String getProductNature() {
		return productNature;
	}

	public void setProductNature(String productNature) {
		this.productNature = productNature;
	}

	public String getCostPriceId() {
		return costPriceId;
	}

	public void setCostPriceId(String costPriceId) {
		this.costPriceId = costPriceId;
	}

}
