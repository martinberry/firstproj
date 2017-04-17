package com.ztravel.order.wx.vo;

public class OrderPriceDetailVo {

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
	 * 代金券Id
	 */
	private String couponId;

	/**
	 * 代金券名称
	 */
    private String couponName;

    private String couponSub;

    /**
     * 套餐id
     */
    private String packageId;

    /**
     * 套餐名称
     */
    private String packageName;

    /**
     * 套餐包含总人数
     */
    private Integer packageNum;

    /**
     * 套餐价格
     */
    private Double packagePrice;

    /**
     * 套餐人均价格
     */
    private String packagePerPrice;

    /**
     * 套餐总价
     */
    private String totalPackagePrice;

    /**
     * 套餐包含总人数
     */
    private Integer sumNum;

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

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public String getCouponSub() {
		return couponSub;
	}

	public void setCouponSub(String couponSub) {
		this.couponSub = couponSub;
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


	public Double getPackagePrice() {
		return packagePrice;
	}

	public void setPackagePrice(Double packagePrice) {
		this.packagePrice = packagePrice;
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

	public void setPackagePerPrice(String packagePerPrice) {
		this.packagePerPrice = packagePerPrice;
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
}
