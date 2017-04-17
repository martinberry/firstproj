package com.ztravel.reuse.product.entity;

import java.util.List;
import java.util.Map;

import com.ztravel.common.entity.AdditionalProduct;
import com.ztravel.common.entity.Attachment;
import com.ztravel.common.enums.AdditionalRule;

public class ProductBookVo {
	/**
	 * 产品id
	 */
	private String productId;

	/**
	 * 产品标题
	 */
	private String productTitle;

	/**
	 *产品第一张图片ID
	 * */
	private String firstImageId;
	/**
	 * 产品类型
	 */
	private String productType;

	/**
	 * 出行日期
	 */
	private String departDay;

	/***
	 * 产品编号
	 * */
	private String productNo;

	/***
	 * 是否包含机票
	 * */
	private Boolean isContainFlight;

	/**
	 * 是否包含酒店信息
	 * **/
	private Boolean isContainHotel;

	/**
	 * 返回日期
	 */
	private String returnDay;

	/**
	 * domestic-国内
	 * international-国际
	 */
	private String isDomestic;

	/**
	 * 成人数
	 * */
	private Integer adultNum;
	/**
	 *儿童数
	 * */
	private Integer childNum;

	/**
	 * 房差数
	 * */
	private Integer singleNum;

	/**
	 *成人总价
	 * */
	private String totalAdultPrice;

	/**
	 * 儿童总价
	 * **/
	private String totalChildPrice;

	/**
	 * 总房差价
	 * */
	private String totalSingleDiff;

	/**
	 * 订单总价
	 * */
	private String totalPrice;

	/**
	 * 	去程航班
	 * */
	private List<ProductFlightInfo> goFlightList;

	/***
	 * 返程航班
	 * */
	private List<ProductFlightInfo> backFlightList;

	/**
	 * 中间航班
	 * */
	private List<ProductFlightInfo> midlFlightList;

	/**
	 * 酒店信息
	 */
	private List<ProductHotelInfo> hotelList;

	/**
	 * 成人票价
	 */
	private String adultPrice;

	/**
	 * 儿童票价
	 */
	private String childrenPrice;


	/**
	 * 酒店单房差
	 */
	private String singleRoomDiff;

	/**
	 * 床型偏好
	 * */
	private String bedPrefer;

	/**
	 * 产品行程天数
	 * */
	private Integer tripDays;
	/**
	 * 产品性质
	 * */
	private String nature;

	/**
	 * 套餐id
	 */
	private String packageId;

    /**
     * 套餐名称
     */
    private String packageName;

	/**
	 * 选择套餐份数（默认一份）
	 */
    private Integer packageNum = 1;

    /**
     * 套餐人均价格
     */
    private String packagePerPrice;

    /**
     * 套餐价格
     */
    private String packagePrice;

    /**
     * 套餐总价
     */
    private String totalPackagePrice;

    /**
     * 套餐包含总人数
     */
    private Integer sumNum;

	private Map<AdditionalRule, String> additionalInfos ;

	/**
	 * 订单附加产品
	 */
    private List<AdditionalProduct> additionalProducts;

    /**
     * 订单附件
     */
    private List<Attachment> attachments;

    /**
     * 是否添加双床房提示
     * */
    private Boolean isBedTips;

    private String costPriceId;

    /**visa 价格类型
	 */
    private String costPriceName;

    /**
 	 *产品种类(目前为止在售有自由行,当地游,签证)
 	 *
 	 */
    private String pieceType;



	public String getBedPrefer() {
		return bedPrefer;
	}


	public void setBedPrefer(String bedPrefer) {
		this.bedPrefer = bedPrefer;
	}


	public String getProductId() {
		return productId;
	}


	public void setProductId(String productId) {
		this.productId = productId;
	}


