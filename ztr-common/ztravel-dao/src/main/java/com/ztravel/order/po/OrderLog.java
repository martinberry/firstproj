package com.ztravel.order.po;

import java.util.Date;

public class OrderLog {
	private String id;
	/**
	 * 订单id
	 */
	private String orderId;
	/**
	 * 操作时间
	 */
	private Date operateTime;
	/**
	 * 操作员
	 */
	private String operator;
	/**
	 * 操作内容
	 */
	private String content;
	/**
	 * 操作备注
	 */
	private String remark;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
