package com.ztravel.order.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ztravel.payment.config.PaymentClientConfig;

@Configuration
@Import({
	PaymentClientConfig.class
})
@ComponentScan({
	"com.ztravel.order.front",
	"com.ztravel.order.dao",
	"com.ztravel.order.back.dao"
	})
public class OrderFrontModuleConfig {

}
