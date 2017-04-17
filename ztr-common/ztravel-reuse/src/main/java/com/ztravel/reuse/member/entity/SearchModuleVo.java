package com.ztravel.reuse.member.entity;

import java.util.List;

import com.ztravel.common.entity.ProductDestination;

public class SearchModuleVo {

	private String departPlace ;
	private List<String> departurePlaceList ;
	private List<ProductDestination> destinations ;
	private String defaultDestination ;
	private Integer destinationLevel;

	public List<String> getDeparturePlaceList() {
		return departurePlaceList;
	}
	public void setDeparturePlaceList(List<String> departurePlaceList) {
		this.departurePlaceList = departurePlaceList;
	}
	public List<ProductDestination> getDestinations() {
		return destinations;
	}
	public void setDestinations(List<ProductDestination> destinations) {
		this.destinations = destinations;
	}
	public String getDefaultDestination() {
		return defaultDestination;
	}
	public void setDefaultDestination(String defaultDestination) {
		this.defaultDestination = defaultDestination;
	}
	public String getDepartPlace() {
		return departPlace;
	}
	public void setDepartPlace(String departPlace) {
		this.departPlace = departPlace;
	}
	public Integer getDestinationLevel() {
		return destinationLevel;
	}
	public void setDestinationLevel(Integer destinationLevel) {
		this.destinationLevel = destinationLevel;
	}


}
