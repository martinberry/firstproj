package com.ztravel.product.back.freetravel.vo;

import java.util.List;

/**
 * 基本信息VO
 * @author xujunhui
 * {@link com.ztravel.product.back.freetravel.entity.Product }
 */
public class BasicInfoVo {
	private String id;
	private String pid ;
	private Integer progress ;
	/*{@link Status}*/
	private String status ;
	private String productName ;
	private String productSubName ;
	private String recommendWords ;
	private String theme;
	private Integer tripDays ;
	private Integer tripNights ;
	private List<String> tags ;
	private String from ;
	private List<String> to ;
	private List<String> toContinent ;
	private List<String> toCountry ;
	/*{@link Nature}*/
	private String nature ;
	/*{@link Content}*/
	private List<String> contents;
	/*{@link DestinationType}*/
	private String destinationType;
	private List<String> highLights ;
	private List<String> highLightTitles ;
	private String lightColor;
	private List<String> images ;
	private List<String> titleImages ;
	private List<String> detailTitleImages ;
	/**
	 * 与产品无关
	 */
	private Boolean withNext;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getTripDays() {
		return tripDays;
	}
	public void setTripDays(Integer tripDays) {
		this.tripDays = tripDays;
	}
	public Integer getTripNights() {
		return tripNights;
	}
	public void setTripNights(Integer tripNights) {
		this.tripNights = tripNights;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public List<String> getTo() {
		return to;
	}
	public void setTo(List<String> to) {
		this.to = to;
	}
	public String getNature() {
		return nature;
	}
	public void setNature(String nature) {
		this.nature = nature;
	}
	public List<String> getContents() {
		return contents;
	}
	public void setContents(List<String> contents) {
		this.contents = contents;
	}
	public List<String> getHighLights() {
		return highLights;
	}
	public void setHighLights(List<String> highLights) {
		this.highLights = highLights;
	}
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public Integer getProgress() {
		return progress;
	}
	public void setProgress(Integer progress) {
		this.progress = progress;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<String> getTitleImages() {
		return titleImages;
	}
	public void setTitleImages(List<String> titleImages) {
		this.titleImages = titleImages;
	}
	public List<String> getToContinent() {
		return toContinent;
	}
	public void setToContinent(List<String> toContinent) {
		this.toContinent = toContinent;
	}
	public List<String> getToCountry() {
		return toCountry;
	}
	public void setToCountry(List<String> toCountry) {
		this.toCountry = toCountry;
	}
	public String getDestinationType() {
		return destinationType;
	}
	public void setDestinationType(String destinationType) {
		this.destinationType = destinationType;
	}
	public String getLightColor() {
		return lightColor;
	}
	public void setLightColor(String lightColor) {
		this.lightColor = lightColor;
	}
	public Boolean getWithNext() {
		return withNext;
	}
	public void setWithNext(Boolean withNext) {
		this.withNext = withNext;
	}
	public List<String> getDetailTitleImages() {
		return detailTitleImages;
	}
	public void setDetailTitleImages(List<String> detailTitleImages) {
		this.detailTitleImages = detailTitleImages;
	}
	public List<String> getHighLightTitles() {
		return highLightTitles;
	}
	public void setHighLightTitles(List<String> highLightTitles) {
		this.highLightTitles = highLightTitles;
	}
	public String getProductSubName() {
		return productSubName == null ? "" : productSubName;
	}
	public void setProductSubName(String productSubName) {
		this.productSubName = productSubName;
	}
	public String getRecommendWords() {
		return recommendWords;
	}
	public void setRecommendWords(String recommendWords) {
		this.recommendWords = recommendWords;
	}

}
