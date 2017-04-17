package com.ztravel.paygate.web.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.util.Assert;

import com.travelzen.framework.core.time.DateTimeUtil;
import com.travelzen.framework.core.util.RandomUtil;
import com.ztravel.paygate.core.enums.GateType;
import com.ztravel.paygate.core.enums.PaygateError;
import com.ztravel.paygate.core.util.BeanMapUtil;
import com.ztravel.paygate.core.util.PaygateEncryptUtil;
import com.ztravel.paygate.web.dto.middlebean.PayConfirmBean;
import com.ztravel.paygate.web.dto.request.RequestPayBean;
import com.ztravel.paygate.web.test.PaygateWebTest;

/**
 * 支付请求测试
 * 
 * @author dingguangxian
 * 
 */
public class PayControllerTest extends PaygateWebTest {

	@Resource
	PayController controller;

	@Test
	public void testAlipay() throws Exception {
	    RequestPayBean bean = new RequestPayBean();
        bean.setOrderNum(DateTimeUtil.datetime14() + RandomUtil.getRandomStr(4));
        bean.setAmount(100);
        bean.setClientId("900001");
        bean.setComment("ztravel test");
        bean.setFgNotifyUrl("");
        bean.setMobilePay(false);
        bean.setGateType(GateType.AliPay.code);
        bean.setUserIP("127.0.0.1");
        Map<String, String> params = BeanMapUtil.mapBean(bean);
        String sign = PaygateEncryptUtil.getSignStr(params, "44598166726f4eb5a222a1e90d717415");
        System.out.println(sign);
        bean.setSign(sign);
        System.out.println(PaygateEncryptUtil.verifySignStr(params, bean.getSign(), "44598166726f4eb5a222a1e90d717415"));

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameters(bean2Map(bean));
		Model model = new ExtendedModelMap();

		controller.reqPay(model, request);

		Map map = model.asMap();
		Assert.notNull(map);
		Assert.isNull(map.get("resultBean"));

		PayConfirmBean confirmBean = (PayConfirmBean) map.get("confirmBean");
		Assert.notNull(confirmBean);
		Assert.notNull(confirmBean.getPayURL());
		Assert.isTrue(PaygateError.SUCCESS.code().equals(confirmBean.getRetCode()));
		Assert.isTrue(PaygateError.SUCCESS.desc().equals(confirmBean.getRetMsg()));
		Assert.isTrue(bean.getOrderNum().equals(confirmBean.getOrderNum()));
	}
}
