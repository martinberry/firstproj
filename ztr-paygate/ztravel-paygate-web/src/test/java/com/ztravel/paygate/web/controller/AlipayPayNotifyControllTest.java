package com.ztravel.paygate.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.ztravel.paygate.thrift.model.ValSignRequest;
import com.ztravel.paygate.web.test.PaygateWebTest;

public class AlipayPayNotifyControllTest extends PaygateWebTest {

	@Resource
	AlipayPayNotifyController controller;

	@Resource
	ApplicationContext applicationContext;

	@Resource
	RequestMappingHandlerAdapter handler;

	@Resource
	BeanNameUrlHandlerMapping handlerMapping;

	@Resource
	RequestMappingHandlerMapping requestMappingHandlerMapping;

	@Test
	public void testBgPayNotify() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		request.addParameters(mockRequestMap());
		request.setRequestURI("/paygate/alipay/payResult/0/00000000000000001201");
		HandlerAdapter handlerAdapter = applicationContext.getBean(HandlerAdapter.class);
		ModelAndView mv = handlerAdapter.handle(request, response, handlerMapping.getHandler(request).getHandler());
		Map<String, Object> model = mv.getModel();
		System.out.println("通知返回地址:" + mv.getViewName());
	}

	@Test
	public void testFgPayNotify() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setMethod("GET");
		request.addParameters(mockRequestMap());
		if (request.getParameter("subject") != null) {
			request.setParameter("subject", new String(request.getParameter("subject").getBytes("utf-8"), "iso8859-1"));
		}

		MockHttpServletResponse response = new MockHttpServletResponse();
		Model model = new ExtendedModelMap();
		String returnPath = controller.payResult("1", null, request, response, model);
		System.out.println("通知返回地址:" + returnPath);
	}

	private Map<String, String> mockRequestMap() {
		String response = "buyer_id=2088701248004123, trade_no=2013091141206212, use_coupon=N, notify_time=2013-09-11 15:10:11, subject=商品描述, sign_type=MD5, is_total_fee_adjust=N, notify_type=trade_status_sync, out_trade_no=20130911150818194886, gmt_payment=2013-09-11 15:10:11, trade_status=TRADE_SUCCESS, discount=0.00, sign=d06775b5cf62d7cb81d2eb7a0539526a, buyer_email=jscs1@10106266.com, gmt_create=2013-09-11 15:10:10, price=0.01, total_fee=0.01, quantity=1, seller_id=2088101964737404, seller_email=bycoa@10106266.com, notify_id=7e62b234e6c95df6544ce52388b11a0e2o, payment_type=8";
		String values[] = response.split(",");
		ValSignRequest request = new ValSignRequest();
		Map<String, String> map = new HashMap<>();
		request.setBankResponseData(map);
		for (String value : values) {
			String[] pair = value.split("=");
			String k = pair[0].trim();
			String v = "";
			if (pair.length > 1) {
				v = pair[1].trim();
			}
			map.put(k, v);
		}
		System.out.println("处理结果数据:" + map);
		return map;
	}
}
