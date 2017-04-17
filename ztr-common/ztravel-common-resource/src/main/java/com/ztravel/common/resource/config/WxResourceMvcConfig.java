package com.ztravel.common.resource.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


@Configuration
@ImportResource({"classpath:spring/ztr-wx-resource-mvc.xml","classpath:spring/wx-exception-handler-bean.xml"})
public class WxResourceMvcConfig {

}
