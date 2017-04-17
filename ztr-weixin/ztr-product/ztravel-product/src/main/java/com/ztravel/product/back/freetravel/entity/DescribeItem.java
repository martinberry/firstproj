package com.ztravel.product.back.freetravel.entity;

import java.util.List;

public class DescribeItem {
	//图片
	private List<String> images ;

	private String describe;

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}
}
