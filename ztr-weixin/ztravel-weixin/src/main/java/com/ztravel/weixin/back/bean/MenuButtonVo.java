package com.ztravel.weixin.back.bean;

public class MenuButtonVo {

	private String name;

	private Integer buttonLevel;

	private String type;

	private String key;

	private String url;

	private String mediaId;

	private Integer priority;

	private Integer state;

	private String viewState;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getViewState() {
		return viewState;
	}

	public void setViewState(String viewState) {
		this.viewState = viewState;
	}
}
