package com.ztravel.payment.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.travelzen.swordfish.thrift.server.config.ThriftServerConfig;
import com.ztravel.common.config.CommonConfig;
import com.ztravel.datasource.config.DataSourceConfig;
import com.ztravel.payment.config.PaymentModuleConfig;
import com.ztravel.product.client.config.ProductClientConfig;

@Configuration
@Import({
	DataSourceConfig.class,
	CommonConfig.class,
	MybatisGlobalConfig.class,
	ThriftServerConfig.class,
	ThriftServerResourcesConfig.class,
	PaymentModuleConfig.class,
	ProductClientConfig.class
})
public class AppConfig {

}
