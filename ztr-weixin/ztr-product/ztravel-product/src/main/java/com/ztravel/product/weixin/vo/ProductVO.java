package com.ztravel.product.weixin.vo;

import java.util.List;

import com.ztravel.weixin.po.WeixinTopic;

/**
 * 用于weixin端产品列表页面展示
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
	 * 关联话题
	 * */
	private List<WeixinTopic> topics;
	
	/**
	 *推荐语 
	 * */
	private String recommendWords;
	/**
	 * 背景图片id
	 */
	private String imageId;
	
	/**
	 * 产品主题
	 * */
	private String theme;
	/**
	 * 产品副标题
	 * */
	private String subName;
	
	/**
	 * 产品最低价
	 * */
	private String lowestPrice;

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
//	public List<String> getTags() {
//		return tags;
//	}
//	public void setTags(List<String> tags) {
//		this.tags = tags;
//	}
//	public List<String> getHighLights() {
//		return highLights;
//	}
//	public void setHighLights(List<String> highLights) {
//		this.highLights = highLights;
//	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
//	public String getTitleImageId() {
//		return titleImageId;
//	}
//	public void setTitleImageId(String titleImageId) {
//		this.titleImageId = titleImageId;
//	}
//	public boolean getIsWish() {
//		return isWish;
//	}
//	public void setIsWish(boolean isWish) {
//		this.isWish = isWish;
//	}
	public List<WeixinTopic> getTopics() {
		return topics;
	}
	public void setTopics(List<WeixinTopic> topics) {
		this.topics = topics;
	}
	public String getRecommendWords() {
		return recommendWords;
	}
	public void setRecommendWords(String recommendWords) {
		this.recommendWords = recommendWords;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getSubName() {
		return subName;
	}
	public void setSubName(String subName) {
		this.subName = subName;
	}
	public String getLowestPrice() {
		return lowestPrice;
	}
	public void setLowestPrice(String lowestPrice) {
		this.lowestPrice = lowestPrice;
	}


}
