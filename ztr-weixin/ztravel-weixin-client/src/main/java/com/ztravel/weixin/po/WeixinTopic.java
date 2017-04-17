package com.ztravel.weixin.po;

import org.joda.time.DateTime;

import com.ztravel.common.enums.TopicStatus;

public class WeixinTopic {
	private String topicId;
	private String topicTitle;
	private String pid;
	private String productTitle;
	private String giftTitle;
	private DateTime releasedTime;
	private Integer discussNum;
	private TopicStatus status;
	private DateTime createdate;
	private DateTime updatedate;
	private String topicContent;
	private String giftImage;
	private String giftContent;
	
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
	public TopicStatus getStatus() {
		return status;
	}
	public void setStatus(TopicStatus status) {
		this.status = status;
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
	public DateTime getCreatedate() {
		return createdate;
	}
	public void setCreatedate(DateTime createdate) {
		this.createdate = createdate;
	}
	public DateTime getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(DateTime updatedate) {
		this.updatedate = updatedate;
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
	public DateTime getReleasedTime() {
		return releasedTime;
	}
	public void setReleasedTime(DateTime releasedTime) {
		this.releasedTime = releasedTime;
	}
	public Integer getDiscussNum() {
		return discussNum;
	}
	public void setDiscussNum(Integer discussNum) {
		this.discussNum = discussNum;
	}

	

}
