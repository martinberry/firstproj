package com.ztravel.weixin.po;

public class CommentPraise {
	private String openId;
	private String commentId;
	private Integer flag;
	private String praiseId;
	
	public String getPraiseId() {
		return praiseId;
	}
	public void setPraiseId(String praiseId) {
		this.praiseId = praiseId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	

}
