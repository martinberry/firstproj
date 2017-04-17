package com.ztravel.front.operator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ztravel.common.config.CommonConfig;
import com.ztravel.common.resource.config.BackResourceMvcConfig;
import com.ztravel.datasource.config.DataSourceConfig;
import com.ztravel.member.config.MemberClientConfig;
import com.ztravel.member.config.MemberOperaModuleConfig;
import com.ztravel.operator.client.config.OperatorClientConfig;
import com.ztravel.operator.config.OperatorModuleConfig;
import com.ztravel.order.config.OrderBackModuleConfig;
import com.ztravel.order.config.OrderClientConfig;
import com.ztravel.payment.config.PaymentClientConfig;
import com.ztravel.product.client.config.ProductClientConfig;
import com.ztravel.product.config.ProductBackModuleConfig;
import com.ztravel.rbac.config.RbacModuleConfig;
import com.ztravel.weixin.config.WeixinBackModuleConfig;
import com.ztravel.weixin.config.WeixinClientConfig;



@Configuration
@Import({
//	MorphiaLogger.class,
	DataSourceConfig.class,
	CommonConfig.class,
	BackResourceMvcConfig.class,
	OperatorModuleConfig.class,
	MybatisGlobalConfig.class,
	MemberOperaModuleConfig.class,
	ProductBackModuleConfig.class,
	OrderBackModuleConfig.class,
	OrderClientConfig.class,
	ProductClientConfig.class,
	MemberClientConfig.class,
	RbacModuleConfig.class,
	OperatorClientConfig.class,
	WeixinBackModuleConfig.class,
	PaymentClientConfig.class,
	WeixinClientConfig.class
})
public class AppConfig {

}
