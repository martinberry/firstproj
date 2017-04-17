package com.ztravel.payment.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ztravel.order.client.config.OrderThriftClientConfig;
import com.ztravel.paygate.wx.client.config.WxPayClientConfig;

@Configuration

@Import({
    PaymentBeanConfig.class,
    OrderThriftClientConfig.class,
    WxPayClientConfig.class
})
public class PaymentModuleConfig {

}
