package com.ztravel.operator.basicdata.vo;

import java.util.List;

import com.ztravel.operator.basicdata.entity.Destination;

public class DestinationVO {

	private List<Destination> destinationList;
	/**
	 * 默认显示目的地
	 */
	private String defaultDestination;

	public List<Destination> getDestinationList() {
		return destinationList;
	}
	public void setDestinationList(List<Destination> destinationList) {
		this.destinationList = destinationList;
	}
	public String getDefaultDestination() {
		return defaultDestination;
	}
	public void setDefaultDestination(String defaultDestination) {
		this.defaultDestination = defaultDestination;
	}

}
