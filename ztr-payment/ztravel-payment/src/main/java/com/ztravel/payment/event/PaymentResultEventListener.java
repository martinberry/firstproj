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
public class PaymentResultEventListener extends BasePaymentEventListener {
	private final static Logger logger = LoggerFactory.getLogger(PaymentResultEventListener.class);

	@Resource(name="orderThriftClientServiceImpl")
	private IOrderThriftClientService orderThriftClientServiceImpl;
	@Resource
	private OrderPayDao orderPayDao;
	@Resource
	private TradeService tradeService;

	@Subscribe
	public void notifyOrder(PaymentResultEvent event) {
		String orderId = event.getOrderId();
		logger.info("notifyOrder start, orderId: {}", orderId);
		OrderPaidBean bean = new OrderPaidBean();
		bean.setOrderId(orderId);
		bean.setPaymentType(event.getPaymentType());
		bean.setTraceNum(event.getThirdpartOrderId());
		bean.setBankPaymentTime(event.getThirdpartOrderDate());
		OrderPay orderPay = orderPayDao.selectByOrderId(orderId);
		if (orderPay != null && (ProductType.VOUCHER).equals(orderPay.getProductType())) {
		    CommonResponse res = orderThriftClientServiceImpl.notifyVoucherOrder4Paid(bean);
	        logger.info("notifyOrder end, response: {}", TZBeanUtils.describe(res));
		} else {
		    CommonResponse res = orderThriftClientServiceImpl.updateOrderStatus(bean);
	        logger.info("notifyOrder end, response: {}", TZBeanUtils.describe(res));
		}
	}

	@Subscribe
    public void recordTradeResult(PaymentResultEvent event) {
        logger.info("recordTradeResult start: {}", TZBeanUtils.describe(event));
        String orderId = event.getOrderId();
        OrderPay orderPay = orderPayDao.selectByOrderId(orderId);
        String memberId = orderPay.getMemberId();
        long orderAmount = orderPay.getOrderAmount();
        long payAmount = orderPay.getPayAmount();
        long useCoupon = orderPay.getUseCoupon();
        String couponItemId = orderPay.getCouponItemId();
        String comment = orderPay.getComment();

        try {
            TradeBean thirdTradeBean = new TradeBean();
            thirdTradeBean.setMemberId(memberId);
            thirdTradeBean.setOrderId(orderId);
            thirdTradeBean.setOrderType(OrderType.PAY_ORDER);
            thirdTradeBean.setTradeType(TradeType.PAYMENT);
            thirdTradeBean.setProductType(StringUtils.isBlank(orderPay.getProductType()) ? null : ProductType.valueOf(orderPay.getProductType()));
            thirdTradeBean.setOrderAmount(orderAmount);
            thirdTradeBean.setTradeAmount(payAmount);
            thirdTradeBean.setPaymentType(event.getPaymentType());
            thirdTradeBean.setComment(comment);
            thirdTradeBean.setTradeDate(DateTime.now());
            thirdTradeBean.setTraceNum(event.getThirdpartOrderId());
            thirdTradeBean.setBankPaymentTime(event.getThirdpartOrderDate());
            thirdTradeBean.setTradeStatus(TradeStatus.SUCCESS);
            tradeService.recordTradeResult(thirdTradeBean);

            if (useCoupon > 0 && StringUtils.isNotBlank(couponItemId)) {
                TradeBean couponTradeBean = new TradeBean();
                couponTradeBean.setMemberId(memberId);
                couponTradeBean.setOrderId(orderId);
                couponTradeBean.setOrderType(OrderType.PAY_ORDER);
                couponTradeBean.setTradeType(TradeType.PAYMENT);
                couponTradeBean.setProductType(StringUtils.isBlank(orderPay.getProductType()) ? null : ProductType.valueOf(orderPay.getProductType()));
                couponTradeBean.setOrderAmount(orderAmount);
                couponTradeBean.setTradeAmount(useCoupon);
                couponTradeBean.setPaymentType(PaymentType.Coupon);
                couponTradeBean.setComment(comment);
                couponTradeBean.setTradeDate(DateTime.now());
                couponTradeBean.setTradeStatus(TradeStatus.SUCCESS);
                couponTradeBean.setCouponItemId(couponItemId);
                tradeService.recordTradeResult(couponTradeBean);
            }
        } catch (Exception e) {
            logger.error("recordTradeResult failed!", e);
        }
        logger.info("recordTradeResult end: {}", TZBeanUtils.describe(event));
    }
}
