package com.ztravel.product.back.freetravel.vo;

import java.util.Map;


public class AdditionalInfoVo {
	private String id;
	private String pid ;
	/* {@link AdditionalRule}*/
	private Map<String, String> additionalInfos ;
	/* {@link TravelTipEnum}*/
	private Map<String, String> travelTips;
	private Integer progress ;
	/*{@link Status}*/
	private String status ;

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
}
