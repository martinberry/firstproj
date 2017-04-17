package com.ztravel.paygate.wx.notifyserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ztravel.common.config.CommonConfig;
import com.ztravel.datasource.config.DataSourceConfig;
import com.ztravel.order.client.config.OrderThriftClientConfig;
import com.ztravel.paygate.wx.config.PaygateWxConfig;
import com.ztravel.payment.config.PaymentClientConfig;

@Configuration
@Import({
	DataSourceConfig.class,
	CommonConfig.class,
	MybatisGlobalConfig.class,
	PaygateWxConfig.class,
	PaymentClientConfig.class,
	OrderThriftClientConfig.class
})
public class AppConfig {

}
