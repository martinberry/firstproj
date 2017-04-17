package com.ztravel.paygate.server.alipay.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.travelzen.framework.core.time.DateTimeUtil;
import com.travelzen.framework.core.util.RandomUtil;
import com.ztravel.paygate.server.alipay.AlipayServiceHandler;
import com.ztravel.paygate.thrift.model.PaySignRequest;
import com.ztravel.paygate.thrift.model.PaySignResponse;
import com.ztravel.paygate.thrift.model.QueryRequest;
import com.ztravel.paygate.thrift.model.QueryResponse;
import com.ztravel.paygate.thrift.model.RefundRequest;
import com.ztravel.paygate.thrift.model.RefundResponse;
import com.ztravel.paygate.thrift.model.RefundValSignRequest;
import com.ztravel.paygate.thrift.model.RefundValSignResponse;
import com.ztravel.paygate.thrift.model.ValSignRequest;

public class AlipayServiceHandlerTest {

	/**
	 * 
	 */
	@Test
	public void signRequestTest() throws Exception {
		PaySignRequest request = new PaySignRequest();
		request.setBgNotifyUrl("http://192.168.163.127:8090/web/notify.do");
		request.setFgNotifyUrl("http://192.168.163.127:8090/web/notify.do?fg=1");
		request.setOrderNum("2013000012030214");
		request.setComment("测试商品");
		request.setAmount(1);

		AlipayServiceHandler handler = new AlipayServiceHandler();
		PaySignResponse response = handler.paySign(request);
		System.out.println("ret code:" + response.getRetCode());
		System.out.println("ret msg :" + response.getRetMsg());
		System.out.println("bank url : " + response.getBankPayUrl());
		System.out.println("extra infos : " + response.getExtraInfos());
	}

	@Test
	public void testValSign() throws Exception {
		// buyer_id=2088701248004123,
		// trade_no=2013090524808512,
		// use_coupon=null, notify_time=2013-09-05 14:37:23,
		// subject=ÉÌÆ·ÃèÊö, is_total_fee_adjust=null,
		// notify_type=trade_status_sync,
		// out_trade_no=20130905143542525188,
		// gmt_payment=null, trade_status=TRADE_SUCCESS,
		// discount=null, sign=12daac9f31164e55579a4e59e303dc1d,
		// buyer_email=jscs1@10106266.com, gmt_create=null, price=null,
		// total_fee=0.01, quantity=null, seller_id=2088101964737404,
		// notify_id=RqPnCoPT3K9%2Fvwbh3I72IdEoFLaFvYgH6wZuyWpGCfPOOQHUpEoBjbplj8VXFl4LAz4W,
		// seller_email=bycoa@10106266.com,
		// payment_type=8
		// 新版的返回信息
		// buyer_id=2088701248004123, trade_no=2013090525001012,
		// exterface=create_direct_pay_by_user, notify_time=2013-09-05 15:52:19,
		// subject=ååæè¿°, sign_type=MD5,
		// notify_type=trade_status_sync, out_trade_no=20130905155029153104,
		// trade_status=TRADE_SUCCESS, sign=fcd7f9fb6974af2afbf4e722fb15bd2d,
		// buyer_email=jscs1@10106266.com, is_success=T, fg=1, total_fee=0.01,
		// seller_id=2088101964737404, seller_email=bycoa@10106266.com,
		// notify_id=RqPnCoPT3K9%2Fvwbh3I72IdEoEH2s1pwNMqK3xGC4kv6YlFhaEv1kF2SG5uZ7a0ebNXRa,
		// payment_type=8
		Map<String, String> responseData = mockRequestMap();

		AlipayServiceHandler handler = new AlipayServiceHandler();
		ValSignRequest request = new ValSignRequest();
		request.setFgNotify(false);
		request.setBankResponseData(responseData);
		handler.payValSign(request);
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

	@Test
	public void testRefund() throws Exception {
		AlipayServiceHandler handler = new AlipayServiceHandler();
		RefundRequest request = new RefundRequest();
		request.setRefundNum(DateTimeUtil.date8() + RandomUtil.getRandomStr(5));
		// request.setOrderNum("20130910173835187764");
		request.setTraceNum("201309114209641333");
		request.setRefundTime(DateTimeUtil.datetime14Readable());
		request.setNotifyUrl("http://180.169.46.150:8280/ztravel-paygate-web/demo/bgNotify.jsp");
		request.setRefundAmount(1);
		request.setComment("测试退款");
		RefundResponse response = handler.refund(request);
		System.out.printf("返回结果:%s,%s", response.getRetCode(), response.getRetMsg());
	}

	@Test
	public void testRefundNotify() throws Exception {
		String notify = "sign=4ff437d83c7f5eb0ec518e82bedbc246, result_details=2013091142096412^0.01^SUCCESS$bycoa@10106266.com^2088101964737404^0.00^SUCCESS, notify_time=2013-09-13 17:52:54, sign_type=MD5, notify_type=batch_refund_notify, notify_id=bfe1d5a8f19ced65d71bfc07946f851042, batch_no=2013091310714, success_num=1";
		Map<String, String> map = new HashMap<>();
		for (String value : notify.split(",")) {
			String[] pair = value.split("=");
			String k = pair[0].trim();
			String v = "";
			if (pair.length > 1) {
				v = pair[1].trim();
			}
			map.put(k, v);
		}
		AlipayServiceHandler handler = new AlipayServiceHandler();
		RefundValSignRequest request = new RefundValSignRequest();
		request.setPartner("");
		request.setRefundResponse(map);
		RefundValSignResponse response = handler.refundValSign(request);
		System.out.printf("验签返回结果:%s,%s,%s", response.getRetCode(), response.getRetMsg(), response.getRefundNum());
	}

	@Test
	public void testQuery() throws Exception {
		AlipayServiceHandler handler = new AlipayServiceHandler();
		QueryRequest request = new QueryRequest();
		request.setOrderNum("RC010000000000014402");
		// request.setTraceNum("2014011854111512");
		QueryResponse response = handler.query(request);
		System.out
				.printf("返回结果:%s,%s,%s,%s", response.getOrderNum(), response.getRetCode(), response.getRetMsg(), response.getExtraInfos());

	}
}
