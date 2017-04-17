package com.ztravel.paygate.wx.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "com.ztravel.paygate.wx.client", "com.ztravel.paygate.wx.dao"})
public class PaygateWxThriftConfig {

}
