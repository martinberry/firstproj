package com.ztravel.product.client.entity;

import java.util.List;
import java.util.Map;


public class ProductClientEntity {
	/**
	 * 行程天数
	 */
	private Integer tripDays;
	/**
	 * 附加信息
	 */
	private Map<String, String> additionalInfos;

	private String providerInfo;

	private String productCode;
	
	private List<String> toCountry ;

	public Integer getTripDays() {
		return tripDays;
	}

	public void setTripDays(Integer tripDays) {
		this.tripDays = tripDays;
	}

	public Map<String, String> getAdditionalInfos() {
		return additionalInfos;
	}

	public void setAdditionalInfos(Map<String, String> additionalInfos) {
		this.additionalInfos = additionalInfos;
	}

	public String getProviderInfo() {
		return providerInfo;
	}

	public void setProviderInfo(String providerInfo) {
		this.providerInfo = providerInfo;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public List<String> getToCountry() {
		return toCountry;
	}

	public void setToCountry(List<String> toCountry) {
		this.toCountry = toCountry;
	}

}
