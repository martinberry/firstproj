package com.ztravel.order.client.service;

import java.util.List;

import com.ztravel.common.entity.OrderCommentSearchEntity;
import com.ztravel.order.client.vo.OrderCommentClientVO;

/**
 * 订单评价
 * @author MH
 */
public interface IOrderCommentClientService {
	/**
	 * 前台产品查询订单评价
	 * @param searchEntity
	 * @return
	 * @throws Exception
	 */
	List<OrderCommentClientVO> searchOrderComment(OrderCommentSearchEntity searchEntity) throws Exception;

	/**
	 * 产品评价条数
	 * @param searchEntity
	 * @return
	 * @throws Exception
	 */
	Long countOrderComment(OrderCommentSearchEntity searchEntity) throws Exception;
	
	List<OrderCommentClientVO> searchOrderCommentByPid(String pid) throws Exception;
	
	Long countOrderCommentByPid(String pid) throws Exception;

}
