package com.ztravel.order.po;

import java.util.Date;

import com.ztravel.common.enums.CommonOrderStatus;

/**
 * t_order与t_order_product表连接查询结果
 * @author MH
 */
public class OrderPO {
	/**
	 * 订单id(主键)
	 */
	private String orderId;
	/**
	 * 订单编号
	 */
	private String orderNo;
	/**
	 * 订单状态(后台状态)
	 */
	private String backState;
	/**
	 * 订单来源
	 */
	private String orderFrom;
	/**
	 * 建单时间
	 */
	private Date createDate;
	/**
	 * 建单会员mid
	 */
	private String creator;
	/**
	 * 游客姓名
	 */
    private String travellerNames;
	/**
	 * 订单总价
	 */
	private Long totalPrice;
	/**
	 * 最近操作人
	 */
	private String operator;
	/**
	 * 产品编号
	 */
	private String productNo;
	/**
	 * 产品标题
	 */
	private String productTitle;
	/**
	 *套餐名称
	 */
	private String packageName;
	/**
	 * 子订单状态
	 */
	private CommonOrderStatus commonOrderStatus;
	/**
	 * 产品快照
	 */
	private String productSnapshot;
	/**
	 * 联系人姓名
	 */
    private String contactorName;
    /**
	 * 支付日期
	 */
    private Date payTime;
    /**
   	 * 支付状态
   	 */
    private String payStatus;
    

	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getProductSnapshot() {
		return productSnapshot;
	}
	public void setProductSnapshot(String productSnapshot) {
		this.productSnapshot = productSnapshot;
	}
    
	public String getContactorName() {
		return contactorName;
	}
	public void setContactorName(String contactorName) {
		this.contactorName = contactorName;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
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
	public String getBackState() {
		return backState;
	}
	public void setBackState(String backState) {
		this.backState = backState;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Long getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
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
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getOrderFrom() {
		return orderFrom;
	}
	public void setOrderFrom(String orderFrom) {
		this.orderFrom = orderFrom;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
    public String getTravellerNames() {
        return travellerNames;
    }
    public void setTravellerNames(String travellerNames) {
        this.travellerNames = travellerNames;
    }
    public CommonOrderStatus getCommonOrderStatus() {
        return commonOrderStatus;
    }
    public void setCommonOrderStatus(CommonOrderStatus commonOrderStatus) {
        this.commonOrderStatus = commonOrderStatus;
    }

}
