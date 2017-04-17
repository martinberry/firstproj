package com.ztravel.weixin.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({
                                "com.ztravel.weixin.client.service"
                               })
public class WeixinClientConfig {

}
