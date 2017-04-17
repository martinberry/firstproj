package com.ztravel.order.back.vo;

import java.util.List;


/**
 * 用于后台自由行订单列表页面展示
 * @author MH
 */
public class OrderListVO {
	/**
	 * 订单id
	 */
	private String orderId;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 订单状态
	 */
	private String status;
	/**
	 * 订单来源
	 */
	private String source;
	/**
	 * 产品id(字母开头+5位数字)
	 */
	private String productNo;
	/**
	 * 产品标题
	 */
	private String productTitle;
	/**
	 * 创建时间
	 */
	private String createDate;
	/**
	 * 建单会员mid
	 */
	private String creatorMid;
	/**
	 * 游客姓名
	 */
	private String travellerNames;
	/**
	 * 订单金额
	 */
	private String orderTotalPrice;
	/**
	 * 最后操作人
	 */
	private String lastOperator;
	/**
	 * 子订单状态
	 */
	private String commonOrderStatus;
	/**
	 * 套餐名称
	 */
	private String packageName;
	
	private List<String> contactorNameList;
	
	
	public List<String> getContactorNameList() {
		return contactorNameList;
	}
	public void setContactorNameList(List<String> contactorNameList) {
		this.contactorNameList = contactorNameList;
	}
	

	private String contactorName;
	private String payTime;
	private String costPrice;
	
	
	public String getContactorName() {
		return contactorName;
	}
	public void setContactorName(String contactorName) {
		this.contactorName = contactorName;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public String getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(String costPrice) {
		this.costPrice = costPrice;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getOrderTotalPrice() {
		return orderTotalPrice;
	}
	public void setOrderTotalPrice(String orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}
	public String getLastOperator() {
		return lastOperator;
	}
	public void setLastOperator(String lastOperator) {
		this.lastOperator = lastOperator;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getCreatorMid() {
		return creatorMid;
	}
	public void setCreatorMid(String creatorMid) {
		this.creatorMid = creatorMid;
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
    public String getCommonOrderStatus() {
        return commonOrderStatus;
    }
    public void setCommonOrderStatus(String commonOrderStatus) {
        this.commonOrderStatus = commonOrderStatus;
    }

}
