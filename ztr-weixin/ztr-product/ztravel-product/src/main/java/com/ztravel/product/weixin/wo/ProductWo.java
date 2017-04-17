package com.ztravel.product.weixin.wo;

import java.util.List;
import java.util.Map;

public class ProductWo {

	private String id;

	//业务ID
	private String pid ;

	//产品名称
	private String pName ;

	private String theme ;

	private Integer tripDays ;

	private Integer tripNights ;

	private List<String> tags ;

	private String from ;

	private List<String> to ;

	//产品内容
//	private List<String> contents;

	private List<String> highLights ;

	private List<String> highLightTitles;

	private String lightColor;

	private List<String> images ;

	private List<String> titleImages ;

	private Double lowestPrice ;
	
	//市场价
	private Double marketPrice ;

	private FlightWo flight ;

	private List<HotelWo> hotels ;

	private List<TripWo> recommendTrips ;

	private Map<String, String> additionalInfos ;

	private Map<String, String> travelTips;

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

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
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

	public List<String> getTitleImages() {
		return titleImages;
	}

	public void setTitleImages(List<String> titleImages) {
		this.titleImages = titleImages;
	}

	public Double getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(Double lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public FlightWo getFlight() {
		return flight;
	}

	public void setFlight(FlightWo flight) {
		this.flight = flight;
	}

	public List<HotelWo> getHotels() {
		return hotels;
	}

	public void setHotels(List<HotelWo> hotels) {
		this.hotels = hotels;
	}

	public List<TripWo> getRecommendTrips() {
		return recommendTrips;
	}

	public void setRecommendTrips(List<TripWo> recommendTrips) {
		this.recommendTrips = recommendTrips;
	}

	public Map<String, String> getAdditionalInfos() {
		return additionalInfos;
	}

	public void setAdditionalInfos(Map<String, String> additionalInfos) {
		this.additionalInfos = additionalInfos;
	}

	public Map<String, String> getTravelTips() {
		return travelTips;
	}

	public void setTravelTips(Map<String, String> travelTips) {
		this.travelTips = travelTips;
	}

	public String getLightColor() {
		return lightColor;
	}

	public void setLightColor(String lightColor) {
		this.lightColor = lightColor;
	}

	public List<String> getHighLightTitles() {
		return highLightTitles;
	}

	public void setHighLightTitles(List<String> highLightTitles) {
		this.highLightTitles = highLightTitles;
	}

	public Double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}

}
