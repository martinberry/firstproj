package com.ztravel.payment.event;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.Subscribe;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.payment.service.ThirdPaymentService;

/**
 * @author zuoning.shen
 *
 */
@Component
public class ThirdRefundRequestEventListener extends BaseRefundEventListener {
	private final static Logger logger = LoggerFactory.getLogger(ThirdRefundRequestEventListener.class);
	@Resource
	private ThirdPaymentService thirdPaymentService;
	@Subscribe
	public void processRefundRequest(ThirdRefundRequestEvent event) {
		logger.info("processRefundRequest start: {}", TZBeanUtils.describe(event));
		try {
			thirdPaymentService.processRefundRequest(event.getRefundOrderId(), event.getOrderGroupId(), event.getRefundAmount());
		} catch (Exception e) {
			logger.error("error processing return order: " + event.getRefundOrderId(), e);
		}
		logger.info("processRefundRequest start: {}", TZBeanUtils.describe(event));
	}
}
