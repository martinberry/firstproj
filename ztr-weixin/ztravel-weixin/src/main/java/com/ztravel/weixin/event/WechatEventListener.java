package com.ztravel.weixin.event;

import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableSet;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.ztravel.common.event.AbstractEventListener;
import com.ztravel.weixin.service.IWeixinService;

/**
 * @author haofan.wan
 *
 */
@Component
public class WechatEventListener extends AbstractEventListener {
	private final static Logger logger = LoggerFactory.getLogger(WechatEventListener.class);

	@Resource(name="wechatEventBus")
    private EventBus wechatEventBus;

	@Resource
	private IWeixinService weixinService ;

	@Override
    protected Set<EventBus> getEventBusToRegister() {
        return ImmutableSet.of(wechatEventBus);
    }

	@Subscribe
	public void eventHandler(WechatEvent event) {
		try {
			weixinService.wxScan(event.getRequestMap());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
