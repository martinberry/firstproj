package com.ztravel.mq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import com.alibaba.fastjson.JSON;
import com.ztravel.mq.bean.MqBeanFactory;


/**
 * mq 生产者
 * @author liuzhuo
 *
 */
public class MqProducer {

	private static final Logger logger = LoggerFactory.getLogger(MqProducer.class);
	
	public static void sendMonitorEntity(Object messageBody,String routingKey) {
		
		try{
			MessageProperties messageProperties = new MessageProperties();
			messageProperties.setHeader("head", System.currentTimeMillis());
			byte[] bytes = JSON.toJSONString(messageBody).getBytes();
			Message message = new Message(bytes, messageProperties);
			MqBeanFactory.amqpTemplate.convertAndSend(routingKey, message);		
		}catch(Exception e) {
			logger.error("发送消息到mq server失败", e);
		}

	}
	
}
