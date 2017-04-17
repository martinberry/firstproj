package com.ztravel.order.client.vo;

/**
 * 用于产品详情页用户评价展示
 * @author MH
 */
public class OrderCommentClientVO {
	/**
	 * 评论id
	 */
	private String id;

	/**
	 * 用户id
	 */
	private String memid;

	/**
	 * 用户头像id
	 */
	private String headImgId;

	/**
	 * 用户昵称
	 */
	private String memNickName;

	/**
	 * 评价时间
	 */
	private String date;

	/**
	 * 评论内容
	 */
	private String comment;

	/**
	 * 订单评分
	 */
	private Integer stars;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemid() {
		return memid;
	}

	public void setMemid(String memid) {
		this.memid = memid;
	}

	public String getHeadImgId() {
		return headImgId;
	}

	public void setHeadImgId(String headImgId) {
		this.headImgId = headImgId;
	}

	public String getMemNickName() {
		return memNickName;
	}

	public void setMemNickName(String memNickName) {
		this.memNickName = memNickName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getStars() {
		return stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}


}
