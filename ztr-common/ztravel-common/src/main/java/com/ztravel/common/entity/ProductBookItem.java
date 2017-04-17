package com.ztravel.common.entity;

import java.util.List;

/**
 * 产品预订条目
 * @author liuzhuo
 *
 */
public class ProductBookItem {

	/**
	 * 产品id
	 */
	private String productId;

	/**
	 *
	 * */
	private String firstImageId;
	/**
	 * 产品编号
	 * */
	private String productNo;
	/**
	 * 产品类型
	 */
	private String productType;

	/**
	 * 产品标题
	 */
	private String productTitle;

	/**
	 * 预订日期
	 */
	private String bookDate;

	/**
	 * 成人数量
	 */
	private Integer adaultNum;

	/**
	 * 儿童数量
	 */
	private Integer childrenNum;

	/**
	 * 总价
	 */
	private Long totalPrice;

	/**
	 * 应付金额
	 * */
	private Long payAmount;

	/**
	 * 乘客信息
	 */
	private List<PassengerInfo> passengerList;

	/**
	 * 联系人信息
	 */
	private ContactorInfo contactorInfo;

	/**
	 * 需求备注
	 */
	private String remark;

	/**
	 * 折扣券id
	 */
	private String discountcouponId;

	private String orderFrom;

	/**
	 * 积分使用数量
	 */
	private Integer integral;

	/**
	 * 使用代金券抵消金额
	 * */
	private Long couponSub;
	/**
	 * 使用积分抵消的金额
	 * */
	private Long integralSub;
	/**
	 * 产品快照ID
	 */
	private String productShapshotId;

	/**
	 * 产品快照信息，JSONObject转换为String
	 * */

	private String shapShot;

	/**
	 * 国内，国际标识
	 * */
	private String isDomestic;

	/**
	 * 登录名
	 * */
	private String userName;

	/**
	 * 用户未登录时提交订单需输入登录密码
	 * */
	private String passWord;

	/**
	 * 床型偏好
	 * */
	private String bedPrefer;

	/**
	 * 产品行程天数
	 * */
	private Integer tripDays;

	/**
	 *产品性质
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
	 * 附加产品
	 */
	private List<AdditionalProduct> additionalProducts;
	
	/***/
	private String priceId;

	public String getBedPrefer() {
		return bedPrefer;
	}

	public void setBedPrefer(String bedPrefer) {
		this.bedPrefer = bedPrefer;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getIsDomestic() {
		return isDomestic;
	}

	public void setIsDomestic(String isDomestic) {
		this.isDomestic = isDomestic;
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

	public Integer getAdaultNum() {
		return adaultNum;
	}

	public void setAdaultNum(Integer adaultNum) {
		this.adaultNum = adaultNum;
	}

	public Integer getChildrenNum() {
		return childrenNum;
	}

	public void setChildrenNum(Integer childrenNum) {
		this.childrenNum = childrenNum;
	}


	public List<PassengerInfo> getPassengerList() {
		return passengerList;
	}

	public void setPassengerList(List<PassengerInfo> passengerList) {
		this.passengerList = passengerList;
	}

	public ContactorInfo getContactorInfo() {
		return contactorInfo;
	}

	public void setContactorInfo(ContactorInfo contactorInfo) {
		this.contactorInfo = contactorInfo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDiscountcouponId() {
		return discountcouponId;
	}

	public void setDiscountcouponId(String discountcouponId) {
		this.discountcouponId = discountcouponId;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductShapshotId() {
		return productShapshotId;
	}

	public void setProductShapshotId(String productShapshotId) {
		this.productShapshotId = productShapshotId;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	public String getShapShot() {
		return shapShot;
	}

	public void setShapShot(String shapShot) {
		this.shapShot = shapShot;
	}

	public Long getIntegralSub() {
		return integralSub;
	}

	public void setIntegralSub(Long integralSub) {
		this.integralSub = integralSub;
	}

	public Long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Long getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}

	public Long getCouponSub() {
		return couponSub;
	}

	public void setCouponSub(Long couponSub) {
		this.couponSub = couponSub;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getFirstImageId() {
		return firstImageId;
	}

	public void setFirstImageId(String firstImageId) {
		this.firstImageId = firstImageId;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrderFrom() {
		return orderFrom;
	}

	public void setOrderFrom(String orderFrom) {
		this.orderFrom = orderFrom;
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

    public List<AdditionalProduct> getAdditionalProducts() {
        return additionalProducts;
    }

    public void setAdditionalProducts(List<AdditionalProduct> additionalProducts) {
        this.additionalProducts = additionalProducts;
    }

	public String getPriceId() {
		return priceId;
	}

	public void setPriceId(String priceId) {
		this.priceId = priceId;
	}

}
