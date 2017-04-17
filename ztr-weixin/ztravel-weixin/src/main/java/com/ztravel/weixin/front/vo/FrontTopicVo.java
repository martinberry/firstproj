package com.ztravel.weixin.front.vo;

import org.joda.time.DateTime;


public class FrontTopicVo {
	private String topicId;
	private String topicTitle;
	private String pid;
	private String productTitle;
	private String giftTitle;
	private DateTime releasedTime;
	private String discussNum;
	private String topicContent;
	private String giftImage;
	private String giftContent;
	public String getTopicId() {
		return topicId;
	}
	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}
	public String getTopicTitle() {
		return topicTitle;
	}
	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getProductTitle() {
		return productTitle;
	}
	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}
	public String getGiftTitle() {
		return giftTitle;
	}
	public void setGiftTitle(String giftTitle) {
		this.giftTitle = giftTitle;
	}
	public DateTime getReleasedTime() {
		return releasedTime;
	}
	public void setReleasedTime(DateTime releasedTime) {
		this.releasedTime = releasedTime;
	}
	public String getDiscussNum() {
		return discussNum;
	}
	public void setDiscussNum(String discussNum) {
		this.discussNum = discussNum;
	}
	public String getTopicContent() {
		return topicContent;
	}
	public void setTopicContent(String topicContent) {
		this.topicContent = topicContent;
	}
	public String getGiftImage() {
		return giftImage;
	}
	public void setGiftImage(String giftImage) {
		this.giftImage = giftImage;
	}
	public String getGiftContent() {
		return giftContent;
	}
	public void setGiftContent(String giftContent) {
		this.giftContent = giftContent;
	}

}
