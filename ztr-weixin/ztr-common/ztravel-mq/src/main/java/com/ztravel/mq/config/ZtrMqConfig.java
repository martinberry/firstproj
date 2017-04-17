package com.ztravel.mq.config;

import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

/**
 * mq初步使用
 * 1，生产者和消费者有待抽象出来
 * 2，xml可剥离出来 使用config的方式实现，新增的listener用注册的方式去实例化
 * @author liuzhuo
 *
 */

@Component
@ImportResource("classpath:spring/ztravel-mq.xml")
public class ZtrMqConfig {
	
	

}
