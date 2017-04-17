package com.ztravel.operator.client.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author wanhaofan
 *
 */
@Configuration
@ComponentScan({
	"com.ztravel.operator.client",
	"com.ztravel.operator.financeMantain.dao",
	"com.ztravel.operator.basicdata.dao"
})
public class OperatorClientConfig {

}
