package com.ztravel.sso.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({
	"com.ztravel.sso.filter"
})
public class SSOFilterConfig {

}
