package com.ztravel.paygate.wx.client.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import com.travelzen.swordfish.thrift.client.config.ThriftClientProxyConfig;


/**
 * 微信支付 client 配置
 * @author haofan.wan
 *
 */
@Configuration
@ImportResource({
	"classpath:spring/ztr-paygate-wx-client.xml"
})
@Import({
	ThriftClientProxyConfig.class,
	ThriftClientResourcesConfig.class
})
public class WxPayClientConfig {

}
