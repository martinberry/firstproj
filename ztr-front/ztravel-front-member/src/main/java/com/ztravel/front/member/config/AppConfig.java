package com.ztravel.front.member.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ztravel.common.bean.MultiFileResloverBean;
import com.ztravel.common.config.CommonConfig;
import com.ztravel.common.resource.config.FrontResourceMvcConfig;
import com.ztravel.common.sensitive.SensitiveWordsConfig;
import com.ztravel.datasource.config.DataSourceConfig;
import com.ztravel.member.config.MemberClientConfig;
import com.ztravel.member.config.MemberFrontEventConfig;
import com.ztravel.member.config.MemberFrontModuleConfig;
import com.ztravel.operator.client.config.OperatorClientConfig;
import com.ztravel.order.config.OrderClientConfig;
import com.ztravel.order.config.OrderFrontModuleConfig;
import com.ztravel.order.config.OrderReuseConfig;
import com.ztravel.payment.config.PaymentClientConfig;
import com.ztravel.product.client.config.ProductClientConfig;
import com.ztravel.product.config.ProductFrontModuleConfig;
import com.ztravel.sso.config.SSOClientConfig;
import com.ztravel.sso.config.SSOFilterConfig;

@Configuration
@Import({
	CommonConfig.class,
	FrontResourceMvcConfig.class,
	MybatisGlobalConfig.class,
	MemberFrontModuleConfig.class,
	DataSourceConfig.class,
	MultiFileResloverBean.class,
	ProductFrontModuleConfig.class,
	OrderClientConfig.class,
	MemberClientConfig.class,
	ProductClientConfig.class,
	OrderFrontModuleConfig.class,
	OrderReuseConfig.class,
	SSOClientConfig.class,
	PaymentClientConfig.class,
	MemberFrontEventConfig.class,
	SSOFilterConfig.class,
	OperatorClientConfig.class,
	SensitiveWordsConfig.class
})
public class AppConfig {

}
