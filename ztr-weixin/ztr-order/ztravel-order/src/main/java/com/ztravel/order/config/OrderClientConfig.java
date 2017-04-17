package com.ztravel.order.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * 验证下 相同的包名  @ComponentScan 能否扫描到
 * @author liuzhuo
 *
 */

@Configuration
@ComponentScan({
	"com.ztravel.order.client",
	"com.ztravel.order.dao",
	"com.ztravel.order.po",
	"com.ztravel.order.common.service",
	"com.ztravel.order.client.thrift.service.impl"
})
public class OrderClientConfig {

}
