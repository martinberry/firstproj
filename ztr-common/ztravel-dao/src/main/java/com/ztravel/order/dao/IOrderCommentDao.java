package com.ztravel.order.dao;

import java.util.List;

import com.ztravel.common.entity.OrderComment;
import com.ztravel.common.entity.OrderCommentSearchEntity;

/**
 * 订单评价dao
 * @author liuzhuo
 */
public interface IOrderCommentDao {
	/**
	 * 添加评论
	 * @param orderComment
	 */
	public void insert(OrderComment orderComment);

	/**
	 * 更新评论状态
	 * @param orderComment
	 * @return
	 */
	public void update(OrderComment orderComment);

	/**
	 * 根据条件查询评论列表(分页,不分页共用)
	 * @param searchEntity
	 * @return
	 */
	public List<OrderComment> searchComment(OrderCommentSearchEntity searchEntity);

	/**
	 * 统计查询记录条数
	 * @param searchEntity
	 * @return
	 */
	public Long countComment(OrderCommentSearchEntity searchEntity);

	/**
	 * 查询评论详情
	 * @param commentId
	 * @return
	 */
	public OrderComment getCommentDetail(String commentId);

}
