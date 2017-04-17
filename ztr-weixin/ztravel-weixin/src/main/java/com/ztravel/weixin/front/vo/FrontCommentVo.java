package com.ztravel.weixin.front.vo;

public class FrontCommentVo {
	private String commentId;
	private String nickName;
	private String commentTime;
	private String praiseNum;
	private String commentDetail;
	private String headImage;
	private String timeStatus;
	private String anonymous;
	
	public String getAnonymous() {
		return anonymous;
	}
	public void setAnonymous(String anonymous) {
		this.anonymous = anonymous;
	}
	public String getTimeStatus() {
		return timeStatus;
	}
	public void setTimeStatus(String timeStatus) {
		this.timeStatus = timeStatus;
	}
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getCommentTime() {
		return commentTime;
	}
	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}
	public String getPraiseNum() {
		return praiseNum;
	}
	public void setPraiseNum(String praiseNum) {
		this.praiseNum = praiseNum;
	}
	public String getCommentDetail() {
		return commentDetail;
	}
	public void setCommentDetail(String commentDetail) {
		this.commentDetail = commentDetail;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	
}
