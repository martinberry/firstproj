package com.ztravel.member.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.ztravel.payment.config.PaymentClientConfig;
import com.ztravel.sraech.client.config.SearchClientConfig;


/**
 * 用户前台模块配置，需要和后端分开，在必要的时候做项目拆分
 * @author liuzhuo
 *
 */


@Configuration
@Import({
	SearchClientConfig.class,
	PaymentClientConfig.class
})
@ComponentScan({
	"com.ztravel.member.front",
	"com.ztravel.member.dao"
	})
@EnableAspectJAutoProxy
public class MemberFrontModuleConfig {

}
