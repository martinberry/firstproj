package com.ztravel.mq.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * mq消费者抽象类  不实现具体方法
 * @author liuzhuo
 *
 */
public abstract class MqConsumer implements MessageListener{

	@Override
	public void onMessage(Message message) {
		
	}

}
