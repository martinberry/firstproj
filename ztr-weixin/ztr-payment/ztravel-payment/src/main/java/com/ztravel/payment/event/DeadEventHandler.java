package com.ztravel.payment.event;

import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableSet;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.ztravel.common.event.AbstractEventListener;

/**
 * @author zuoning.shen
 *
 */
@Component
public class DeadEventHandler extends AbstractEventListener{
    private final static Logger logger = LoggerFactory.getLogger(DeadEventHandler.class);

    @Resource(name="paymentEventBus")
    private EventBus paymentEventBus;
    @Resource(name="refundEventBus")
    private EventBus refundEventBus;
    @Resource(name="syncEventBus")
    private EventBus syncEventBus;

    @Subscribe
    public void handlerDeadEvent(DeadEvent event){
        logger.error("dead event from {}, event: {}",event.getSource(), event.getEvent());
    }

    @Override
    protected Set<EventBus> getEventBusToRegister() {
        return ImmutableSet.of(paymentEventBus, refundEventBus, syncEventBus);
    }
}
