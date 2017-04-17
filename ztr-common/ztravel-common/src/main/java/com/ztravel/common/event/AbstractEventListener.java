package com.ztravel.common.event;

import javax.annotation.PostConstruct;

import com.google.common.eventbus.EventBus;

import java.util.Set;

/**
 * @author zuoning.shen
 *
 */
public abstract class AbstractEventListener {
	@PostConstruct
	public void init(){
		for(EventBus eventBus: getEventBusToRegister()){
			eventBus.register(this);
		}
	}

	protected abstract Set<EventBus> getEventBusToRegister();
}
