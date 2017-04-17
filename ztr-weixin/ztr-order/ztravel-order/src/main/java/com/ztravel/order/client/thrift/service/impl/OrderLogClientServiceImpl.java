package com.ztravel.order.client.thrift.service.impl;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Service;

import com.travelzen.swordfish.thrift.client.annotation.ThriftServiceEndpoint;
import com.ztravel.order.client.service.IOrderLogClientService;
import com.ztravel.order.dao.IOrderDao;
import com.ztravel.order.dao.IOrderLogDao;
import com.ztravel.order.po.Order;
import com.ztravel.order.po.OrderLog;

@Service(value="OrderLogClientServiceImpl")
@ThriftServiceEndpoint
public class OrderLogClientServiceImpl implements IOrderLogClientService{
	@Resource
	private IOrderLogDao orderLogDaoImpl;

	@Resource
	IOrderDao orderDao;

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
		Order order = new Order();
		if( orderDao.selectByOrderCode(orderId) != null){
			order = orderDao.selectByOrderCode(orderId).get(0);
		}
		record.setId(UUID.randomUUID().toString());
		if(order != null){
			record.setOrderId(order.getOrderId());
		}
		record.setOperator(operator);
		record.setOperateTime(new Date());
		record.setContent(content);
		record.setRemark(remark);
		save(record);
	}

}
