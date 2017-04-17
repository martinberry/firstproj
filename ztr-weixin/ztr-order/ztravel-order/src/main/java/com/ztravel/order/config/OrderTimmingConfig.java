package com.ztravel.order.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ztravel.operator.client.config.OperatorClientConfig;
import com.ztravel.payment.config.PaymentClientConfig;
import com.ztravel.product.client.config.ProductClientConfig;

/**
 * 产品定时器 配置,定时器server只需要依赖此配置即可
 * @author liuzhuo
 *
 */

@Configuration
@Import({
	PaymentClientConfig.class,
	ProductClientConfig.class,
	OrderClientConfig.class,
	OperatorClientConfig.class
})
@ComponentScan({
	"com.ztravel.order.timming.service",
	"com.ztravel.order.common.service",
	"com.ztravel.order.timming.dao",
	"com.ztravel.order.dao"
	})
public class OrderTimmingConfig {

}
