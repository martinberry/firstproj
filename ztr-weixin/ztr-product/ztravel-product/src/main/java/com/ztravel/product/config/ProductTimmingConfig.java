package com.ztravel.product.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 产品定时器 配置,定时器server只需要依赖此配置即可
 * @author liuzhuo
 *
 */

@Configuration
@ComponentScan({
	"com.ztravel.product.timming.service",
	"com.ztravel.product.timming.dao",
	"com.ztravel.product.dao"
	})
public class ProductTimmingConfig {

}
