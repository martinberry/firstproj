package com.ztravel.product.po.pieces.common;


/**
 * 成本价格信息
 * */
public class PriceInfo {
	
	private String name;
	
	private String id;
	private Boolean hasChildPrice;
	
	private Double adultCost;
	
	private Double childCost;
	
	private Double adultPrice;
	
	private Double childPrice;
	
	

	public Boolean getHasChildPrice() {
		return hasChildPrice;
	}

	public void setHasChildPrice(Boolean hasChildPrice) {
		this.hasChildPrice = hasChildPrice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getAdultCost() {
		return adultCost;
	}

	public void setAdultCost(Double adultCost) {
		this.adultCost = adultCost;
	}

	public Double getChildCost() {
		return childCost;
	}

	public void setChildCost(Double childCost) {
		this.childCost = childCost;
	}

	public Double getAdultPrice() {
		return adultPrice;
	}

	public void setAdultPrice(Double adultPrice) {
		this.adultPrice = adultPrice;
	}

	public Double getChildPrice() {
		return childPrice;
	}

	public void setChildPrice(Double childPrice) {
		this.childPrice = childPrice;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
