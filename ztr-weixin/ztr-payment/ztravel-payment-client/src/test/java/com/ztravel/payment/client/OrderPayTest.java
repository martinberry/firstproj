package com.ztravel.payment.client;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.enums.PaymentType;
import com.ztravel.common.payment.OrderPayBean;
import com.ztravel.common.payment.OrderPayInfoBean;
import com.ztravel.common.payment.PaymentResponse;
import com.ztravel.payment.config.PaymentClientConfig;
import com.ztravel.payment.service.IOrderPaymentService;

/**
 *
 */

/**
 * @author zuoning.shen
 *
 */
public class OrderPayTest {

    @Test
    public void testOrderPay() {
        ApplicationContext context = new AnnotationConfigApplicationContext(PaymentClientConfig.class);
        IOrderPaymentService orderPaymentService = (IOrderPaymentService) context.getBean("orderPaymentService");
        OrderPayBean orderPayBean = new OrderPayBean();
        String orderId = "ordp00000000000000001701";
        orderPayBean.setOrderId(orderId);
        orderPayBean.setMemberId("test0605");
        orderPayBean.setPayAmount(10000l);
        orderPayBean.setFgNotifyUrl("");
        orderPayBean.setMobile(false);
        orderPayBean.setPaymentType(PaymentType.Alipay);
        orderPayBean.setComment("订单号: " + orderId);
        orderPayBean.setCouponItemId("citm000000000000000000000001");
        orderPayBean.setUseCoupon(10000l);
        orderPayBean.setUseRewardPoint(0l);
        orderPayBean.setOrderAmount(20000l);

//        String checksum = orderPaymentService.reqChecksum(orderPayBean);
//        System.out.println("checksum: " + checksum);
    	OrderPayInfoBean orderPayInfoBean = orderPaymentService.getOrderPayInfo("1233445");
//        boolean verified = orderPaymentService.verifyChecksum(orderPayBean, checksum);
//        System.out.println("verified: " + verified);
//
//        PaymentResponse res = orderPaymentService.placeOrder(orderPayBean);
//        System.out.println("PaymentResponse: " + TZBeanUtils.describe(res));
    }
}
