package com.ztravel.common.entity;

import java.util.Date;

import org.joda.time.DateTime;

import com.ztravel.common.enums.OrderCommentStatus;

/**
 * 订单评论查询实体类
 * @author liuzhuo
 */
public class OrderCommentSearchEntity extends PaginationEntity {

	/**
	 * 产品标题
	 */
	private String productTitle;

	/**
	 * 产品id(ObjectId)
	 */
	private String productId;

	/**
	 * 产品pid
	 */
	private String pid;

	/**
	 * 会员id(会员表主键)
	 */
	private String memberId;

	/**
	 * 会员mid(会员表mid,展示用)
	 */
	private String mid;

	/**
	 * 订单id
	 */
	private String orderId;

	/**
	 * 评价时间--始
	 */
	private Date dateBegin;

	/**
	 * 评价时间--止
	 */
	private Date dateEnd;

	/**
	 * 评价状态
	 */
	private OrderCommentStatus status;

//	/**
//	 * 返回记录条数
//	 */
//	private Integer limit;
//
//	/**
//	 * 记录偏移量
//	 */
//	private Integer skip;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Date getDateBegin() {
		return dateBegin;
	}

	public void setDateBegin(Date dateBegin) {
		this.dateBegin = dateBegin;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public OrderCommentStatus getStatus() {
		return status;
	}

	public void setStatus(OrderCommentStatus status) {
		this.status = status;
	}

//	public Integer getLimit() {
//		return limit;
//	}
//
//	public void setLimit(Integer limit) {
//		this.limit = limit;
//	}
//
//	public Integer getSkip() {
//		return skip;
//	}
//
//	public void setSkip(Integer skip) {
//		this.skip = skip;
//	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


}
