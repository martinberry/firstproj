package com.ztravel.common.bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


@Configuration
@ImportResource({
    "classpath:spring/ztravel-common-aop.xml"
})
public class DataSourceAopConfig {

}
