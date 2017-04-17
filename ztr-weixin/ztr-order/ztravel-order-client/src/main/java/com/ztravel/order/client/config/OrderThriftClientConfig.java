package com.ztravel.order.client.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import com.travelzen.swordfish.thrift.client.config.ThriftClientProxyConfig;


@Configuration
@ImportResource({
	"classpath:spring/ztravel-order-client.xml"
})
@Import({
	ThriftClientProxyConfig.class,
	OrderThriftClientResourcesConfig.class
})
public class OrderThriftClientConfig {

}
