package com.ztravel.datasource.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


@Configuration
@ImportResource({
	"classpath:spring/ztravel-datasource.xml",
	"classpath:spring/ztravel-datastore.xml",
	"classpath:spring/ztravel-transaction.xml"
})
public class DataSourceConfig {

}
