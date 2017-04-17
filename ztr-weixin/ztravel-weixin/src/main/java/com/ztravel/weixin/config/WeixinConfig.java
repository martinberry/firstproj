package com.ztravel.weixin.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.config.TaskExecutorFactoryBean;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

/***
微信配置
* @author zhaopengfei
**/
@Configuration

@ComponentScan({
	"com.ztravel.weixin.controller",
    "com.ztravel.weixin.activity.controller",
    "com.ztravel.weixin.activity.controller",
    "com.ztravel.weixin.wechat.controller",
	"com.ztravel.weixin.activity.dao",
	"com.ztravel.weixin.activity.service",
    "com.ztravel.weixin.dao",
    "com.ztravel.weixin.front.service",
    "com.ztravel.weixin.user.dao",
    "com.ztravel.weixin.user.service",
    "com.ztravel.weixin.servlet",
    "com.ztravel.weixin.service",
    "com.ztravel.weixin.event"
	})

public class WeixinConfig {
	@Bean(name="wechatEventBus")
	public AsyncEventBus wechatEventBus() throws Exception{
		TaskExecutorFactoryBean taskExecutorFactoryBean = new TaskExecutorFactoryBean() ;
		taskExecutorFactoryBean.setPoolSize("0-100");
		taskExecutorFactoryBean.setBeanName("wechatEventExecutor");
		taskExecutorFactoryBean.afterPropertiesSet();
		Executor executor = taskExecutorFactoryBean.getObject() ;
		EventBus eventBus = new AsyncEventBus("wechatEventBus", executor) ;
		return (AsyncEventBus)eventBus ;
	}
}
