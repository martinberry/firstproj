package com.ztravel.paygate.wx.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ztravel.common.bean.DataSourceAopConfig;

@Configuration
@ComponentScan({ "com.ztravel.paygate.wx.service", "com.ztravel.paygate.wx.controller", "com.ztravel.paygate.wx.dao"})
@Import({
	DataSourceAopConfig.class,
	PaygateWxRetryConfig.class
})
public class PaygateWxConfig {

}
