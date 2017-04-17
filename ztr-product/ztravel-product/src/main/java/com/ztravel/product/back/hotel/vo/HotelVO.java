/**
 *
 */
package com.ztravel.product.back.hotel.vo;

/**
 * @author
 * 产品管理-->酒店资源 用于酒店列表页面展示
 */
public class HotelVO {
	/**
	 * 主键id
	 */
	private String id;
	/**
	 * 酒店ID,展示用
	 */
	private String hotelId;
	/**
	 * 酒店中文名称
	 */
	private String hotelNameCn;
	/**
	 * 国家
	 */
	private String country;
	/**
	 * 城市/景点
	 */
	private String cityOrAttraction;
	/**
	 * 酒店类型
	 */
	private String hotelType;
	/**
	 * 星级
	 */
	private String rating;
	/**
	 * 卖点
	 */
	private String highLights;
	/**
	 * 电话
	 */
	private String phoneNumber;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 地址
	 */
	private String address;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHotelId() {
		return hotelId;
	}
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
	public String getHotelNameCn() {
		return hotelNameCn;
	}
	public void setHotelNameCn(String hotelNameCn) {
		this.hotelNameCn = hotelNameCn;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCityOrAttraction() {
		return cityOrAttraction;
	}
	public void setCityOrAttraction(String cityOrAttraction) {
		this.cityOrAttraction = cityOrAttraction;
	}
	public String getHotelType() {
		return hotelType;
	}
	public void setHotelType(String hotelType) {
		this.hotelType = hotelType;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getHighLights() {
		return highLights;
	}
	public void setHighLights(String highLights) {
		this.highLights = highLights;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
