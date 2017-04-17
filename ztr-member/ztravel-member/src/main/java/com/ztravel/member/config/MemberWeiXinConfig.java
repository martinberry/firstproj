package com.ztravel.member.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import com.ztravel.payment.config.PaymentClientConfig;
import com.ztravel.sraech.client.config.SearchClientConfig;

/***
 微信配置
 * @author zhaopengfei
 **/
@Configuration
@Import({
	SearchClientConfig.class,
	PaymentClientConfig.class
})
@ComponentScan({
	"com.ztravel.member.client",
	"com.ztravel.member.dao",
	"com.ztravel.member.weixin.controller",
	"com.ztravel.member.weixin.service",
	"com.ztravel.reuse.member.service"
	})
@EnableAspectJAutoProxy
@ImportResource({
    "classpath:spring/ztravel-front-event.xml"
})
public class MemberWeiXinConfig {

}
