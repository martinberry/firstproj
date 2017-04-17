package com.ztravel.sso.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan({
	"com.ztravel.sso"
})
@EnableAspectJAutoProxy
public class SSOConfig {

}
