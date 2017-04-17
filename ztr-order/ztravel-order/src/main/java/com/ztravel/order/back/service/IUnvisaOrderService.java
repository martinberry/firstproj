package com.ztravel.order.back.service;

import java.util.List;

import com.ztravel.order.back.criteria.LocalOrderSearchCriteria;
import com.ztravel.order.back.vo.OrderListVO;

public interface IUnvisaOrderService {
	public List<OrderListVO> searchLocalOrder(LocalOrderSearchCriteria criteria)throws Exception;
	public Integer countLocalOrders(LocalOrderSearchCriteria criteria) throws Exception;

}
