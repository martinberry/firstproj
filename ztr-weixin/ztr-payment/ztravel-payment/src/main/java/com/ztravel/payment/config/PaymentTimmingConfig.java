package com.ztravel.payment.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 产品定时器 配置,定时器server只需要依赖此配置即可
 * @author liuzhuo
 *
 */

@Configuration
@ComponentScan({
	"com.ztravel.payment.timing",
	"com.ztravel.payment.po",
	"com.ztravel.payment.bo",
	"com.ztravel.payment.dao"
	})
public class PaymentTimmingConfig {

}
