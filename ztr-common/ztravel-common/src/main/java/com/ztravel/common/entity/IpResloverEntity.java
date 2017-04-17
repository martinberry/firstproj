package com.ztravel.common.entity;

/**
 * ip解析信息类
 * @author liuzhuo
 *
 */
public class IpResloverEntity {
	
	/**
	 * ip
	 */
	private String ip;
	
	/**
	 * 解析后地址
	 */
	private String address;
	
	/**
	 * 纬度
	 */
	private String dimension;
	
	/**
	 * 精度
	 */
	private String longitude;
	
	public IpResloverEntity() {
		
	}
	
	public IpResloverEntity(String ip, String address, String dimension, String longitude) {
		this.ip = ip;
		this.address = address;
		this.dimension = dimension;
		this.longitude = longitude;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	


	

}
