package com.ztravel.payment.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import com.travelzen.swordfish.thrift.client.config.ThriftClientProxyConfig;

/**
 * @author zuoning.shen
 *
 */

@Configuration
@ImportResource({
	"classpath:spring/ztr-payment-client.xml"
})
@Import({
	ThriftClientProxyConfig.class,
	ThriftClientResourcesConfig.class
})
public class PaymentClientConfig {

}
