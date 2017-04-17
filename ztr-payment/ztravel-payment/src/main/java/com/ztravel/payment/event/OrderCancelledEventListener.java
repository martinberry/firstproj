/**
 *
 */
package com.ztravel.payment.event;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.Subscribe;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.enums.OrderType;
import com.ztravel.common.enums.PaymentType;
import com.ztravel.common.enums.ProductType;
import com.ztravel.common.enums.TradeStatus;
import com.ztravel.common.enums.TradeType;
import com.ztravel.common.order.OrderPaidBean;
import com.ztravel.common.payment.TradeBean;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.order.client.service.IOrderThriftClientService;
import com.ztravel.payment.dao.OrderPayDao;
import com.ztravel.payment.po.OrderPay;
import com.ztravel.payment.service.TradeService;

/**
 * @author zuoning.shen
 *
 */
@Component
public class OrderCancelledEventListener extends BasePaymentEventListener {
    private final static Logger logger = LoggerFactory.getLogger(OrderCancelledEventListener.class);

    @Resource
    private OrderPayDao orderPayDao;
    @Resource
    private TradeService tradeService;

    @Resource(name="orderThriftClientServiceImpl")
	private IOrderThriftClientService orderThriftClientServiceImpl;

	@Subscribe
	public void notifyOrder(OrderCancelledEvent event) {
		String orderId = event.getOrderId();
		logger.info("notifyOrder start, orderId: {}", orderId);
		CommonResponse res = orderThriftClientServiceImpl.updateOrderToCancled(orderId);
		logger.info("notifyOrder end, response: {}", TZBeanUtils.describe(res));
	}


    @Subscribe
    public void recordTradeResult(OrderCancelledEvent event) {

        logger.info("recordTradeResult start: {}", TZBeanUtils.describe(event));
        String orderId = event.getOrderId();
        boolean refunded = event.isRefunded();
        OrderPay orderPay = orderPayDao.selectByOrderId(orderId);
        String memberId = orderPay.getMemberId();
        long orderAmount = orderPay.getOrderAmount();
        long useCoupon = orderPay.getUseCoupon();
        String couponItemId = orderPay.getCouponItemId();
        String comment = orderPay.getComment();

        if(refunded){
            try {
            	   TradeBean thirdTradeBean = new TradeBean();
                   thirdTradeBean.setMemberId(memberId);
                   thirdTradeBean.setOrderId(orderId);
                   thirdTradeBean.setOrderType(OrderType.CANCEL_ORDER);
                   thirdTradeBean.setTradeType(TradeType.REFUND);
                   thirdTradeBean.setProductType(StringUtils.isBlank(orderPay.getProductType()) ? null : ProductType.valueOf(orderPay.getProductType()));
                   thirdTradeBean.setOrderAmount(orderAmount);
                   thirdTradeBean.setTradeAmount(orderPay.getPayAmount());
                   thirdTradeBean.setPaymentType(event.getPaymentType());
                   thirdTradeBean.setComment(comment);
                   thirdTradeBean.setTradeDate(DateTime.now());
                   thirdTradeBean.setTraceNum(event.getThirdpartOrderId());
                   thirdTradeBean.setBankPaymentTime(event.getThirdpartOrderDate());
                   thirdTradeBean.setTradeStatus(TradeStatus.SUCCESS);
                   tradeService.recordTradeResult(thirdTradeBean);
                if (useCoupon > 0 && StringUtils.isNotBlank(couponItemId)) {
                    TradeBean thirdCouponTradeBean = new TradeBean();
                    thirdCouponTradeBean.setMemberId(memberId);
                    thirdCouponTradeBean.setOrderId(orderId);
                    thirdCouponTradeBean.setOrderType(OrderType.CANCEL_ORDER);
                    thirdCouponTradeBean.setTradeType(TradeType.REFUND);
                    thirdCouponTradeBean.setProductType(StringUtils.isBlank(orderPay.getProductType()) ? null : ProductType.valueOf(orderPay.getProductType()));
                    thirdCouponTradeBean.setOrderAmount(orderAmount);
                    thirdCouponTradeBean.setTradeAmount(useCoupon);
                    thirdCouponTradeBean.setPaymentType(PaymentType.Coupon);
                    thirdCouponTradeBean.setComment(comment);
                    thirdCouponTradeBean.setTradeDate(DateTime.now());
                    thirdCouponTradeBean.setTradeStatus(TradeStatus.SUCCESS);
                    thirdCouponTradeBean.setCouponItemId(couponItemId);
                    tradeService.recordTradeResult(thirdCouponTradeBean);
                }
            } catch (Exception e) {
                logger.error("recordTradeResult failed!", e);
            }
        }

        logger.info("recordTradeResult end: {}", TZBeanUtils.describe(event));

    }
}
