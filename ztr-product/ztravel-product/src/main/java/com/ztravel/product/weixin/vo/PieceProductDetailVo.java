package com.ztravel.product.weixin.vo;

import java.util.Map;

import com.ztravel.product.po.pieces.common.PieceProduct;

public class PieceProductDetailVo extends PieceProduct {

	private String firstImage;

	private String proId;

	private String natureType;

	private Map<String, String> additional ;

	public String getFirstImage() {
		return firstImage;
	}

	public void setFirstImage(String firstImage) {
		this.firstImage = firstImage;
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public String getNatureType() {
		return natureType;
	}

	public void setNatureType(String natureType) {
		this.natureType = natureType;
	}

	public Map<String, String> getAdditional() {
		return additional;
	}

	public void setAdditional(Map<String, String> additional) {
		this.additional = additional;
	}

}
