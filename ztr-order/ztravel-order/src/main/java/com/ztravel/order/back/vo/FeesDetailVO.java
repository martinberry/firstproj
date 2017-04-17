package com.ztravel.order.back.vo;

import java.util.Map;

/**
 * 费用详情
 * @author MH
 */
public class FeesDetailVO {
	/**
	 * 成人单价
	 */
	private String adultPrice;
	/**
	 * 儿童单价
	 */
	private String childPrice;
	/**
	 * 单房差价
	 */
	private String singleRoomSupplement;
	/**
	 * 成人数量
	 */
	private String adultNum;
	/**
	 * 儿童数量
	 */
	private String childNum;
	/**
	 * 单房差数量
	 */
	private String singleRoomNum;
	/**
	 * 成人总价
	 */
	private String totalAdultPrice;
	/**
	 * 儿童总价
	 */
	private String totalChildPrice;
	/**
	 * 单房差总价
	 */
	private String totalSingleSupplement;
	/**
	 * 代金券名称
	 */
	private String couponName;
	/**
	 * 代金券抵消金额
	 */
	private String couponPrice;
	/**
	 * 应付金额
	 */
	private String payPrice;
	/**
	 * 套餐名称
	 */
	private String packageName;
	/**
	 * 套餐单价
	 */
	private String packagePrice;
	/**
	 * 套餐数量
	 */
	private String packageNum;
	/**
	 * 套餐总价
	 */
	private String packageTotalPrice;
	/**
	 * 支付信息
	 * @return
	 */
	private Map<String, String> paidInfo;

	public String getAdultPrice() {
		return adultPrice;
	}
	public void setAdultPrice(String adultPrice) {
		this.adultPrice = adultPrice;
	}
	public String getChildPrice() {
		return childPrice;
	}
	public void setChildPrice(String childPrice) {
		this.childPrice = childPrice;
	}
	public String getSingleRoomSupplement() {
		return singleRoomSupplement;
	}
	public void setSingleRoomSupplement(String singleRoomSupplement) {
		this.singleRoomSupplement = singleRoomSupplement;
	}
	public String getAdultNum() {
		return adultNum;
	}
	public void setAdultNum(String adultNum) {
		this.adultNum = adultNum;
	}
	public String getChildNum() {
		return childNum;
	}
	public void setChildNum(String childNum) {
		this.childNum = childNum;
	}
	public String getSingleRoomNum() {
		return singleRoomNum;
	}
	public void setSingleRoomNum(String singleRoomNum) {
		this.singleRoomNum = singleRoomNum;
	}
	public String getTotalAdultPrice() {
		return totalAdultPrice;
	}
	public void setTotalAdultPrice(String totalAdultPrice) {
		this.totalAdultPrice = totalAdultPrice;
	}
	public String getTotalChildPrice() {
		return totalChildPrice;
	}
	public void setTotalChildPrice(String totalChildPrice) {
		this.totalChildPrice = totalChildPrice;
	}
	public String getTotalSingleSupplement() {
		return totalSingleSupplement;
	}
	public void setTotalSingleSupplement(String totalSingleSupplement) {
		this.totalSingleSupplement = totalSingleSupplement;
	}
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	public String getCouponPrice() {
		return couponPrice;
	}
	public void setCouponPrice(String couponPrice) {
		this.couponPrice = couponPrice;
	}
	public String getPayPrice() {
		return payPrice;
	}
	public void setPayPrice(String payPrice) {
		this.payPrice = payPrice;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getPackagePrice() {
		return packagePrice;
	}
	public void setPackagePrice(String packagePrice) {
		this.packagePrice = packagePrice;
	}
	public String getPackageNum() {
		return packageNum;
	}
	public void setPackageNum(String packageNum) {
		this.packageNum = packageNum;
	}
	public String getPackageTotalPrice() {
		return packageTotalPrice;
	}
	public void setPackageTotalPrice(String packageTotalPrice) {
		this.packageTotalPrice = packageTotalPrice;
	}
	public Map<String, String> getPaidInfo() {
		return paidInfo;
	}
	public void setPaidInfo(Map<String, String> paidInfo) {
		this.paidInfo = paidInfo;
	}
}
