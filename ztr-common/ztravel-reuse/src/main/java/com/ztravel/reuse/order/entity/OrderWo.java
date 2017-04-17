package com.ztravel.reuse.order.entity;

public class OrderWo {

	/***
	 * 订单ID
	 * */
	private String orderId;

	/***
	 * 订单编号
	 * */
    private String orderNo;

    /***
	 * 订单类型:国际产品订单/国内产品订单
	 * */
    private String orderType;

    /***
	 *建单时间
	 * */
    private String createDate;

    /***
	 *建单人:member ID
	 * */
    private String creator;

    /***
	 *订单总价
	 * */
    private String totalPrice;

    /***
	 *订单应付金额
	 * */
    private String payAmount;

    /***
	 *订单前端状态
	 * */
    private String frontState;

    /***
	 *订单后台状态
	 * */
    private String backState;

    /**
     * 订单下一步操作
     */
    private String nextStep;

    /**
     * 是否可评价(订单状态变为“已完成”44小时后 且 未评价 的订单,可评价)
     */
    private Boolean canComment;

    /***
	 *订单处理进度
	 * */
    private Integer progress;

    /**
     * 使用代金券抵消的金额
     * */
    private String couponSub;

    /***
   	 *使用积分抵消的金额
   	 * */
    private String integralSub;

    /***
   	 *优惠总金额
   	 * */
    private String discountTotalSub;

	/**
	 *套餐名称
	 */
	private String packageName;
	/**
	 *套餐ID
	 */
	private String packageId;

	private String commonOrderId;

	private String commonOrderPrice;

	private String commonOrderStatus;
	
	private String productNature;

	public String getProductNature() {
		return productNature;
	}

	public void setProductNature(String productNature) {
		this.productNature = productNature;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public String getFrontState() {
		return frontState;
	}

	public void setFrontState(String frontState) {
		this.frontState = frontState;
	}

	public String getBackState() {
		return backState;
	}

	public void setBackState(String backState) {
		this.backState = backState;
	}

	public String getNextStep() {
		return nextStep;
	}

	public void setNextStep(String nextStep) {
		this.nextStep = nextStep;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public String getCouponSub() {
		return couponSub;
	}

	public void setCouponSub(String couponSub) {
		this.couponSub = couponSub;
	}

	public String getIntegralSub() {
		return integralSub;
	}

	public void setIntegralSub(String integralSub) {
		this.integralSub = integralSub;
	}

	public String getDiscountTotalSub() {
		return discountTotalSub;
	}

	public void setDiscountTotalSub(String discountTotalSub) {
		this.discountTotalSub = discountTotalSub;
	}

	public Boolean getCanComment() {
		return canComment;
	}

	public void setCanComment(Boolean canComment) {
		this.canComment = canComment;
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


	public String getCommonOrderId() {
		return commonOrderId;
	}

	public void setCommonOrderId(String commonOrderId) {
		this.commonOrderId = commonOrderId;
	}

	public String getCommonOrderPrice() {
		return commonOrderPrice;
	}

	public void setCommonOrderPrice(String commonOrderPrice) {
		this.commonOrderPrice = commonOrderPrice;
	}

	public String getCommonOrderStatus() {
		return commonOrderStatus;
	}

	public void setCommonOrderStatus(String commonOrderStatus) {
		this.commonOrderStatus = commonOrderStatus;
	}


}
