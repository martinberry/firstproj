package com.ztravel.product.back.hotel.wo;

import java.util.Date;
import java.util.List;

/**
 * @author tengmeilin
 * 酒店实体对外
 * */

public class HotelEntityWo {

	private String id ;

	/**
	 * 展示用
	 */
	private String showId;

	/**
	 * 中文名称
	 */
	private String hotelNameCn ;

	/**
	 * 英文名称
	 */
	private String hotelNameEn ;

	/**
	 * 类型
	 */
	private String type ;

	/**
	 * 星级
	 */
	private String rating ;

	/**
	 * 大洲区域
	 */
	private String continent ;

	/**
	 * 国家
	 */
	private String nation ;

	/**
	 * 城市/非城市包含景点
	 */
	private String city ;


	/**
	 * 地址
	 */
	private String address ;


	/**
	 * 电话
	 */
	private String phone ;

	/**
	 * 纬度
	 */
	private String lat ;

	/**
	 * 经度
	 */
	private String lon ;

	/**
	 * 综合设施
	 */
	private String compFacilities ;

	/**
	 * 餐饮设施
	 */
	private String cateringFacilities ;

	/**
	 * 网络设施
	 */
	private String networkFacilities ;

	/**
	 * 活动设施
	 */
	private String activityFacilities ;

	/**
	 * 服务项目
	 */
	private String serviceFacilities ;

	/**
	 * 图片
	 */
	private List<String> pictureIds ;

	/**
	 * 卖点
	 */
	private String advantage ;

	/**
	 * 描述
	 */
	private String describe ;

	/**
	 * 入住须知
	 */
	private String notes ;

	/**
	 * 状态
	 */
	private Boolean isComplete ;

	/**
	 * 逻辑删除
	 */
//	private Boolean isActive ;

	/**
	 * 创建时间
	 */
	private Date createTime ;

	/**
	 * 更新时间
	 */
	private Date updateTime ;

	public String getShowId() {
		return showId;
	}
	public void setShowId(String showId) {
		this.showId = showId;
	}
	public String getHotelNameCn() {
		return hotelNameCn;
	}
	public void setHotelNameCn(String hotelNameCn) {
		this.hotelNameCn = hotelNameCn;
	}
	public String getHotelNameEn() {
		return hotelNameEn;
	}
	public void setHotelNameEn(String hotelNameEn) {
		this.hotelNameEn = hotelNameEn;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getContinent() {
		return continent;
	}
	public void setContinent(String continent) {
		this.continent = continent;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public String getCompFacilities() {
		return compFacilities;
	}
	public void setCompFacilities(String compFacilities) {
		this.compFacilities = compFacilities;
	}
	public String getCateringFacilities() {
		return cateringFacilities;
	}
	public void setCateringFacilities(String cateringFacilities) {
		this.cateringFacilities = cateringFacilities;
	}
	public String getNetworkFacilities() {
		return networkFacilities;
	}
	public void setNetworkFacilities(String networkFacilities) {
		this.networkFacilities = networkFacilities;
	}
	public String getActivityFacilities() {
		return activityFacilities;
	}
	public void setActivityFacilities(String activityFacilities) {
		this.activityFacilities = activityFacilities;
	}
	public String getServiceFacilities() {
		return serviceFacilities;
	}
	public void setServiceFacilities(String serviceFacilities) {
		this.serviceFacilities = serviceFacilities;
	}

	public List<String> getPictureIds() {
		return pictureIds;
	}
	public void setPictureIds(List<String> pictureIds) {
		this.pictureIds = pictureIds;
	}
	public String getAdvantage() {
		return advantage;
	}
	public void setAdvantage(String advantage) {
		this.advantage = advantage;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Boolean getIsComplete() {
		return isComplete;
	}
	public void setIsComplete(Boolean isComplete) {
		this.isComplete = isComplete;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


}
