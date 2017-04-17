package com.ztravel.product.front.entity;

/**
 * 前台产品列表搜索条件
 * @author MH
 */
public class ProductSearchCriteria {
	/**
	 * 出发地
	 */
	private String departure;
	/**
	 * 目的地(大洲或国家)
	 */
	private String destination;
	/**
	 * 目的地层级(1 大洲; 2 国家)
	 */
	private int destLevel;

	public String getDeparture() {
		return departure;
	}
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public int getDestLevel() {
		return destLevel;
	}
	public void setDestLevel(int destLevel) {
		this.destLevel = destLevel;
	}

}
