package com.ztravel.product.back.activity.vo;

import java.util.Map;

import com.ztravel.product.back.activity.entity.Coupon;


public class ActivityVo{
	/**
	 * 产品ID
	 * */
	private String id;
	/**
	 * 活动名称
	 * */
	private String name;
	/**
	 * 活动编号
	 * */
	private String code;
	/**
	 * 活动起始时间
	 * */
	private String startTime;
	/**
	 * 活动结束时间
	 * */
	private String endTime;

	private String startHourFrom;

	private String startMinFrom;

	private String endHourTo;

	private String endMinTo;
	/**
	 * 用户范围,在列表中显示时,会有省略号
	 * */
	private String userRange;

	/**
	 * 完整的用户范围信息
	 * */
	private String userRangeComplete;
	/**
	 * 关联的代金券
	 * */
	private Map<String,Coupon> coupons;
	/**
	 * 活动状态
	 * */
	private  String status;
	/**
	 * 活动状态描述
	 * */
	private String statusDesc;
	/**
	 *活动说明
	 * */
	private String remark;
	/**
	 * 批量设置或手动添加设置 true:批量设置;false:手动添加设置
	 * */
	private String grantType;
	/**
	 *用户范围类型:CURRENTALL:现有用户;NEWUSER:活动期间新增用户
	 * */
	private String userRangType;

	private String type;

	private String typeDesc;

	/**
	 *冗余字段,便于活动列表展示活动券名
	 * */
	private String couponNames;

	private String operator;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getUserRange() {
		return userRange;
	}
	public void setUserRange(String userRange) {
		this.userRange = userRange;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUserRangType() {
		return userRangType;
	}
	public void setUserRangType(String userRangType) {
		this.userRangType = userRangType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Map<String, Coupon> getCoupons() {
		return coupons;
	}
	public void setCoupons(Map<String, Coupon> coupons) {
		this.coupons = coupons;
	}
	public String getCouponNames() {
		return couponNames;
	}
	public void setCouponNames(String couponNames) {
		this.couponNames = couponNames;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getTypeDesc() {
		return typeDesc;
	}
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}
	public String getGrantType() {
		return grantType;
	}
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}
	public String getUserRangeComplete() {
		return userRangeComplete;
	}
	public void setUserRangeComplete(String userRangeComplete) {
		this.userRangeComplete = userRangeComplete;
	}
	public String getStartHourFrom() {
		return startHourFrom;
	}
	public void setStartHourFrom(String startHourFrom) {
		this.startHourFrom = startHourFrom;
	}
	public String getStartMinFrom() {
		return startMinFrom;
	}
	public void setStartMinFrom(String startMinFrom) {
		this.startMinFrom = startMinFrom;
	}
	public String getEndHourTo() {
		return endHourTo;
	}
	public void setEndHourTo(String endHourTo) {
		this.endHourTo = endHourTo;
	}
	public String getEndMinTo() {
		return endMinTo;
	}
	public void setEndMinTo(String endMinTo) {
		this.endMinTo = endMinTo;
	}
}
