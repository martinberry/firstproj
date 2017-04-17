package com.ztravel.product.weixin.vo;

import com.ztravel.product.po.pieces.visa.VisaProduct;

public class VisaProductListVo extends VisaProduct{
	
	private String image;
	
	private String lowest;
	
	private String toCountry;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getLowest() {
		return lowest;
	}

	public void setLowest(String lowest) {
		this.lowest = lowest;
	}

	public String getToCountry() {
		return toCountry;
	}

	public void setToCountry(String toCountry) {
		this.toCountry = toCountry;
	}
	
}
