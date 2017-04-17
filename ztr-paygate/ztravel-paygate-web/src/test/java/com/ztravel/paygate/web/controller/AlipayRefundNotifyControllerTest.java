package com.ztravel.paygate.web.controller;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.ztravel.paygate.web.test.PaygateWebTest;

public class AlipayRefundNotifyControllerTest extends PaygateWebTest {

	@Resource
	AlipayRefundNotifyController controller;

	@Test
	public void testRefundNotify() {
		String notify = "sign=4ff437d83c7f5eb0ec518e82bedbc246, result_details=2013091142096412^0.01^SUCCESS$bycoa@10106266.com^2088101964737404^0.00^SUCCESS, notify_time=2013-09-13 17:52:54, sign_type=MD5, notify_type=batch_refund_notify, notify_id=bfe1d5a8f19ced65d71bfc07946f851042, batch_no=2013091310714, success_num=1";

		MockHttpServletRequest req = new MockHttpServletRequest();
		req.setParameters(convertStr2Map(notify));

		String str = controller.refundResult("00000000000000001302", req);
		System.out.println("結果處理:::" + str);
	}
}
