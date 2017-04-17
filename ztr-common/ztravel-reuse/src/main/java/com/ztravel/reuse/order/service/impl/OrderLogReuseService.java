package com.ztravel.reuse.order.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.travelzen.framework.core.wrapper.Pagination;
import com.ztravel.order.dao.IOrderLogDao;
import com.ztravel.order.po.OrderLog;
import com.ztravel.reuse.order.service.IOrderLogReuseService;

@Service
public class OrderLogReuseService implements IOrderLogReuseService {
	@Resource
	private IOrderLogDao orderLogDaoImpl;

	@Override
	public void save(OrderLog orderLog) throws Exception {
		if(StringUtils.isBlank(orderLog.getId())){
			throw new IllegalArgumentException("record primary key value is blank");
		}
		orderLogDaoImpl.insert(orderLog);
	}

	@Override
	public void save(String orderId, String operator, String content, String remark) throws Exception {
		OrderLog record = new OrderLog();
		record.setId(UUID.randomUUID().toString());
		record.setOrderId(orderId);
		record.setOperator(operator);
		record.setOperateTime(new Date());
		record.setContent(content);
		record.setRemark(remark);
		save(record);
	}

	@Override
	public List<OrderLog> getByOrderId(String orderId) throws Exception {
		return orderLogDaoImpl.selectByOrderId(orderId);
	}

	@Override
	public Pagination<OrderLog> getByOrderIdWithRow(String orderId, int pageNo, int pageSize) throws Exception {
		Pagination<OrderLog> page = new Pagination<OrderLog>();
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("orderId", orderId);
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);
		page.setTotalItemCount(orderLogDaoImpl.countByOrderId(orderId));
		page.setData(orderLogDaoImpl.selectByOrderIdWithRow(orderId, page.getStart(), page.getPageSize()));
		return page;
	}

}
