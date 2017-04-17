package com.ztravel.common.entity;

/**
 * 联系人信息
 * @author liuzhuo
 *
 */
public class ContactorInfo {

	/**
	 * 联系人姓名
	 */
	private String name;

	/**
	 * 联系人手机
	 */
	private String phone;

	/**
	 * 联系人邮箱
	 */
	private String email;

	/**
	 * 省
	 */
	private String province;

	/**
	 * 市
	 */
	private String city;
	/**
	 * 县
	 * */
	private String county;

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	/**
	 * 详细地址
	 */
	private String addressDetail;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}



}
