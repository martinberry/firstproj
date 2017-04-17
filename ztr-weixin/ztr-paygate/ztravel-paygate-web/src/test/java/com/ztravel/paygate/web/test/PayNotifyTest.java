/**
 *
 */
package com.ztravel.paygate.web.test;

import java.net.URL;
import java.util.Map;

import com.ztravel.paygate.core.enums.PayState;
import com.ztravel.paygate.core.enums.PaygateError;
import com.ztravel.paygate.core.util.BeanMapUtil;
import com.ztravel.paygate.core.util.PaygateEncryptUtil;
import com.ztravel.paygate.web.dto.result.PayResult;
import com.ztravel.paygate.web.util.HttpUtil;

/**
 * @author zuoning.shen
 *
 */
public class PayNotifyTest {
    public static void main(String[] args) throws Exception{
        PayResult payResult = new PayResult();
        payResult.setClientId("900001");
        payResult.setGateType("0001");
        payResult.setRetCode(PaygateError.SUCCESS.code());
        payResult.setRetMsg(PaygateError.SUCCESS.desc());
        payResult.setOrderNum("1506090962");
        payResult.setPayState(PayState.SUCCESS);
        payResult.setBankPaymentTime("2015-06-09 18:20:15");
        payResult.setTraceNum("2015060900001000120060205994");
        payResult.setAmount(1);
        payResult.setService("order_pay");
        Map<String,String> params = BeanMapUtil.mapBean(payResult);
        params.put("sign", "bce882b6daa9ad6652a03eb9e4295647"/*PaygateEncryptUtil.getSignStr(params, "44598166726f4eb5a222a1e90d717415")*/);
        try {
            String notifyUrl = "http://192.168.164.135:8480/ztravel-payment-server/notify/payNotify";
            System.out.println(params);
            System.out.println(notifyUrl);
            String response = HttpUtil.doFormPost(new URL(notifyUrl), params);
            System.out.println("response:" + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}