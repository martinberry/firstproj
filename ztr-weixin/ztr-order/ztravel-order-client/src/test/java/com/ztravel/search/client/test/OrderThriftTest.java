package com.ztravel.search.client.test;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.enums.PaymentType;
import com.ztravel.common.order.OrderPaidBean;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.order.client.config.OrderThriftClientConfig;
import com.ztravel.order.client.service.IOrderThriftClientService;


/**
 * test case
 * 1:本地调试
 *   global/ztr-search/search-server.properties
 *   ztr.search.ip=127.0.0.1  --> ztr.search.ip=192.168.164.135
 *
 *
 * @author liuzhuo
 *
 */

@ContextConfiguration(classes={
		OrderThriftClientConfig.class
})
public class OrderThriftTest extends AbstractJUnit4SpringContextTests{

	@Resource(name="orderThriftClientServiceImpl")
	IOrderThriftClientService orderthriftClientServiceImpl;

	@Test
	public void test(){
		String orderCode = "1509143663";
		try {
			long timestart = new Date().getTime();
			CommonResponse response = orderthriftClientServiceImpl.updateOrderToCancled(orderCode);
			long timeEnd = new Date().getTime();
			System.out.println(timeEnd-timestart);
			TZBeanUtils.describe(response);
		} catch (Exception e) {
			// TODO: handle exception
		}
//		OrderPaidBean orderPayedBean = new OrderPaidBean();
//		orderPayedBean.setOrderId("1506272844");
//		orderPayedBean.setBankPaymentTime("2015-07-07");
//		orderPayedBean.setPaymentType(PaymentType.Alipay);
//		orderPayedBean.setTraceNum("2015061000001000120060279679");
//		try {
//			CommonResponse response = orderthriftClientServiceImpl.updateOrderStatus(orderPayedBean);
//			System.out.println("========"+response.isSuccess());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

//		String orderId = null;
//		String orderId1 = "1508051038323003";
//		String orderId2= "";
//		CommonResponse response = orderthriftClientServiceImpl.weatherOrderCancled(orderId1);
//		TZBeanUtils.describe(response);

	}

}
