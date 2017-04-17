package com.ztravel.sso.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ztravel.common.bean.MultiFileResloverBean;
import com.ztravel.common.config.CommonConfig;
import com.ztravel.common.resource.config.FrontResourceMvcConfig;
import com.ztravel.datasource.config.DataSourceConfig;
import com.ztravel.member.config.MemberClientConfig;
import com.ztravel.member.config.MemberFrontEventConfig;
import com.ztravel.operator.client.config.OperatorClientConfig;
import com.ztravel.payment.config.PaymentClientConfig;
import com.ztravel.product.client.config.ProductClientConfig;


@Configuration
@Import({
	CommonConfig.class,
	FrontResourceMvcConfig.class,
	DataSourceConfig.class,
	MybatisGlobalConfig.class,
	SSOConfig.class,
	MemberClientConfig.class,
	MultiFileResloverBean.class,
	MemberFrontEventConfig.class,
	ProductClientConfig.class,
	PaymentClientConfig.class,
	SSOFilterConfig.class,
	OperatorClientConfig.class
})
public class AppConfig {

}
