package com.ztravel.product.po.pieces.visa;

import java.util.List;

public class VisaDetailInfo {

	private Boolean isInterview;

	private String validate;

	private String stayTime;//此字段需确认是时间范围还是具体一个日期？

	private String scope;

	private String times;

	private List<String> images;

	public Boolean getIsInterview() {
		return isInterview;
	}

	public void setIsInterview(Boolean isInterview) {
		this.isInterview = isInterview;
	}

	public String getValidate() {
		return validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}

	public String getStayTime() {
		return stayTime;
	}

	public void setStayTime(String stayTime) {
		this.stayTime = stayTime;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

}
