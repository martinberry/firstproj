package com.ztravel.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ztravel.common.config.CommonConfig;
import com.ztravel.common.resource.config.BackResourceMvcConfig;
import com.ztravel.datasource.config.DataSourceConfig;

@Configuration
@Import({
	TestBeanConfig.class,
	CommonConfig.class,
	BackResourceMvcConfig.class,
	DataSourceConfig.class,
	MybatisGlobalConfig.class
})
public class AppConfig {

}
