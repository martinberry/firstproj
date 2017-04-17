package com.ztravel.reuse.order.service;

import java.util.List;

import com.travelzen.framework.core.wrapper.Pagination;
import com.ztravel.order.po.OrderLog;
/**
 * 订单日志记录service
 * 前台后台都会用到
 * @author xujunhui
 *
 */
public interface IOrderLogReuseService {
	
	void save(OrderLog orderLog)throws Exception;
	
	void save(String orderId,String operator, String content, String remark)throws Exception;
	
	List<OrderLog> getByOrderId(String orderId)throws Exception;
	
	Pagination<OrderLog> getByOrderIdWithRow(String orderId, int pageNo, int pageSize)throws Exception;
}
