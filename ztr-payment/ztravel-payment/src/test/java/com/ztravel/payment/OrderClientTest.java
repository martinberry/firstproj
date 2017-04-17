/**
 * 
 */
package com.ztravel.payment;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.enums.PaymentType;
import com.ztravel.common.order.OrderPaidBean;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.order.client.config.OrderThriftClientConfig;
import com.ztravel.order.client.service.IOrderThriftClientService;

/**
 * @author zuoning.shen
 *
 */
public class OrderClientTest {
    @Test
    public void testOrderPay() {
        ApplicationContext context = new AnnotationConfigApplicationContext(OrderThriftClientConfig.class);
        IOrderThriftClientService orderThriftClientServiceImpl = (IOrderThriftClientService) context.getBean("orderThriftClientServiceImpl");
        OrderPaidBean bean = new OrderPaidBean();
        bean.setOrderId("201506026246");
        bean.setPaymentType(PaymentType.Alipay);
        bean.setTraceNum("2015060200001000120059541136");
        bean.setBankPaymentTime("2015-06-02 19:07:30");
        CommonResponse res = orderThriftClientServiceImpl.updateOrderStatus(bean);
        System.out.println("Response: "  + TZBeanUtils.describe(res));
    }
}
