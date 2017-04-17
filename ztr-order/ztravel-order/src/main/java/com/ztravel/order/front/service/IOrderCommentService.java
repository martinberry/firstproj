package com.ztravel.order.front.service;

import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.entity.OrderComment;

/**
 * 订单评论
 * @author liuzhuo
 */
public interface IOrderCommentService {

	/**
	 * 新增评论
	 * @param orderCommon
	 * @throws Exception
	 */
	public AjaxResponse insert(OrderComment comment) throws Exception;

//	/**
//	 * 前台产品查询订单评价
//	 * @param searchEntity
//	 * @return
//	 * @throws Exception
//	 */
//	List<OrderCommentFronVo> searchOrderComment(OrderCommentSearchEntity searchEntity) throws Exception;
//
//	/**
//	 * 获取查询订单条数
//	 * @param searchEntity
//	 * @return
//	 * @throws Exception
//	 */
//	Long countOrderComment(OrderCommentSearchEntity searchEntity) throws Exception;

}
