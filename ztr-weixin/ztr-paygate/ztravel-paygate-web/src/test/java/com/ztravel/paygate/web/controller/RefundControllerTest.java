package com.ztravel.paygate.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import com.travelzen.framework.core.time.DateTimeUtil;
import com.travelzen.framework.core.util.RandomUtil;
import com.ztravel.paygate.core.enums.GateType;
import com.ztravel.paygate.core.util.PaygateEncryptUtil;
import com.ztravel.paygate.web.dto.request.RequestRefundBean;
import com.ztravel.paygate.web.dto.result.CommonSyncResult;
import com.ztravel.paygate.web.test.PaygateWebTest;

public class RefundControllerTest extends PaygateWebTest {

	@Resource
	RefundController controller;

	private class RefundBean {
		private String orderNum;
		private String traceNum;

		public RefundBean(String orderNum, String traceNum) {
			super();
			this.orderNum = orderNum;
			this.traceNum = traceNum;
		}

	}

//	@Test
//	public void testChinapnrRefund() {
//		List<RefundBean> refundList = new ArrayList<RefundBean>();
//		refundList.add(new RefundBean("20130911145726260767", "2013091103158106"));
//		refundList.add(new RefundBean("20130910180244512156", "2013091003156574"));
//		refundList.add(new RefundBean("131205015128201", "2013120559980217"));
//		refundList.add(new RefundBean("RC000000000000010203", "2013120870101177"));
//		refundList.add(new RefundBean("RC000000000000009703", "2013120560122163"));
//		refundList.add(new RefundBean("RC000000000000009404", "2013120559988917"));
//		refundList.add(new RefundBean("031312103112263", "2013091703169154"));
//		refundList.add(new RefundBean("030312103112261", "2013091603166929"));
//		refundList.add(new RefundBean("039312103112263", "2013091703169475"));
//		refundList.add(new RefundBean("030312103112262", "2013091703169049"));
//		refundList.add(new RefundBean("0303121031132263", "2013091703169092"));
//		refundList.add(new RefundBean("035312103112263", "2013091703169460"));
//		refundList.add(new RefundBean("030312103142263", "2013091703169683"));
//		refundList.add(new RefundBean("530312103112263", "2013091703169535"));
//		refundList.add(new RefundBean("035312103142263", "2013091703169861"));
//		refundList.add(new RefundBean("033312103142263", "2013091703169795"));
//		refundList.add(new RefundBean("130918165243101", "2013091803173534"));
//		refundList.add(new RefundBean("RC000000000000001203", "2013100803208979"));
//		refundList.add(new RefundBean("ORD20131005213028503", "2013100803208911"));
//		refundList.add(new RefundBean("RC000000000000001501", "2013100803208965"));
//		refundList.add(new RefundBean("RC000000000000001302", "2013100803208945"));
//		refundList.add(new RefundBean("RC000000000000001301", "2013100803208944"));
//		refundList.add(new RefundBean("RC000000000000001201", "2013100803208932"));
//		refundList.add(new RefundBean("131009114749806", "2013100903210426"));
//		refundList.add(new RefundBean("RC000000000000001401", "2013100803208959"));
//		refundList.add(new RefundBean("RC000000000000002001", "2013100803209408"));
//		refundList.add(new RefundBean("RC000000000000002101", "2013100903210551"));
//		refundList.add(new RefundBean("131010202529901", "2013101003215936"));
//		refundList.add(new RefundBean("131010194158302", "2013101003215856"));
//		refundList.add(new RefundBean("131011090605001", "2013101103217101"));
//		refundList.add(new RefundBean("131010194640303", "2013101003215864"));
//		refundList.add(new RefundBean("RC000000000000003001", "2013101003213816"));
//		refundList.add(new RefundBean("131008163034202", "2013101003213737"));
//		refundList.add(new RefundBean("RC000000000000002602", "2013100903211556"));
//		refundList.add(new RefundBean("RC000000000000003101", "2013101003214249"));
//		refundList.add(new RefundBean("RC000000000000002801", "2013100903211594"));
//		refundList.add(new RefundBean("RC000000000000002202", "2013100903210698"));
//		refundList.add(new RefundBean("131010155205301", "2013101003213989"));
//		refundList.add(new RefundBean("131010145120901", "2013101003213738"));
//		refundList.add(new RefundBean("131010164232601", "2013101003214140"));
//		refundList.add(new RefundBean("20131011203830994619", "2013101103218681"));
//		refundList.add(new RefundBean("RC000000000000008603", "2013112373944077"));
//		refundList.add(new RefundBean("RC000000000000008405", "2013111859917341"));
//		refundList.add(new RefundBean("RC000000000000008903", "2013112889779585"));
//		refundList.add(new RefundBean("030012103112263", "2013091703169177"));
//		refundList.add(new RefundBean("030312103112263", "2013091603166960"));
//		refundList.add(new RefundBean("030312103212263", "2013091703169137"));
//		refundList.add(new RefundBean("030312103112264", "2013091703168901"));
//		refundList.add(new RefundBean("030312102112263", "2013091703169152"));
//		refundList.add(new RefundBean("070312103112263", "2013091703169353"));
//		refundList.add(new RefundBean("20130910173835187764", "2013091003156470"));
//		refundList.add(new RefundBean("RC000000000000000102", "2013092903199693"));
//		refundList.add(new RefundBean("RC000000000000000103", "2013092903199694"));
//		refundList.add(new RefundBean("RC000000000000000201", "2013093003200231"));
//		refundList.add(new RefundBean("RC000000000000000301", "2013093003200232"));
//		refundList.add(new RefundBean("RC000000000000000602", "2013093003200241"));
//		refundList.add(new RefundBean("RC000000000000000601", "2013093003200238"));
//		refundList.add(new RefundBean("ORD20131008100933601", "2013100803208733"));
//		refundList.add(new RefundBean("ORD20131004104706804", "2013100803208799"));
//		refundList.add(new RefundBean("131010201534801", "2013101003215911"));
//		refundList.add(new RefundBean("RC000000000000002503", "2013100903211518"));
//		refundList.add(new RefundBean("RC000000000000002601", "2013100903211549"));
//		for (RefundBean b : refundList) {
//			System.out.println("退款:" + b.orderNum);
//			try {
//				RequestRefundBean bean = new RequestRefundBean();
//				bean.setClientId("100001");
//				bean.setSign("44598166726f4eb5a222a1e90d717415");
//				bean.setGateType(GateType.AliPay.code);
//				bean.setRefundNum(DateTimeUtil.datetime14() + RandomUtil.getRandomStr(4));
//				bean.setOrderNum(b.orderNum);
//				bean.setTraceNum(b.traceNum);
//				bean.setTransAmount(100);
//				bean.setRefundAmount(100);
//				bean.setComment("测试商品退款");
//
//				MockHttpServletRequest request = new MockHttpServletRequest();
//
//				Map<String, String> params = bean2Map(bean);
//				params.put("sign", PaygateEncryptUtil.getSignStr(params, "44598166726f4eb5a222a1e90d717415"));
//				request.setParameters(params);
//				Model model = new ExtendedModelMap();
//
//				CommonSyncResult response = controller.reqRefund(model, request, new MockHttpServletResponse());
//
//				System.out.println("[汇付天下]返回信息:" + response);
//			} catch (Exception e) {
//				System.err.println("出错:" + e.getLocalizedMessage());
//			}
//		}
//	}

	@Test
	public void testAlipayRefund() {
		RequestRefundBean bean = new RequestRefundBean();
		bean.setClientId("900001");
		bean.setGateType(GateType.AliPay.code);
		bean.setRefundNum(DateTimeUtil.datetime14() + RandomUtil.getRandomStr(4));
		bean.setOrderNum("1509026644");
		bean.setTraceNum("1509026644");
		bean.setTransAmount(1);
		bean.setRefundAmount(1);
		bean.setComment("测试商品退款");

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameters(bean2Map(bean));
		Model model = new ExtendedModelMap();

		CommonSyncResult response = controller.reqRefund(model, request, new MockHttpServletResponse());

		System.out.println("[支付宝]返回信息:" + response);
	}
}
