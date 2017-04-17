package com.ztravel.front.member.test;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import com.ztravel.front.member.config.MybatisGlobalConfig;
import com.ztravel.payment.config.PaymentClientConfig;



@Configuration
@ComponentScan({
	"com.ztravel.order.dao",
	"com.ztravel.order.front.service",
	"com.ztravel.product.client",
	"com.ztravel.product.dao",
	"com.ztravel.product.front.dao",
	"com.ztravel.product.front.service",
	"com.ztravel.payment.client"
})
@Import({
	MybatisGlobalConfig.class,
	PaymentClientConfig.class
})
@ImportResource({
	"classpath:spring/ztravel-datasource.xml",
	"classpath:spring/ztravel-datastore.xml"
})
public class OrderTestConfig {

}
