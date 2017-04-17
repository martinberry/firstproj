package com.ztravel.operator.basicdata.entity;

import java.util.List;


public class RecProductHomePageEntity {

	private String mainTitle;

	private String viceTitle;

    private String productType;

	private String productId;

	private String pictureId;

	private Double lowestPrice ;

	private List<String> tags ;

	private List<String> highLights ;


	public Double getLowestPrice() {
		return lowestPrice;
	}
	public void setLowestPrice(Double lowestPrice) {
		this.lowestPrice = lowestPrice;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public List<String> getHighLights() {
		return highLights;
	}
	public void setHighLights(List<String> highLights) {
		this.highLights = highLights;
	}
	public String getMainTitle() {
		return mainTitle;
	}
	public void setMainTitle(String mainTitle) {
		this.mainTitle = mainTitle;
	}
	public String getViceTitle() {
		return viceTitle;
	}
	public void setViceTitle(String viceTitle) {
		this.viceTitle = viceTitle;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getPictureId() {
		return pictureId;
	}
	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
	}
    public String getProductType() {
        return productType;
    }
    public void setProductType(String productType) {
        this.productType = productType;
    }

}
