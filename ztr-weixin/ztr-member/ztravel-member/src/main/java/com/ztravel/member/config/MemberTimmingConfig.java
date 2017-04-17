package com.ztravel.member.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({
	"com.ztravel.member.client",
	"com.ztravel.reuse.member.service",
	"com.ztravel.member.dao"
})
public class MemberTimmingConfig {

}
