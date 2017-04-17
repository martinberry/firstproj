package com.ztravel.timming.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ztravel.common.config.CommonConfig;
import com.ztravel.datasource.config.DataSourceConfig;
import com.ztravel.member.config.MemberTimmingConfig;
import com.ztravel.order.config.OrderReuseConfig;
import com.ztravel.order.config.OrderTimmingConfig;
import com.ztravel.payment.config.PaymentTimmingConfig;
import com.ztravel.product.config.ProductTimmingConfig;
import com.ztravel.weixin.config.WeixinTimmingConfig;


@Configuration
@Import({
	DataSourceConfig.class,
	MybatisGlobalConfig.class,
	CommonConfig.class,
	SpringTimmingGlobalConfig.class,
	ProductTimmingConfig.class,
	OrderTimmingConfig.class,
	OrderReuseConfig.class,
	MemberTimmingConfig.class,
	PaymentTimmingConfig.class,
	WeixinTimmingConfig.class
})
public class AppConfig {

}
