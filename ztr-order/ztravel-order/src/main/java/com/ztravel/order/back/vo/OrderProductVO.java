package com.ztravel.order.back.vo;

/**
 * 订单产品信息,用于后台自由行订单详情页面展示
 * @author MH
 */
public class OrderProductVO {
	/**
	 * 产品id
	 */
	private String productId;
	/**
	 * 产品编号
	 */
	private String productNo;
	/**
	 * 产品标题
	 */
	private String productTitle;
	/**
	 * 出发日期
	 */
	private String departureDate;
	/**
	 * 行程天数
	 */
	private Integer travelDays;
//	private String travelDays;
	/**
	 * 套餐名称
	 */
	private String packageName;
	/**
	 * 签证产品价格类型
	 */
	private String costPriceName;
	public String getCostPriceName() {
		return costPriceName;
	}
	public void setCostPriceName(String costPriceName) {
		this.costPriceName = costPriceName;
	}
	/**
	 * 碎片产品种类
	 */
    private String pieceType;

	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public String getProductTitle() {
		return productTitle;
	}
	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}
	public String getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}
	public Integer getTravelDays() {
		return travelDays;
	}
	public void setTravelDays(Integer travelDays) {
		this.travelDays = travelDays;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getPieceType() {
		return pieceType;
	}
	public void setPieceType(String pieceType) {
		this.pieceType = pieceType;
	}

}
