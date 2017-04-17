package com.ztravel.order.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;



@Configuration
@ComponentScan({
	"com.ztravel.order.dao",
	"com.ztravel.order.common.service",
	"com.ztravel.order.client.thrift.service.impl"
})
public class OrderThriftConfig {

}
