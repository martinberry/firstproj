package com.ztravel.reuse.order.service;

import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.entity.OrderComment;

public interface IOrderCommentReuseService {
	
	Boolean isOrderAlreadyCommented(String orderId) throws Exception;
	
	AjaxResponse insert(OrderComment comment) throws Exception;
	
	
}
