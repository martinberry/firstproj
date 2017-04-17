package com.ztravel.order.po;

import java.util.Date;

public class OrderProduct {

	/**
	 * 快照ID
	 * */
    private String snapshotId;

    /**
	 * 订单ID
	 * */
    private String orderId;

    /**
     * 产品ID
     * */
    private String productId;

    /**
     * 产品编号
     * */
    private String productNo;

    /**
     * 产品名称
     * */
    private String productTitle;

    /**
     * 产品类型
     * */
    private String productType;

    /**
     * 预订日期
     * */
    private Date bookDate;

    /**
     * 返程日期
     * */
    private Date backDate;

    /**
     * 产品快照
     * */
    private String productSnapshot;

    /**
     * 产品描述第一张图片的ID
     * */
    private String imageId;

    /**
     * 产品供应商信息
     * */
    private String providerInfo;

    private Integer tripDays;

	/**
	 *套餐名称
	 */
	private String packageName;
	/**
	 *套餐ID
	 */
	private String packageId;

    /**visa 价格类型
	 */
    private String costPriceName;
    /**
	 *产品种类(目前为止在售有自由行,当地游,签证)
	 *
	 */
    private String pieceType;


    public String getSnapshotId() {
        return snapshotId;
    }

    public void setSnapshotId(String snapshotId) {
        this.snapshotId = snapshotId == null ? null : snapshotId.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle == null ? null : productTitle.trim();
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType == null ? null : productType.trim();
    }

    public String getProductSnapshot() {
        return productSnapshot;
    }

    public void setProductSnapshot(String productSnapshot) {
        this.productSnapshot = productSnapshot == null ? null : productSnapshot.trim();
    }

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getProviderInfo() {
		return providerInfo;
	}

	public void setProviderInfo(String providerInfo) {
		this.providerInfo = providerInfo;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public Integer getTripDays() {
		return tripDays;
	}

	public void setTripDays(Integer tripDays) {
		this.tripDays = tripDays;
	}

	public Date getBookDate() {
		return bookDate;
	}

	public void setBookDate(Date bookDate) {
		this.bookDate = bookDate;
	}

	public Date getBackDate() {
		return backDate;
	}

	public void setBackDate(Date backDate) {
		this.backDate = backDate;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getPieceType() {
		return pieceType;
	}

	public void setPieceType(String pieceType) {
		this.pieceType = pieceType;
	}

	public String getCostPriceName() {
		return costPriceName;
	}

	public void setCostPriceName(String costPriceName) {
		this.costPriceName = costPriceName;
	}


}