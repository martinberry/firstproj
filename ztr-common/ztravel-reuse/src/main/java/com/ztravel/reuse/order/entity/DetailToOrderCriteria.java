package com.ztravel.reuse.order.entity;


/**
 * 从产品详情页跳转到预订产品页需传递的参数
 * */
public class DetailToOrderCriteria {

    private String productId ;

	private String bookDate;

	private Integer adultNum;

	private Integer childNum;

	private Integer bookNum = 1; //套餐预定数量默认为：1份

	private String packageId;
	
	//
	private String productNature;
	
	private String costPriceId;

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

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public Integer getBookNum() {
        return bookNum;
    }

    public void setBookNum(Integer bookNum) {
        this.bookNum = bookNum;
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
