package com.ztravel.paygate.web.test;
import java.net.URL;
import java.util.Map;

import com.ztravel.paygate.core.enums.PaygateError;
import com.ztravel.paygate.core.enums.RefundState;
import com.ztravel.paygate.core.util.BeanMapUtil;
import com.ztravel.paygate.core.util.PaygateEncryptUtil;
import com.ztravel.paygate.web.dto.result.RefundResult;
import com.ztravel.paygate.web.util.HttpUtil;


public class RefundNotifyTest {
	/**
	 * 支付结果再次通知
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{
		RefundResult refundResult = new RefundResult();
		refundResult.setClientId("900004");
		refundResult.setGateType("0002");//支付�?001,财付�?002
		refundResult.setRetCode(PaygateError.SUCCESS.code());
		refundResult.setRetMsg(PaygateError.SUCCESS.desc());
		refundResult.setOrderNum("150227102818596");//订单�?
		refundResult.setTraceNum("TRACE_T000000002");//支付宝流水号
		refundResult.setRefundNum("DP150227102818596");
		refundResult.setRefundState(RefundState.SUCCESS);
		refundResult.setRefundAmount(79000);
		Map<String,String> params = BeanMapUtil.mapBean(refundResult);
		params.put("sign", PaygateEncryptUtil.getSignStr(params, "44598166726f4eb5a222a1e90d717415"));
		try {
			String notifyUrl = "http://localhost:8082/tops-finance-server/thirdpaynotify/refundNotify";
			System.out.println(params);
			System.out.println(notifyUrl);
			String response = HttpUtil.doFormPost(new URL(notifyUrl), params);
			System.out.println("responsel::::" + response);
		} catch (Exception e) {
//			logger.error("支付结果通知给调用端出现异常.",e);
//			return "E";
			e.printStackTrace();
		}
	}
}
