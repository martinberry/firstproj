package com.ztravel.order.wx.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.OrderConstans;
import com.ztravel.common.entity.OrderComment;
import com.ztravel.common.enums.OrderCommentSource;
import com.ztravel.common.enums.OrderCommentStatus;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.common.valid.BasicValidator;
import com.ztravel.common.valid.ValidResult;
import com.ztravel.order.dao.IOrderCommentDao;
import com.ztravel.order.po.OrderProduct;
import com.ztravel.order.wx.service.IWxOrderCommentService;
import com.ztravel.order.wx.service.IWxOrderService;
import com.ztravel.reuse.order.service.IOrderProductReuseService;


@Service
public class WxOrderCommentServiceImpl implements IWxOrderCommentService {

	@Resource
	IWxOrderService wxOrderServiceImpl;

	@Resource
	IOrderCommentDao orderCommentDao;
	
	@Resource
	IOrderProductReuseService orderProductReuseService;

	@Override
	public AjaxResponse addComment(OrderComment comment) throws Exception {
		completeOrderCommentInfo(comment);
		ValidResult validResult = BasicValidator.valid(comment);
		if(!validResult.isSuccess()) {
			return AjaxResponse.instance(OrderConstans.ORDER_COMMENT_INSERT_VALID_ERR, validResult.getValidMsg());
		}
		orderCommentDao.insert(comment);
		return AjaxResponse.instance(OrderConstans.ORDER_COMMENT_INSERT_SUCCESS_CODE, "");
	}

	/**
	 * 完善用户提交的评论信息
	 * @param orderComment
	 */
	public void completeOrderCommentInfo(OrderComment comment) throws Exception {
		//获取产品相关信息
		OrderProduct product = orderProductReuseService.getOrderProductByOrderId(comment.getOrderId());
		comment.setProductId(product.getProductId());
		comment.setPid(product.getProductNo());
		comment.setProductTitle(product.getProductTitle());
		comment.setDate(new Date());
		comment.setSource(OrderCommentSource.MEMCOMIT);
		comment.setMemberId(SSOUtil.getMemberId());
		if( SSOUtil.getMemberSessionBean() != null ){
			comment.setMid(SSOUtil.getMemberSessionBean().getMid());
		}
		comment.setStatus(OrderCommentStatus.COMMITED);
	}

}
