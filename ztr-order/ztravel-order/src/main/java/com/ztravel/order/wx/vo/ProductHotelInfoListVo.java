package com.ztravel.order.wx.vo;

import java.util.List;

public class ProductHotelInfoListVo {

	List<ProductHotelInfoVo> productHotelInfoVoLsit;

	/**
	 * 床型偏好
	 * */
	private String bedPrefer;

	public List<ProductHotelInfoVo> getProductHotelInfoVoLsit() {
		return productHotelInfoVoLsit;
	}

	public void setProductHotelInfoVoLsit(
			List<ProductHotelInfoVo> productHotelInfoVoLsit) {
		this.productHotelInfoVoLsit = productHotelInfoVoLsit;
	}

	public String getBedPrefer() {
		return bedPrefer;
	}

	public void setBedPrefer(String bedPrefer) {
		this.bedPrefer = bedPrefer;
	}

}
