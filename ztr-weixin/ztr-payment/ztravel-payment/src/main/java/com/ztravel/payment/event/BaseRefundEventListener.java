package com.ztravel.payment.event;

import java.util.Set;

import javax.annotation.Resource;

import com.google.common.collect.ImmutableSet;
import com.google.common.eventbus.EventBus;
import com.ztravel.common.event.AbstractEventListener;

/**
 * @author zuoning.shen
 *
 */
public abstract class BaseRefundEventListener extends AbstractEventListener{

	@Resource(name="refundEventBus")
    private EventBus refundEventBus;

    @Override
    protected Set<EventBus> getEventBusToRegister() {
        return ImmutableSet.of(refundEventBus);
    }
}
