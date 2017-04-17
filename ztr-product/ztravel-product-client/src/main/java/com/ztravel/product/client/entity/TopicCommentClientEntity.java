package com.ztravel.product.client.entity;

import org.joda.time.DateTime;

public class TopicCommentClientEntity {
	private String commentId;
	private String nickName;	
	private DateTime commentTime;
	private Integer praiseNum;
	private String commentDetail; 
	private String topicId;
	private String timeStatus;
	private int anonymous;
	private String submiterId;
	private String headImage;
	public int getAnonymous() {
		return anonymous;
	}
	public void setAnonymous(int anonymous) {
		this.anonymous = anonymous;
	}
	public String getSubmiterId() {
		return submiterId;
	}
	public void setSubmiterId(String submiterId) {
		this.submiterId = submiterId;
	}
	public String getTimeStatus() {
		return timeStatus;
	}
	public void setTimeStatus(String timeStatus) {
		this.timeStatus = timeStatus;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public DateTime getCommentTime() {
		return commentTime;
	}
	public void setCommentTime(DateTime commentTime) {
		this.commentTime = commentTime;
	}
	public Integer getPraiseNum() {
		return praiseNum;
	}
	public void setPraiseNum(Integer praiseNum) {
		this.praiseNum = praiseNum;
	}
	public String getCommentDetail() {
		return commentDetail;
	}
	public void setCommentDetail(String commentDetail) {
		this.commentDetail = commentDetail;
	}
	public String getTopicId() {
		return topicId;
	}
	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}


}
