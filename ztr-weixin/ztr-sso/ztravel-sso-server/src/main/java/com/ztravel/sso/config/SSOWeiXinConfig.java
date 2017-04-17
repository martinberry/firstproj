package com.ztravel.sso.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({
	"com.ztravel.sso.client.service",
	"com.ztravel.sso.dao"
})
public class SSOWeiXinConfig {

}
