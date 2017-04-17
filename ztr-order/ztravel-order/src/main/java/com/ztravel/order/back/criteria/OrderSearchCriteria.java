package com.ztravel.order.back.criteria;

import javax.validation.constraints.Pattern;

import com.ztravel.common.entity.PaginationEntity;

/**
 * 自由行订单列表，订单搜索条件
 * @author MH
 */
public class OrderSearchCriteria extends PaginationEntity {
	/**
	 * 订单号
	 */
	@Pattern(regexp = "^[\\d]{0,12}$")
	private String orderNo;
	/**
	 * 产品ID
	 */
	@Pattern(regexp = "^[a-zA-Z\\d]{0,6}$")
	private String productId;
	/**
	 * 产品标题
	 */
	@Pattern(regexp = "^[\\S]{0,30}$")
	private String productTitle;
	/**
	 * 订单状态
	 */
	private String status;
	/**
	 * 订单来源
	 */
	private String source;
	/**
	 * 建单会员mid
	 */
	@Pattern(regexp = "^[\\d]{0,8}$")
	private String memberId;
	/**
	 * 建单日期(from, to)
	 */
	private String createDateFrom;

	private String createDateTo;
	/**
	 * 订单金额下限
	 */
	@Pattern(regexp = "^\\d{0,7}\\.{0,1}\\d{0,2}$")
	private String orderPriceLowerBound;
	/**
	 * 订单金额上限
	 */
	@Pattern(regexp = "^\\d{0,7}\\.{0,1}\\d{0,2}$")
	private String orderPriceUpperBound;
	/**
	 * 游客姓名
	 */
	private String travellerNames;

	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreateDateFrom() {
		return createDateFrom;
	}
	public void setCreateDateFrom(String createDateFrom) {
		this.createDateFrom = createDateFrom;
	}
	public String getCreateDateTo() {
		return createDateTo;
	}
	public void setCreateDateTo(String createDateTo) {
		this.createDateTo = createDateTo;
	}
	public String getOrderPriceLowerBound() {
		return orderPriceLowerBound;
	}
	public void setOrderPriceLowerBound(String orderPriceLowerBound) {
		this.orderPriceLowerBound = orderPriceLowerBound;
	}
	public String getOrderPriceUpperBound() {
		return orderPriceUpperBound;
	}
	public void setOrderPriceUpperBound(String orderPriceUpperBound) {
		this.orderPriceUpperBound = orderPriceUpperBound;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
    public String getTravellerNames() {
        return travellerNames;
    }
    public void setTravellerNames(String travellerNames) {
        this.travellerNames = travellerNames;
    }

}
