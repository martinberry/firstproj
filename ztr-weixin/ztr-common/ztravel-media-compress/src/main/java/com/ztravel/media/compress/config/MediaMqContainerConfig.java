package com.ztravel.media.compress.config;

import javax.annotation.Resource;

import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.amqp.core.MessageListener;



@Component
public class MediaMqContainerConfig {
	
	@Resource
	org.springframework.amqp.rabbit.connection.ConnectionFactory ztrConnectionFactory;
	
	@Resource(name="mediaCompressListener")
	MessageListener messageListener;
	
	@Bean
    public SimpleMessageListenerContainer messageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(ztrConnectionFactory);
        container.setQueueNames("ztr_media_queue");
        container.setMessageListener(messageListener);
        return container;
    }
	

}
