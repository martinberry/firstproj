package com.ztravel.media.compress.listener;


import javax.annotation.Resource;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

import com.ztravel.mq.handler.IMqHandler;


@Component(value="mediaCompressListener")
public class MediaCompressListener implements MessageListener{
	
	@Resource
	IMqHandler mqHandler;
	
	
	@Override
	public void onMessage(Message message) {
		mqHandler.handle(new String(message.getBody()));
	}

}
