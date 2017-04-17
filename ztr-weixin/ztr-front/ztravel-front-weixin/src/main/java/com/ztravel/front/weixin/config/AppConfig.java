package com.ztravel.front.weixin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ztravel.common.bean.MultiFileResloverBean;
import com.ztravel.common.config.CommonConfig;
import com.ztravel.common.resource.config.WxResourceMvcConfig;
import com.ztravel.common.sensitive.SensitiveWordsConfig;
import com.ztravel.datasource.config.DataSourceConfig;
import com.ztravel.member.config.MemberFrontEventConfig;
import com.ztravel.member.config.MemberWeiXinConfig;
import com.ztravel.operator.client.config.OperatorClientConfig;
import com.ztravel.order.config.OrderReuseConfig;
import com.ztravel.order.config.OrderWeiXinConfig;
import com.ztravel.payment.config.PaymentClientConfig;
import com.ztravel.product.config.ProductWeiXinConfig;
import com.ztravel.sso.config.SSOClientConfig;
import com.ztravel.sso.config.SSOFilterConfig;
import com.ztravel.sso.config.SSOWeiXinConfig;
import com.ztravel.weixin.config.WeixinClientConfig;
import com.ztravel.weixin.config.WeixinConfig;

@Configuration
@Import({
	CommonConfig.class,
	WxResourceMvcConfig.class,
	MybatisGlobalConfig.class,
	MemberWeiXinConfig.class,
	DataSourceConfig.class,
	MultiFileResloverBean.class,
	ProductWeiXinConfig.class,
	OrderWeiXinConfig.class,
	SSOWeiXinConfig.class,
    SSOClientConfig.class,
	PaymentClientConfig.class,
	MemberFrontEventConfig.class,
	SSOFilterConfig.class,
	WeixinConfig.class,
	WeixinClientConfig.class,
	SensitiveWordsConfig.class,
	OrderReuseConfig.class,
	OperatorClientConfig.class
})
public class AppConfig {

}
