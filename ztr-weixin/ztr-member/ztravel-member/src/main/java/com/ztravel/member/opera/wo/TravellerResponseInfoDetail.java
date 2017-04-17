package com.ztravel.member.opera.wo;

public class TravellerResponseInfoDetail extends TravellerResponseInfo {

	/**
	 * 常旅客国籍
	 */
	private String nationality;

	/**
	 * 常旅客生日
	 */
	private	String birthday;

	/**
	 * 常旅客邮箱
	 */
	private String email;

	/**
	 * 邮寄地址
	 */
	private String address;

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
