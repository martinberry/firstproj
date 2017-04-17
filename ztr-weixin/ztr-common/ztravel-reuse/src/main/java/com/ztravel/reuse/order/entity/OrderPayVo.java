package com.ztravel.reuse.order.entity;

public class OrderPayVo {
	/**
	 * 产品标题
	 */
	private String title;

	/**
	 * 产品标题
	 */
	private String memberId;
	/**
	 * 订单号
	 */
	private String orderId;

	/**
	 * 订单编号
	 * */
	private String orderCode;

	/**
	 * 应付总额
	 */
	private Long payAmount;

	/**
	 *产品总额
	 */
	private Long totalPrice;

	/**
	 *代金券
	 */
	private Long discountCoupon;

	/**
	 *积分
	 */
	private Long useRewardPoint;

	/**
	 *代金券Id
	 */
    private String couponItemId;

    private String productType;

    private String createDate;

    private String departDate ;
    
    private String orderIdOrigin;


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}




	public Long getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}


	public Long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Long getDiscountCoupon() {
		return discountCoupon;
	}

	public void setDiscountCoupon(Long discountCoupon) {
		this.discountCoupon = discountCoupon;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getCouponItemId() {
		return couponItemId;
	}

	public void setCouponItemId(String couponItemId) {
		this.couponItemId = couponItemId;
	}

	public Long getUseRewardPoint() {
		return useRewardPoint;
	}

	public void setUseRewardPoint(Long useRewardPoint) {
		this.useRewardPoint = useRewardPoint;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getDepartDate() {
		return departDate;
	}

	public void setDepartDate(String departDate) {
		this.departDate = departDate;
	}

	public String getOrderIdOrigin() {
		return orderIdOrigin;
	}

	public void setOrderIdOrigin(String orderIdOrigin) {
		this.orderIdOrigin = orderIdOrigin;
	}


}
