package com.ztravel.weixin.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.ztravel.weixin.back.controller",
	                              "com.ztravel.weixin.back.service",
	                              "com.ztravel.weixin.dao",
	                              "com.ztravel.weixin.activity.controller",
                                  "com.ztravel.weixin.activity.service",
                                  "com.ztravel.weixin.activity.dao",
                                  "com.ztravel.weixin.user.service",
                                  "com.ztravel.weixin.user.dao"
	                           })
public class WeixinBackModuleConfig {

}
