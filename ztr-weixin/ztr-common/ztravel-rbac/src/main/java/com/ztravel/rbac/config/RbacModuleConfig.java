package com.ztravel.rbac.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
	RbacBeanConfig.class
})
public class RbacModuleConfig {

}
