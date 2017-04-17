package com.ztravel.product.back.freetravel.entity;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;

import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;
import com.ztravel.common.enums.AdditionalRule;
import com.ztravel.common.enums.DestinationType;
import com.ztravel.common.enums.Nature;
import com.ztravel.product.back.freetravel.enums.Content;
import com.ztravel.product.back.freetravel.enums.Status;
import com.ztravel.product.back.freetravel.enums.TravelTipEnum;

/**
 * @author wanhaofan
 * 自由行产品
 * */
@Entity(value="freeTravelProduct", noClassnameStored=true)
public class Product {
	@Id
	private ObjectId id;

	//业务ID
	private String pid ;

	//产品名称
	private String pName ;
	
	//产品副标题
	private String subName ;
	
	//推荐语
	private String recommendWords;

	private String theme ;

	private Integer tripDays ;

	private Integer tripNights ;

	private List<String> tags ;

	private String from ;

	private List<String> to ;

	private List<String> toCountry ;

	private List<String> toContinent ;

	//产品性质
	private Nature nature ;

	//产品内容
	private List<Content> contents;

	//目的地类型
	private DestinationType destinationType;

	private List<String> highLights ;

	private List<String> highLightTitles;

	private String lightColor;

	private List<String> images ;
	/*列表页标题图层*/
	private List<String> titleImages ;
	/*详情页标题图层*/
	private List<String> detailTitleImages ;

	private Flight flight ;

	private List<Hotel> hotels ;

	//打包供应商
	private String packageSupplier ;

	//接送机供应商
	private String shuttleSupplier ;
	
	//真旅本子供应商
	private String zenbookSupplier ;
	
	//wifi供应商
	private String wifiSupplier ;
	
	//其他供应商
	private String otherSupplier ;
	
	//接送机成本
	private Double shuttleCost ;
	
	//真旅本子成本
	private Double zenbookCost ;
	
	//wifi成本
	private Double wifiCost ;
	
	//其他成本
	private Double otherCost ;

	//供应商
	private String flightSupplier ;

	private Map<String, Day> calendar ;

	private List<Trip> recommendTrips ;

	private Double lowestPrice ;

	private Map<AdditionalRule, String> additionalInfos ;

	private Map<TravelTipEnum, String> travelTips;

	//产品进度
	private Integer progress ;

	//产品状态
	private Status status ;

	private String createdBy ;

	private String updateBy ;

	private DateTime createdTime ;

	private DateTime updatedTime ;


	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public DateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(DateTime createdTime) {
		this.createdTime = createdTime;
	}

	public DateTime getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(DateTime updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Boolean isPackage(){
		return this.nature != null && this.nature.equals(Nature.PACKAGE) ;
	}

	public Boolean isCombination(){
		return this.nature != null && this.nature.equals(Nature.COMBINATION) ;
	}

	public Boolean isContainFlight() {
		return this.contents != null && this.contents.contains(Content.FLIGHT) ;
	}

	public Boolean isContainHotel() {
		return this.contents != null && this.contents.contains(Content.HOTEL) ;
	}
	
	public Boolean isContainShuttle() {
		return this.contents != null && this.contents.contains(Content.SHUTTLE) ;
	}
	
	public Boolean isContainZenbook() {
		return this.contents != null && this.contents.contains(Content.ZENBOOK) ;
	}
	
	public Boolean isContainWifi() {
		return this.contents != null && this.contents.contains(Content.WIFI) ;
	}
	
	public Boolean isContainOther() {
		return this.contents != null && this.contents.contains(Content.OTHER) ;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
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

	public List<Hotel> getHotels() {
		return hotels;
	}

	public void setHotels(List<Hotel> hotels) {
		this.hotels = hotels;
	}

	public Map<String, Day> getCalendar() {
		return calendar;
	}

	public void setCalendar(Map<String, Day> calendar) {
		this.calendar = calendar;
	}

	public List<Trip> getRecommendTrips() {
		return recommendTrips;
	}

	public void setRecommendTrips(List<Trip> recommendTrips) {
		this.recommendTrips = recommendTrips;
	}

	public Map<AdditionalRule, String> getAdditionalInfos() {
		return additionalInfos;
	}

	public void setAdditionalInfos(Map<AdditionalRule, String> additionalInfos) {
		this.additionalInfos = additionalInfos;
	}

	public Nature getNature() {
		return nature;
	}

	public void setNature(Nature nature) {
		this.nature = nature;
	}

	public List<Content> getContents() {
		return contents;
	}

	public void setContents(List<Content> contents) {
		this.contents = contents;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public String getPackageSupplier() {
		return packageSupplier;
	}

	public void setPackageSupplier(String packageSupplier) {
		this.packageSupplier = packageSupplier;
	}

	public String getFlightSupplier() {
		return flightSupplier;
	}

	public void setFlightSupplier(String flightSupplier) {
		this.flightSupplier = flightSupplier;
	}

	public Double getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(Double lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public List<String> getToCountry() {
		return toCountry;
	}

	public void setToCountry(List<String> toCountry) {
		this.toCountry = toCountry;
	}

	public List<String> getToContinent() {
		return toContinent;
	}

	public void setToContinent(List<String> toContinent) {
		this.toContinent = toContinent;
	}

	public List<String> getTitleImages() {
		return titleImages;
	}

	public void setTitleImages(List<String> titleImages) {
		this.titleImages = titleImages;
	}

	public DestinationType getDestinationType() {
		return destinationType;
	}

	public void setDestinationType(DestinationType destinationType) {
		this.destinationType = destinationType;
	}

	public String getLightColor() {
		return lightColor;
	}

	public void setLightColor(String lightColor) {
		this.lightColor = lightColor;
	}

	public Map<TravelTipEnum, String> getTravelTips() {
		return travelTips;
	}

	public void setTravelTips(Map<TravelTipEnum, String> travelTips) {
		this.travelTips = travelTips;
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

	public String getShuttleSupplier() {
		return shuttleSupplier;
	}

	public void setShuttleSupplier(String shuttleSupplier) {
		this.shuttleSupplier = shuttleSupplier;
	}

	public String getZenbookSupplier() {
		return zenbookSupplier;
	}

	public void setZenbookSupplier(String zenbookSupplier) {
		this.zenbookSupplier = zenbookSupplier;
	}

	public String getWifiSupplier() {
		return wifiSupplier;
	}

	public void setWifiSupplier(String wifiSupplier) {
		this.wifiSupplier = wifiSupplier;
	}

	public String getOtherSupplier() {
		return otherSupplier;
	}

	public void setOtherSupplier(String otherSupplier) {
		this.otherSupplier = otherSupplier;
	}

	public Double getShuttleCost() {
		return shuttleCost;
	}

	public void setShuttleCost(Double shuttleCost) {
		this.shuttleCost = shuttleCost;
	}

	public Double getZenbookCost() {
		return zenbookCost;
	}

	public void setZenbookCost(Double zenbookCost) {
		this.zenbookCost = zenbookCost;
	}

	public Double getWifiCost() {
		return wifiCost;
	}

	public void setWifiCost(Double wifiCost) {
		this.wifiCost = wifiCost;
	}

	public Double getOtherCost() {
		return otherCost;
	}

	public void setOtherCost(Double otherCost) {
		this.otherCost = otherCost;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public String getRecommendWords() {
		return recommendWords;
	}

	public void setRecommendWords(String recommendWords) {
		this.recommendWords = recommendWords;
	}

}
