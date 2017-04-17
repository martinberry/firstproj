package com.ztravel.search.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.travelzen.swordfish.thrift.server.config.ThriftServerConfig;
import com.ztravel.datasource.config.DataSourceConfig;
import com.ztravel.search.config.SearchModuleConfig;

@Configuration
@Import({
	DataSourceConfig.class,
	ThriftServerConfig.class,
	ThriftServerResourcesConfig.class,
	SearchModuleConfig.class
})
public class AppConfig {

}
