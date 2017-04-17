package com.ztravel.product.weixin.wo;

import java.util.List;

public class HotelWo {

	//酒店资源ID
	private String id ;

	//酒店名称
	private String name ;

	//酒店中文名称
	private String hotelNameCn ;

	//酒店英文名称
	private String hotelNameEn ;

	//酒店图片
	private List<String> pictureIds ;

	//入住偏移时间
	private List<Integer> checkinDays ;

	//入住偏移时间
	private String checkinDaysStr ;

	//酒店卖点
	private String highLights ;

	//酒店星级
	private String rate ;

	//酒店类型
	private String hoteltype ;

	//房型名称
	private String roomType ;

	//床型名称
	private String bedType ;

	//早餐
	private String breakFestType ;

	//电话
	private String phone ;

	//地址
	private String address ;

	//酒店描述
	private String describe ;

	 //综合设施
	private String compFacilities ;

	 //餐饮设施
	private String cateringFacilities ;

	//网络设施
	private String networkFacilities ;

	 //活动设施
	private String activityFacilities ;

	 //服务项目
	private String serviceFacilities ;

	//入住须知
	private String notes ;

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

	public List<String> getPictureIds() {
		return pictureIds;
	}

	public void setPictureIds(List<String> pictureIds) {
		this.pictureIds = pictureIds;
	}

	public List<Integer> getCheckinDays() {
		return checkinDays;
	}

	public void setCheckinDays(List<Integer> checkinDays) {
		this.checkinDays = checkinDays;
	}

	public String getCheckinDaysStr() {
		return checkinDaysStr;
	}

	public void setCheckinDaysStr(String checkinDaysStr) {
		this.checkinDaysStr = checkinDaysStr;
	}

	public String getHighLights() {
		return highLights;
	}

	public void setHighLights(String highLights) {
		this.highLights = highLights;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getHoteltype() {
		return hoteltype;
	}

	public void setHoteltype(String hoteltype) {
		this.hoteltype = hoteltype;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getBedType() {
		return bedType;
	}

	public void setBedType(String bedType) {
		this.bedType = bedType;
	}

	public String getBreakFestType() {
		return breakFestType;
	}

	public void setBreakFestType(String breakFestType) {
		this.breakFestType = breakFestType;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
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

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}


}
