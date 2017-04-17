package com.ztravel.paygate.wx.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.travelzen.swordfish.thrift.server.config.ThriftServerConfig;
import com.ztravel.common.config.CommonConfig;
import com.ztravel.datasource.config.DataSourceConfig;
import com.ztravel.paygate.wx.config.PaygateWxThriftConfig;

@Configuration
@Import({
	DataSourceConfig.class,
	CommonConfig.class,
	MybatisGlobalConfig.class,
	ThriftServerConfig.class,
	ThriftServerResourcesConfig.class,
	PaygateWxThriftConfig.class
})
public class AppConfig {

}
