package com.ztravel.search.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.ztravel.search.product", "com.ztravel.search.autoComplete"})
public class SearchModuleConfig {

}
