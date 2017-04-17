package com.ztravel.product.client.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 产品client
 * @author haofan.wan
 *
 */

@Configuration
@ComponentScan({
	"com.ztravel.product.client.service",
	"com.ztravel.product.dao",
	"com.ztravel.product.topic.dao",
	"com.ztravel.reuse.product.service"
	})
public class ProductClientConfig {

}
