package com.ztravel.order.client.service;

import com.ztravel.order.po.OrderLog;


/**
 * orderKig client 提供出去的接口
 * @author liuzhuo
 *
 */
public interface IOrderLogClientService {
	void save(OrderLog orderLog)throws Exception;
	void save(String orderId,String operator, String content, String remark)throws Exception;
}
