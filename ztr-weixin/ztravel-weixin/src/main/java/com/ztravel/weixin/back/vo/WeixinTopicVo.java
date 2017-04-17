package com.ztravel.weixin.back.vo;
public class WeixinTopicVo {
	private String topicId;
	private String topicTitle;
	private String pid;
	private String productTitle;
	private String giftTitle;
	private String releasedTime;
	private String discussNum;
	private String status;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	
	public String getGiftTitle() {
		return giftTitle;
	}
	public void setGiftTitle(String giftTitle) {
		this.giftTitle = giftTitle;
	}
	public String getReleasedTime() {
		return releasedTime;
	}
	public void setReleasedTime(String releasedTime) {
		this.releasedTime = releasedTime;
	}
	public String getDiscussNum() {
		return discussNum;
	}
	public void setDiscussNum(String discussNum) {
		this.discussNum = discussNum;
	}


}
