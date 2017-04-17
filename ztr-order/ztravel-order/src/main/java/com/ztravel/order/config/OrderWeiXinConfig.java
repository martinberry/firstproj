package com.ztravel.order.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhaopengfei
 * 扫描微信端建单时需要单相关包
 * */
@Configuration
@ComponentScan({
	"com.ztravel.order.client",
	"com.ztravel.order.wx.controller",
	"com.ztravel.order.wx.service",
	"com.ztravel.order.dao",
	"com.ztravel.order.common.service",
	"com.ztravel.order.weixin.controller",
	"com.ztravel.order.weixin.service",
	"com.ztravel.order.back.dao"
})
public class OrderWeiXinConfig {

}
