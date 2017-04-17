package com.ztravel.product.back.freetravel.vo;

import java.util.List;

import com.ztravel.product.back.freetravel.entity.Trip;

public class RecommendTripVo {
	private String id;
	private String pid ;
	private Integer progress ;
	/*{@link Status}*/
	private String status ;
	private String productName ;
	private Integer tripDays ;
	private Integer tripNights ;
	private List<Trip> recommendTrips;
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
	public List<Trip> getRecommendTrips() {
		return recommendTrips;
	}
	public void setRecommendTrips(List<Trip> recommendTrips) {
		this.recommendTrips = recommendTrips;
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
	public Boolean getWithNext() {
		return withNext;
	}
	public void setWithNext(Boolean withNext) {
		this.withNext = withNext;
	}
}
