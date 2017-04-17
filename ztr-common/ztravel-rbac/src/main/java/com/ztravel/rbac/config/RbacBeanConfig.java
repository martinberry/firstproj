package com.ztravel.rbac.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.ztravel.rbac.controller", "com.ztravel.rbac.service", "com.ztravel.rbac.dao"})
public class RbacBeanConfig {

}
