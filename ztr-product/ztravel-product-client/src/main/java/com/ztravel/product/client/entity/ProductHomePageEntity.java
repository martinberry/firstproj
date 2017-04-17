package com.ztravel.product.client.entity;

import java.util.List;



public class ProductHomePageEntity {
	private String productId ;
	
	private String pictureId ;
	
	private Double lowestPrice ;
	
	private List<String> highLights ;
	
	private List<String> tags ;
	
	private String mainTitle ;
	
	private String viceTitle ;

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

	public Double getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(Double lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public List<String> getHighLights() {
		return highLights;
	}

	public void setHighLights(List<String> highLights) {
		this.highLights = highLights;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
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
	
	

}
