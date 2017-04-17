/**
 * 
 */
package com.ztravel.payment.server;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.constants.OperatorConstants;
import com.ztravel.common.enums.PaymentType;
import com.ztravel.common.enums.ProductType;
import com.ztravel.common.payment.OrderPayBean;
import com.ztravel.common.payment.PaymentResponse;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.payment.server.config.AppConfig;
import com.ztravel.payment.service.IOrderPaymentService;

/**
 * @author zuoning.shen
 *
 */
public class OrderPayTest {
    @Test
    public void testPayOrder() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        IOrderPaymentService orderPaymentService = (IOrderPaymentService) context.getBean("OrderPaymentService");
        OrderPayBean orderPayBean = new OrderPayBean();
        String orderId = "test000000062901";
        String memberId = "MEMB1506250952540205";
        orderPayBean.setOrderId(orderId);
        orderPayBean.setProductType(ProductType.TRAVEL);
        orderPayBean.setMemberId(memberId);
        orderPayBean.setOrderAmount(200l);
        orderPayBean.setPayAmount(100l);
        orderPayBean.setUseRewardPoint(0l);
        orderPayBean.setUseCoupon(100l);
        orderPayBean.setCouponItemId("citm000000000000000000000615");
        orderPayBean.setFgNotifyUrl("");
        orderPayBean.setMobile(false);
        orderPayBean.setPaymentType(PaymentType.Alipay);
        orderPayBean.setComment("订单号： " + orderId);

        PaymentResponse res = orderPaymentService.placeOrder(orderPayBean);
        System.out.println("PaymentResponse: " + TZBeanUtils.describe(res));
    }
    
    //@Test
    public void testCancelOrder() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        IOrderPaymentService orderPaymentService = (IOrderPaymentService) context.getBean("OrderPaymentService");
        String orderId = "test000000061801";
//        CommonResponse res = orderPaymentService.cancelOrder(orderId, OperatorConstants.AUTO_USER);
//        System.out.println("PaymentResponse: " + TZBeanUtils.describe(res));
    }
}
