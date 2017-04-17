package com.ztravel.front.member.test;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import com.ztravel.front.member.config.MybatisGlobalConfig;


@Configuration
@ComponentScan({
	"com.ztravel.member.client",
	"com.ztravel.member.dao",
	"com.ztravel.member.front.service"
})
@Import({
	MybatisGlobalConfig.class
})
@ImportResource({
	"classpath:spring/ztravel-datasource.xml",
	"classpath:spring/ztravel-datastore.xml"
})
public class TestConfig {

}
