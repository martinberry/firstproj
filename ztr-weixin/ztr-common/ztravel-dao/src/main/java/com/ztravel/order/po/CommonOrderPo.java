package com.ztravel.order.po;

import org.joda.time.DateTime;

import com.ztravel.common.enums.CommonOrderStatus;
import com.ztravel.common.enums.CommonOrderType;
import com.ztravel.common.enums.PaymentType;

public class CommonOrderPo {
	/**
	 * 订单ID
	 * */
	private String orderId;
	/**
	 * 原订单号
	 * */
	private String orderNoOrigin;
	/**
	 * 原订单状态
	 * */
	 private String backState;
	/**
	 * 原订单Id
	 * */
	private String orderIdOrigin;
	/**
	 * 现订单号
	 * */
	private String orderNoVice;
	/**
	 * 订单状态
	 * */
	private CommonOrderStatus status;
	/**
	 * 订单价格
	 * */
	private Long price;
	/**
	 * 会员Id
	 * */
	private String memberId;
	/**
	 * 建单日期
	 * */
	private DateTime createDate;
	/**
	 * 更新日期
	 * */
	private DateTime updateDate;
	/**
	 * 支付类型
	 * */
	private PaymentType payType;
	/**
	 * 支付流水号
	 * */
	private String paySerialNum;
	/**
	 * 订单类型
	 * */
	private CommonOrderType orderType;
	/**
	* 备注
	* */
	private String remark;
	/**
	* 状态变更历史
	* */
	private String stateChangeHistory;
	/**
	 * 游客姓名
	 */
    private String travellerNames;


	public String getStateChangeHistory() {
		return stateChangeHistory;
	}
	public void setStateChangeHistory(String stateChangeHistory) {
		this.stateChangeHistory = stateChangeHistory;
	}
	public String getBackState() {
		return backState;
	}
	public void setBackState(String backState) {
		this.backState = backState;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderNoOrigin() {
		return orderNoOrigin;
	}
	public void setOrderNoOrigin(String orderNoOrigin) {
		this.orderNoOrigin = orderNoOrigin;
	}
	public String getOrderNoVice() {
		return orderNoVice;
	}
	public void setOrderNoVice(String orderNoVice) {
		this.orderNoVice = orderNoVice;
	}
	public CommonOrderStatus getStatus() {
		return status;
	}
	public void setStatus(CommonOrderStatus status) {
		this.status = status;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public DateTime getCreateDate() {
		return createDate;
	}
	public void setCreateDate(DateTime createDate) {
		this.createDate = createDate;
	}
	public DateTime getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(DateTime updateDate) {
		this.updateDate = updateDate;
	}
	public PaymentType getPayType() {
		return payType;
	}
	public void setPayType(PaymentType payType) {
		this.payType = payType;
	}
	public String getPaySerialNum() {
		return paySerialNum;
	}
	public void setPaySerialNum(String paySerialNum) {
		this.paySerialNum = paySerialNum;
	}
	public CommonOrderType getType() {
		return orderType;
	}
	public void setType(CommonOrderType orderType) {
		this.orderType = orderType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOrderIdOrigin() {
		return orderIdOrigin;
	}
	public void setOrderIdOrigin(String orderIdOrigin) {
		this.orderIdOrigin = orderIdOrigin;
	}
    public String getTravellerNames() {
        return travellerNames;
    }
    public void setTravellerNames(String travellerNames) {
        this.travellerNames = travellerNames;
    }

}




