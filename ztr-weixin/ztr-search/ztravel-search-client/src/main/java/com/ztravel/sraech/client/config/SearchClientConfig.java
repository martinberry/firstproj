package com.ztravel.sraech.client.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import com.travelzen.swordfish.thrift.client.config.ThriftClientProxyConfig;


@Configuration
@ImportResource({
	"classpath:spring/ztr-search-client.xml"
})
@Import({
	ThriftClientProxyConfig.class,
	SearchThriftClientResourcesConfig.class
})
public class SearchClientConfig {

}
