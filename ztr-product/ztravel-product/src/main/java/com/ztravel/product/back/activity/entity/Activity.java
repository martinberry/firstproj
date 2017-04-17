package com.ztravel.product.back.activity.entity;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;

import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;
import com.ztravel.common.enums.ActivityStatus;
import com.ztravel.common.enums.ActivityType;
import com.ztravel.common.enums.GrantType;
import com.ztravel.common.enums.UserRangeType;

@Entity(value="activity", noClassnameStored=true)
public class Activity {

	@Id
	private ObjectId id;

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
	private DateTime startTime;
	/**
	 * 活动结束时间
	 * */
	private DateTime endTime;
	/**
	 * 用户范围
	 * */
	private List<String> userRange;
	/**
	 * 关联的代金券
	 * */
	private Map<String,Coupon> coupons;
	/**
	 * 活动状态
	 * */
	private  ActivityStatus status;
	/**
	 *活动说明
	 * */
	private String remark;
	/**
	 *活动类型
	 * */
	private ActivityType type;
	/**
	 *最后操作人
	 * */
	private String operator;
	/**
	 *停止发放时间
	 * */
	private DateTime stopGrantTime;
	/**
	 *开始发放时间
	 * */
	private DateTime startGrantTime;
	/**
	 *最后更新时间
	 * */
	private DateTime updateTime;
	/**
	 *活动创建人
	 * */
	private String creator;
	/**
	 *活动创建时间
	 * */
	private DateTime createTime;
	/**
	 * 批量设置或手动添加设置
	 * */
	private GrantType grantType;
	/**
	 *用户范围类型:现有用户/活动期间新增用户
	 * */
	private UserRangeType userRangType;
	/**
	 * 冗余字段,便于活动列表根据券名进行活动查询,名字之间以逗号隔开
	 * */
	private String couponNameString;

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
	public DateTime getStartTime() {
		return startTime;
	}
	public void setStartTime(DateTime startTime) {
		this.startTime = startTime;
	}
	public DateTime getEndTime() {
		return endTime;
	}
	public void setEndTime(DateTime endTime) {
		this.endTime = endTime;
	}
	public List<String> getUserRange() {
		return userRange;
	}
	public void setUserRange(List<String> userRange) {
		this.userRange = userRange;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public DateTime getStopGrantTime() {
		return stopGrantTime;
	}
	public void setStopGrantTime(DateTime stopGrantTime) {
		this.stopGrantTime = stopGrantTime;
	}
	public DateTime getStartGrantTime() {
		return startGrantTime;
	}
	public void setStartGrantTime(DateTime startGrantTime) {
		this.startGrantTime = startGrantTime;
	}
	public DateTime getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(DateTime updateTime) {
		this.updateTime = updateTime;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public DateTime getCreateTime() {
		return createTime;
	}
	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime;
	}
	public GrantType getGrantType() {
		return grantType;
	}
	public void setGrantType(GrantType grantType) {
		this.grantType = grantType;
	}
	public UserRangeType getUserRangType() {
		return userRangType;
	}
	public void setUserRangType(UserRangeType userRangType) {
		this.userRangType = userRangType;
	}
	public ActivityType getType() {
		return type;
	}
	public void setType(ActivityType type) {
		this.type = type;
	}
	public ActivityStatus getStatus() {
		return status;
	}
	public void setStatus(ActivityStatus status) {
		this.status = status;
	}
	public Map<String, Coupon> getCoupons() {
		return coupons;
	}
	public void setCoupons(Map<String, Coupon> coupons) {
		this.coupons = coupons;
	}
	public String getCouponNameString() {
		return couponNameString;
	}
	public void setCouponNameString(String couponNameString) {
		this.couponNameString = couponNameString;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public ObjectId getId() {
		return id;
	}
}
