package com.ztravel.reuse.order.entity;

import java.util.List;
import java.util.Map;

import com.ztravel.reuse.product.entity.ProductFlightInfo;
import com.ztravel.reuse.product.entity.ProductHotelInfo;


public class OrderProductWo {

    /**
     * 产品ID
     * */
    private String productId;
    /**
     * 产品编号
     */
    private String productNo;
    /**
     * 行程天数
     */
    private Integer tripDays;
    /**
     * 产品名称
     * */
    private String productTitle;

    /**
     * 产品类型
     * */
//    private String productType;

    /**
     * 预订日期
     * */
    private String bookDate;

    /**
     * 返程日期
     */
    private String backDate;

    /**
     * 产品描述第一张图片的ID
     * */
    private String imageId;

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

	private Map<String, String> additionalInfos ;

	 /**
     * 套餐id
     */
    private String packageId;

    /**
     * 套餐名称
     */
    private String packageName;
    /**
     * 供应商
     */
    private String supplier;
    /**
     * 产品快照
     */
    private String productSnapshot;

	/**visa 价格类型
	 */
    private String costPriceName;
    /**
	 *产品种类(目前为止在售有自由行,当地游,签证)
	 *
	 */
    private String pieceType;
    
    private Boolean isBedTips;
    
    
    
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

	public String getBookDate() {
		return bookDate;
	}

	public void setBookDate(String bookDate) {
		this.bookDate = bookDate;
	}

	public String getBackDate() {
		return backDate;
	}

	public void setBackDate(String backDate) {
		this.backDate = backDate;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
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

	public Map<String, String> getAdditionalInfos() {
		return additionalInfos;
	}

	public void setAdditionalInfos(Map<String, String> additionalInfos) {
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

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
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

	public String getProductSnapshot() {
		return productSnapshot;
	}

	public void setProductSnapshot(String productSnapshot) {
		this.productSnapshot = productSnapshot;
	}

	public Boolean getIsBedTips() {
		return isBedTips;
	}

	public void setIsBedTips(Boolean isBedTips) {
		this.isBedTips = isBedTips;
	}


}
