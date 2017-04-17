package com.ztravel.order.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.travelzen.swordfish.thrift.server.config.ThriftServerConfig;
import com.ztravel.common.bean.SequenceGeneratorConfig;
import com.ztravel.datasource.config.DataSourceConfig;
import com.ztravel.member.config.MemberClientConfig;
import com.ztravel.operator.client.config.OperatorClientConfig;
import com.ztravel.order.config.OrderClientConfig;
import com.ztravel.order.config.OrderReuseConfig;
import com.ztravel.order.config.OrderThriftConfig;
import com.ztravel.payment.config.PaymentClientConfig;
import com.ztravel.product.client.config.ProductClientConfig;

@Configuration
@Import({
	DataSourceConfig.class,
	ThriftServerConfig.class,
	ThriftServerResourcesConfig.class,
	OrderThriftConfig.class,
	OrderClientConfig.class,
	OrderReuseConfig.class,
	SequenceGeneratorConfig.class,
	MybatisGlobalConfig.class,
	MemberClientConfig.class,
	ProductClientConfig.class,
	PaymentClientConfig.class,
	OperatorClientConfig.class
})
public class AppConfig {

}
