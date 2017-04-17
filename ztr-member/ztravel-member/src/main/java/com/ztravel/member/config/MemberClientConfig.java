package com.ztravel.member.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * 用户client部分配置，注意dao为公共的，可以重复扫描
 * @author liuzhuo
 *
 */

@Configuration
@ComponentScan({
	"com.ztravel.member.client",
	"com.ztravel.member.dao",
	"com.ztravel.reuse.member.service"
})
public class MemberClientConfig {

}
