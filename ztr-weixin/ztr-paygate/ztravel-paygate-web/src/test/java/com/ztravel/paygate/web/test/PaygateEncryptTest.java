/**
 * 
 */
package com.ztravel.paygate.web.test;

import java.util.Map;

import com.ztravel.paygate.core.util.BeanMapUtil;
import com.ztravel.paygate.core.util.PaygateEncryptUtil;
import com.ztravel.paygate.web.dto.request.RequestPayBean;

/**
 * @author zuoning.shen
 *
 */
public class PaygateEncryptTest {

	public static void main(String[] args){
		RequestPayBean bean = new RequestPayBean();
		bean.setOrderNum("RC140903112104023600");
		bean.setAmount(300);
		bean.setClientId("900002");
		bean.setComment("订单号: RC140903112104023600");
		bean.setFgNotifyUrl("/tops-front-purchaser/finance/order/pay/pay_third_party_result");
		bean.setGateType("0004");
		bean.setSign("ee28b040f92c4407101ac7a69a5ac2bb");
		bean.setUserIP("127.0.0.1");
		Map<String, String> params = BeanMapUtil.mapBean(bean);
		System.out.println(PaygateEncryptUtil.verifySignStr(params, bean.getSign(), "44598166726f4eb5a222a1e90d717415"));
	}
}
