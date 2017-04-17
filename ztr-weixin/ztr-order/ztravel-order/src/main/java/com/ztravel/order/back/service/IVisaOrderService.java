package com.ztravel.order.back.service;

import java.util.List;

import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.order.back.criteria.VisaOrderSearchCriteria;
import com.ztravel.order.back.vo.OrderListVO;
import com.ztravel.order.po.MaterialContactor;

public interface IVisaOrderService {

	public List<OrderListVO> searchVisaOrder(VisaOrderSearchCriteria criteria) throws Exception;
	public Integer countVisaOrders(VisaOrderSearchCriteria criteria) throws Exception;
	public AjaxResponse materialSend (MaterialContactor orderContactor,String messageContext);

}
