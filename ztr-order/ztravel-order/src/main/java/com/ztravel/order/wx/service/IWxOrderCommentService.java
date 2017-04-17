package com.ztravel.order.wx.service;

import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.entity.OrderComment;

public interface IWxOrderCommentService {

	AjaxResponse addComment(OrderComment comment) throws Exception;

}
