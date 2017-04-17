package com.ztravel.order.back.service;

import java.util.List;

import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.entity.OrderCommentSearchEntity;
import com.ztravel.order.back.vo.OrderCommentVO;

public interface IOrderCommentService {

	/**
	 * 订单评论审核
	 * @param orderComment
	 * @return
	 * @throws Exception
	 */
	public AjaxResponse audit(String commentId, Integer auditResult) throws Exception;

	/**
	 * 后台查询订单评价
	 * @param searchEntity
	 * @return
	 * @throws Exception
	 */
	List<OrderCommentVO> searchOrderComment(OrderCommentSearchEntity searchEntity) throws Exception;

	/**
	 * 符合搜索条件的评价条数
	 * @param searchEntity
	 * @return
	 * @throws Exception
	 */
	Long countComments(OrderCommentSearchEntity searchEntity) throws Exception;

	/**
	 * 查询评价详情
	 * @param commentId
	 * @return
	 * @throws Exception
	 */
	OrderCommentVO getCommentDetail(String commentId) throws Exception;

}
