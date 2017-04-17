package com.ztravel.order.back.vo;

import com.ztravel.common.enums.OrderCommentStatus;

/**
 * 后台评论页面展示
 * @author MH
 */
public class OrderCommentVO {
	/**
	 * 评论id
	 */
	private String id;
	/**
	 * 产品pid
	 */
	private String pid;
	/**
	 * 产品标题
	 */
	private String productTitle;
	/**
	 * 评论用户mid
	 */
	private String mid;
	/**
	 * 评论用户昵称
	 */
	private String nickName;
	/**
	 * 评论内容
	 */
	private String content;
	/**
	 * 评论星级
	 */
	private Integer stars;
	/**
	 * 评论时间
	 */
	private String date;
	/**
	 * 评论状态
	 */
	private String status;
	/**
	 * 评论状态枚举值
	 */
	private OrderCommentStatus statusEnum;

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
	public String getProductTitle() {
		return productTitle;
	}
	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public OrderCommentStatus getStatusEnum() {
		return statusEnum;
	}
	public void setStatusEnum(OrderCommentStatus statusEnum) {
		this.statusEnum = statusEnum;
	}

}
