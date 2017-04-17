package com.ztravel.weixin.back.entity;

import org.joda.time.DateTime;

public class MenuButton {

	  private String buttonId;

	  private String name;

	  private String parentButtonId;

	  private Integer buttonLevel;

	  private String type;

	  private String key;

	  private String url;

	  private String mediaId;

	  private Integer priority;

	  private DateTime createTime;

	  private String remark;

	  private Integer state;

	  private String viewStateId;

	  private String wxUrlFlag;

	public String getButtonId() {
		return buttonId;
	}

	public void setButtonId(String buttonId) {
		this.buttonId = buttonId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentButtonId() {
		return parentButtonId;
	}

	public void setParentButtonId(String parentButtonId) {
		this.parentButtonId = parentButtonId;
	}

	public Integer getButtonLevel() {
		return buttonLevel;
	}

	public void setButtonLevel(Integer buttonLevel) {
		this.buttonLevel = buttonLevel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public DateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getViewStateId() {
		return viewStateId;
	}

	public void setViewStateId(String viewStateId) {
		this.viewStateId = viewStateId;
	}

	public String getWxUrlFlag() {
		return wxUrlFlag;
	}

	public void setWxUrlFlag(String wxUrlFlag) {
		this.wxUrlFlag = wxUrlFlag;
	}
}
