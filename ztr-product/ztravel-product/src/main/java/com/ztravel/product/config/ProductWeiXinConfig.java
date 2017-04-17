package com.ztravel.product.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhaopengfei
 * 微信config
 * 添加微信相关的controller,service,dao层包及其他组件信息
 * */
@Configuration
@ComponentScan({
	"com.ztravel.product.weixin.controller",
	"com.ztravel.product.weixin.vo",
	"com.ztravel.product.weixin.service",
	"com.ztravel.product.weixin.wo",
	"com.ztravel.product.weixin.entity",
	"com.ztravel.product.common",
	"com.ztravel.product.weixin.controller",
	"com.ztravel.product.weixin.service",
	"com.ztravel.reuse.product.service",
	"com.ztravel.product.client.service",
	"com.ztravel.product.front.dao",
	"com.ztravel.product.topic.dao",
	"com.ztravel.product.dao"
	})
public class ProductWeiXinConfig {

}