	public String getProductTitle() {
		return productTitle;
	}


	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}


	public String getProductType() {
		return productType;
	}


	public void setProductType(String productType) {
		this.productType = productType;
	}


	public String getDepartDay() {
		return departDay;
	}


	public void setDepartDay(String departDay) {
		this.departDay = departDay;
	}


	public String getReturnDay() {
		return returnDay;
	}


	public void setReturnDay(String returnDay) {
		this.returnDay = returnDay;
	}


	public String getIsDomestic() {
		return isDomestic;
	}


	public void setIsDomestic(String isDomestic) {
		this.isDomestic = isDomestic;
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


	public List<ProductFlightInfo> getGoFlightList() {
		return goFlightList;
	}


	public void setGoFlightList(List<ProductFlightInfo> goFlightList) {
		this.goFlightList = goFlightList;
	}


	public List<ProductFlightInfo> getBackFlightList() {
		return backFlightList;
	}


	public void setBackFlightList(List<ProductFlightInfo> backFlightList) {
		this.backFlightList = backFlightList;
	}


	public List<ProductFlightInfo> getMidlFlightList() {
		return midlFlightList;
	}


	public void setMidlFlightList(List<ProductFlightInfo> midlFlightList) {
		this.midlFlightList = midlFlightList;
	}


	public List<ProductHotelInfo> getHotelList() {
		return hotelList;
	}


	public void setHotelList(List<ProductHotelInfo> hotelList) {
		this.hotelList = hotelList;
	}


	public String getAdultPrice() {
		return adultPrice;
	}


	public void setAdultPrice(String adultPrice) {
		this.adultPrice = adultPrice;
	}


	public String getChildrenPrice() {
		return childrenPrice;
	}


	public void setChildrenPrice(String childrenPrice) {
		this.childrenPrice = childrenPrice;
	}


	public String getSingleRoomDiff() {
		return singleRoomDiff;
	}


	public void setSingleRoomDiff(String singleRoomDiff) {
		this.singleRoomDiff = singleRoomDiff;
	}


	public Boolean getIsContainFlight() {
		return isContainFlight;
	}


	public void setIsContainFlight(Boolean isContainFlight) {
		this.isContainFlight = isContainFlight;
	}


	public Boolean getIsContainHotel() {
		return isContainHotel;
	}


	public void setIsContainHotel(Boolean isContainHotel) {
		this.isContainHotel = isContainHotel;
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


	public String getTotalSingleDiff() {
		return totalSingleDiff;
	}


	public void setTotalSingleDiff(String totalSingleDiff) {
		this.totalSingleDiff = totalSingleDiff;
	}


	public Integer getSingleNum() {
		return singleNum;
	}


	public void setSingleNum(Integer singleNum) {
		this.singleNum = singleNum;
	}


	public String getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getFirstImageId() {
		return firstImageId;
	}


	public void setFirstImageId(String firstImageId) {
		this.firstImageId = firstImageId;
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


	public String getNature() {
		return nature;
	}


	public void setNature(String nature) {
		this.nature = nature;
	}


	public Map<AdditionalRule, String> getAdditionalInfos() {
		return additionalInfos;
	}


	public void setAdditionalInfos(Map<AdditionalRule, String> additionalInfos) {
		this.additionalInfos = additionalInfos;
	}


    public String getPackageId() {
        return packageId;
    }


    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }


    public String getPackageName() {
        return packageName;
    }


    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }


    public Integer getPackageNum() {
        return packageNum;
    }


    public void setPackageNum(Integer packageNum) {
        this.packageNum = packageNum;
    }


    public String getTotalPackagePrice() {
        return totalPackagePrice;
    }


    public void setTotalPackagePrice(String totalPackagePrice) {
        this.totalPackagePrice = totalPackagePrice;
    }


    public String getPackagePrice() {
        return packagePrice;
    }


    public void setPackagePrice(String packagePrice) {
        this.packagePrice = packagePrice;
    }


    public Integer getSumNum() {
        return sumNum;
    }


    public void setSumNum(Integer sumNum) {
        this.sumNum = sumNum;
    }


    public String getPackagePerPrice() {
        return packagePerPrice;
    }


    public void setPackagePerPrice(String packagePerPrice) {
        this.packagePerPrice = packagePerPrice;
    }


    public List<AdditionalProduct> getAdditionalProducts() {
        return additionalProducts;
    }


    public void setAdditionalProducts(List<AdditionalProduct> additionalProducts) {
        this.additionalProducts = additionalProducts;
    }


    public List<Attachment> getAttachments() {
        return attachments;
    }


    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }


	public Boolean getIsBedTips() {
		return isBedTips;
	}


	public void setIsBedTips(Boolean isBedTips) {
		this.isBedTips = isBedTips;
	}


	public String getCostPriceId() {
		return costPriceId;
	}


	public void setCostPriceId(String costPriceId) {
		this.costPriceId = costPriceId;
	}


	public String getCostPriceName() {
		return costPriceName;
	}


	public void setCostPriceName(String costPriceName) {
		this.costPriceName = costPriceName;
	}
	public String getPieceType() {
		return pieceType;
	}


	public void setPieceType(String pieceType) {
		this.pieceType = pieceType;
	}
}
