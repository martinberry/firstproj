package com.ztravel.weixin.back.entity;

import java.util.List;

import com.ztravel.common.entity.PaginationEntity;

public class SearchMaterialEntity extends PaginationEntity{
	private String mediaId;
	private String title;
	private String picUrl;
	private String type;

	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}


}
