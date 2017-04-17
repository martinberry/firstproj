package com.ztravel.operator.basicdata.entity;

/**
 * 目的地
 * @author MH
 */
public class Destination {
	/**
	 * 大洲
	 */
	private String continent;
	/**
	 * 国家
	 */
	private String country;
	/**
	 * 城市
	 */
	private String city;

	public String getContinent() {
		return continent;
	}
	public void setContinent(String continent) {
		this.continent = continent;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

}
