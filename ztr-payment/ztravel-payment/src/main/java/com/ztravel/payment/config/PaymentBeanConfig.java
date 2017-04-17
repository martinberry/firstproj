package com.ztravel.payment.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan({ "com.ztravel.common.retry", "com.ztravel.payment.controller", "com.ztravel.payment.service", "com.ztravel.payment.processor", "com.ztravel.payment.paygate",
    "com.ztravel.payment.bo", "com.ztravel.payment.dao", "com.ztravel.payment.event" })
@ImportResource({
    "classpath:spring/ztravel-payment-ctx.xml",
    "classpath:spring/ztravel-payment-async-task.xml"
})
public class PaymentBeanConfig {

}
