package com.ztravel.mq.bean;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 提供mq操作静态实例
 * @author liuzhuo
 *
 */
public class MqBeanFactory {
	
	public static AmqpTemplate amqpTemplate;
	
	static {
		@SuppressWarnings("resource")
		ApplicationContext contex=new ClassPathXmlApplicationContext("classpath:spring/ztravel-mq.xml");
		amqpTemplate = contex.getBean(AmqpTemplate.class);
	}

}
