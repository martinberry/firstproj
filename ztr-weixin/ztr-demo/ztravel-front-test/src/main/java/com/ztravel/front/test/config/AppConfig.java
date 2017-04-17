package com.ztravel.front.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ztravel.demo.member.config.MemberModuleConfig;
import com.ztravel.demo.resource.config.ResouceMvcConfig;

/**
 * demo 项目启动入口配置
 * @author piaomeihua
 *
 */
@Configuration
@Import({
	ResouceMvcConfig.class,
	MemberModuleConfig.class,
	FrontModuleConfig.class
})
public class AppConfig {

}
