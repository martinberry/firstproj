package com.ztravel.product.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({
	"com.ztravel.product.front.controller",
	"com.ztravel.product.front.service",
	"com.ztravel.product.front.dao",
	"com.ztravel.product.front.book",
	"com.ztravel.product.common"
	})
public class ProductFrontModuleConfig {

}
