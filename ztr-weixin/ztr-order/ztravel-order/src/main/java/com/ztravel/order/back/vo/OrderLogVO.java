package com.ztravel.order.back.vo;


/**
 * @author MH
 */
public class OrderLogVO {

	/**
	 * 操作时间
	 */
	private String operateTime;
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

	public String getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(String operateTime) {
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
