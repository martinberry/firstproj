package com.ztravel.front.member.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.ztravel.order.front.service.IOrderService;
public class UpdateOperateRecordTest {

	@Test
	public void updateOperateRecord() throws Exception{
		 ApplicationContext context = new AnnotationConfigApplicationContext(OrderTestConfig.class);
		 IOrderService orderService  = (IOrderService) context.getBean("orderServiceImpl");
//		orderService.getOrderIdByOrderNum("1505271744561802");

	}

}
