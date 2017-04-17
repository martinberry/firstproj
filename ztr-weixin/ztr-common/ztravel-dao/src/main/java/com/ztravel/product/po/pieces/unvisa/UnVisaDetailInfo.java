package com.ztravel.product.po.pieces.unvisa;

import java.util.List;

import com.ztravel.common.enums.LanguageType;

public class UnVisaDetailInfo {
	/**
	 * {@link LanguageType}
	 * */
	private String languageType;

	private String serviceTime;
	private List<String> images;

	public String getLanguageType() {
		return languageType;
	}

	public void setLanguageType(String languageType) {
		this.languageType = languageType;
	}

	public String getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

}
