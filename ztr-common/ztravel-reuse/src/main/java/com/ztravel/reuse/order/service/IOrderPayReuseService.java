package com.ztravel.reuse.order.service;

import com.ztravel.order.po.CommonOrder;
import com.ztravel.order.po.Order;
import com.ztravel.reuse.order.entity.OrderPayVo;

public interface IOrderPayReuseService {

	OrderPayVo buildOrderPayVoByOrderId(Order order,String memberId) throws Exception;

	OrderPayVo buildOrderPayVoByOrdeAndCommonOrder(Order order, CommonOrder commonOrder,String memberId) throws Exception;
}
