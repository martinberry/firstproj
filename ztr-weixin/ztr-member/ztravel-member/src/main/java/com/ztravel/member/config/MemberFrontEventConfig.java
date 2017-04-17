package com.ztravel.member.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


/**
 * @author haofan.wan
 *
 */


@Configuration
@ComponentScan({
	"com.ztravel.member.front.event"
	})
@ImportResource({
    "classpath:spring/ztravel-front-event.xml"
})
public class MemberFrontEventConfig {

}
