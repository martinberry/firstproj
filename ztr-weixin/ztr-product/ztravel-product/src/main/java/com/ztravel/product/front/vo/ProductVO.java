package com.ztravel.product.front.vo;

import java.util.List;

/**
 * 用于C端产品列表页面展示
 * @author MH
 */
public class ProductVO {
	/**
	 * 产品ObjectId
	 */
	private String id;
	/**
	 * 产品pid
	 */
	private String pid;
	/**
	 * 产品名称
	 */
	private String pName ;
	/**
	 * 标签
	 */
	private List<String> tags;
	/**
	 * 亮点
	 */
	private List<String> highLights;
	/**
	 * 背景图片id
	 */
	private String imageId;
	/**
	 * 标题图片id
	 */
	private String titleImageId;

	private boolean isWish;

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
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public List<String> getHighLights() {
		return highLights;
	}
	public void setHighLights(List<String> highLights) {
		this.highLights = highLights;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public String getTitleImageId() {
		return titleImageId;
	}
	public void setTitleImageId(String titleImageId) {
		this.titleImageId = titleImageId;
	}
	public boolean getIsWish() {
		return isWish;
	}
	public void setIsWish(boolean isWish) {
		this.isWish = isWish;
	}


}
