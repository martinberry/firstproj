package com.ztravel.weixin.topicdiscuss.vo;

public class TopicCommentSubmitVo {
	private String topicId;
	private String comment;
	private int anonymous;
	private String nickName;
	private String submiterId ;
	private String headImage ;
	public String getTopicId() {
		return topicId;
	}
	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getAnonymous() {
		return anonymous;
	}
	public void setAnonymous(int anonymous) {
		this.anonymous = anonymous;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getSubmiterId() {
		return submiterId;
	}
	public void setSubmiterId(String submiterId) {
		this.submiterId = submiterId;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	
	
}
