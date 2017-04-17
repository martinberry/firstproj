package com.ztravel.order.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ztravel.sraech.client.config.SearchClientConfig;

@Configuration
@Import({
    SearchClientConfig.class
})
@ComponentScan({"com.ztravel.order.back.freetravel.controller",
	                              "com.ztravel.order.back.freetravel.service",
	                              "com.ztravel.order.back.freetravel.dao",
	                              "com.ztravel.order.back.controller",
	                              "com.ztravel.order.back.service",
	                              "com.ztravel.order.dao",
	                              "com.ztravel.reuse.order.service",
	                              "com.ztravel.order.common.service"})
public class OrderBackModuleConfig {

}
