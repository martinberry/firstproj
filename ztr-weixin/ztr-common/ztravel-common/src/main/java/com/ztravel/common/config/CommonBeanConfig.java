package com.ztravel.common.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.ztravel.common.retry",
	"com.ztravel.common.areasearch",
	"com.ztravel.common.rbac"
	})
public class CommonBeanConfig {

}
