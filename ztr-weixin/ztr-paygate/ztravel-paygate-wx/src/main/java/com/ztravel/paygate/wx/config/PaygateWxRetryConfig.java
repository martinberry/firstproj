package com.ztravel.paygate.wx.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath:spring/paygate-task.xml"})
public class PaygateWxRetryConfig {

}
