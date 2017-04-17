package com.ztravel.product.weixin.vo;

import com.ztravel.product.po.pieces.unvisa.UnVisaProduct;

public class LocaltListVo extends UnVisaProduct{
	
	private String image;
	
	private String lowest;
	
	private String typeDesc;

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

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

}
