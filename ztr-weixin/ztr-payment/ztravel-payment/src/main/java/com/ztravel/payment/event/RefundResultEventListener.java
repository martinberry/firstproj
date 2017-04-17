package com.ztravel.payment.event;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.Subscribe;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.order.OrderPaidBean;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.order.client.service.IOrderThriftClientService;
import com.ztravel.payment.dao.OrderPayDao;
import com.ztravel.payment.service.TradeService;

/**
 * @author zuoning.shen
 *
 */
@Component
public class RefundResultEventListener extends BaseRefundEventListener {
	private final static Logger logger = LoggerFactory.getLogger(RefundResultEventListener.class);

	@Resource(name="orderThriftClientServiceImpl")
	private IOrderThriftClientService orderThriftClientServiceImpl;
	@Resource
	private OrderPayDao orderPayDao;
	@Resource
	private TradeService tradeService;
	@Subscribe
	public void notifyOrder(RefundResultEvent event) {
		logger.info("notifyOrder start: {}", TZBeanUtils.describe(event));
		String orderId = event.getRefundOrderId();
		logger.info("notifyOrder end: {}", TZBeanUtils.describe(event));
	}

	@Subscribe
	public void traceTradeResult(RefundResultEvent event) {
		logger.info("traceTradeResult start: {}", TZBeanUtils.describe(event));
		logger.info("traceTradeResult end: {}", TZBeanUtils.describe(event));
	}

}
