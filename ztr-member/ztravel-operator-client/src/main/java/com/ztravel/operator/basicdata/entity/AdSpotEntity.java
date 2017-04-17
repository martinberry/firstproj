package com.ztravel.operator.basicdata.entity;

/**
 * 基础数据 -->广告位
 * @author zhoubo
 *
 */
import org.bson.types.ObjectId;

import com.github.jmkgreen.morphia.annotations.Id;

public class AdSpotEntity {

	@Id
	private ObjectId id;

	private String title;

	private String url;

	private String priority;

	private String imageId;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}


}
