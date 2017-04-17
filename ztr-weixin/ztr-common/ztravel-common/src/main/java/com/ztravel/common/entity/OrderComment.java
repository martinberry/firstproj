package com.ztravel.common.entity;

import java.util.Date;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;
import com.ztravel.common.enums.OrderCommentSource;
import com.ztravel.common.enums.OrderCommentStatus;

/**
 * 订单评价
 * @author liuzhuo
 *
 */

@Entity(value="OrderComment", noClassnameStored=true)
public class OrderComment {

	@Id
	ObjectId id;

	/**
	 * 产品id(ObjectId)
	 */
	@NotEmpty(message="产品id不能为空")
	private String productId;

	/**
	 * 产品pid(展示用)
	 */
	@NotEmpty(message="产品pid不能为空")
	private String pid;

	/**
	 * 订单id(订单表主键,非展示)
	 */
	@NotEmpty(message="订单id不能为空")
	private String orderId;

	/**
	 * 会员id(建单人)
	 * (会员表主键,非展示)
	 */
	@NotEmpty(message="会员id不能为空")
	private String memberId;
	/**
	 * 会员mid(展示用)
	 */
	@NotEmpty(message="会员mid不能为空")
	private String mid;

	/**
	 * 产品标题
	 */
	@NotEmpty(message="产品标题不能为空")
	private String productTitle;

	/**
	 * 评价内容
	 */
	private String comment;

	/**
	 * 评价时间
	 */
	private Date date;

	/**
	 * 评价状态
	 */
	private OrderCommentStatus status;

	/**
	 * 评价星级
	 */
	private Integer stars;

	/**
	 * 评论操作人
	 */
	private String operator;

	/**
	 * 评论来源(用户自己评论或者后台添加)
	 */
//	@NotEmpty(message="评价来源不能为空")
//	private String source;
	private OrderCommentSource source;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public OrderCommentStatus getStatus() {
		return status;
	}

	public void setStatus(OrderCommentStatus status) {
		this.status = status;
	}

	public Integer getStars() {
		return stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public OrderCommentSource getSource() {
		return source;
	}

	public void setSource(OrderCommentSource source) {
		this.source = source;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

}
