/**
 *
 */
package com.ztravel.payment.service;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.eventbus.EventBus;
import com.travelzen.framework.util.TZBeanUtils;
import com.travelzen.swordfish.thrift.client.annotation.ThriftServiceEndpoint;
import com.ztravel.common.enums.GateType;
import com.ztravel.common.payment.OrderPayBean;
import com.ztravel.common.payment.OrderPayInfoBean;
import com.ztravel.common.payment.PaymentResponse;
import com.ztravel.common.payment.WxPaymentResponse;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.paygate.wx.client.entity.WxPayResponse;
import com.ztravel.payment.event.PaymentResultEvent;
import com.ztravel.payment.paygate.model.CommonOrderRefundRequest;
import com.ztravel.payment.paygate.model.PayResultBean;
import com.ztravel.payment.paygate.model.VoucherOrderRefundRequest;
import com.ztravel.payment.processor.OrderPaymentProcessor;

/**
 * @author zuoning.shen
 *
 */
@Service("OrderPaymentService")
@ThriftServiceEndpoint
public class OrderPaymentService implements IOrderPaymentService {
    private static Logger logger = LoggerFactory.getLogger(OrderPaymentService.class);
    @Resource
    private OrderPaymentProcessor paymentProcessor;
    @Resource
    protected ThirdPaymentService thirdPaymentService;
    @Resource(name = "paymentEventBus")
    private EventBus paymentEventBus;

    @Override
    public String reqChecksum(OrderPayBean order) {
        logger.info("reqChecksum start, order: {}", TZBeanUtils.describe(order));
        String checksum = paymentProcessor.reqPayChecksum(order);
        logger.info("reqChecksum end, checksum: {}", checksum);
        return checksum;
    }
    @Override
    public boolean verifyChecksum(OrderPayBean order, String checksum) {
        logger.info("verifyChecksum start, order: {}, checksum: {}", TZBeanUtils.describe(order), checksum);
        boolean result = paymentProcessor.verifyPayChecksum(order, checksum);
        logger.info("verifyChecksum end, result: {}", result);
        return result;
    }
    @Override
    public PaymentResponse placeOrder(OrderPayBean order) {
        logger.info("placeOrder start, order: {}", TZBeanUtils.describe(order));
        PaymentResponse res = null;
        try {
            res = paymentProcessor.placeOrder(order);
            if (!order.needPay()) {
                PayResultBean payResult = new PayResultBean();
                payResult.setOrderNum(order.getOrderId());
                payResult.setAmount(order.getPayAmount());
                payResult.setGateType(GateType.fromPayType(order.getPaymentType()).getGateCode());
                boolean needNotify = thirdPaymentService.processPaymentResult(false, payResult);
                if (needNotify) {
                    String orderId = payResult.getOrderNum();
                    logger.info("Notify payment result event, orderId: {}", orderId);
                    paymentEventBus.post(new PaymentResultEvent(order.getOrderId(), null, null, null));
                }
            }
        } catch (Exception e) {
            logger.error("Place order failed, orderId: {}", order.getOrderId(), e);
            res = new PaymentResponse();
            res.setSuccess(false);
            res.setErrMsg(e.getMessage());
        }

        logger.info("placeOrder end, response: {}", TZBeanUtils.describe(res));
        return res;
    }

    @Override
    public WxPaymentResponse placeWxOrder(OrderPayBean order) {
        logger.info("placeWxOrder start, order: {}", TZBeanUtils.describe(order));
        WxPaymentResponse res = new WxPaymentResponse() ;
        try {
        	WxPayResponse response = paymentProcessor.placeWxOrder(order);
        	res.setValue(response.getResult_msg()) ;
        	res.setSuccess(true);
        } catch (Exception e) {
            logger.error("Place wx order failed, orderId: {}", order.getOrderId(), e);
            res = new WxPaymentResponse();
            res.setSuccess(false);
            res.setErrMsg(e.getMessage());
        }

        logger.info("placeWxOrder end, response: {}", TZBeanUtils.describe(res));
        return res;
    }

    @Override
    public void closeWxOrder(String orderId) {
        logger.info("close wx order start, order: {}", orderId);
        boolean ret = false ;
		try {
			ret = paymentProcessor.closeWxOrder(orderId);
		} catch (Exception e) {
			logger.error("close wx order failed, orderId: {}", orderId, e);
		}
        logger.info("close wx order:{} end, response: {}", orderId, ret);
    }

    @Override
    public OrderPayInfoBean getOrderPayInfo(String orderId) {
        logger.info("getOrderPayInfo start, order: {}", orderId);
        OrderPayInfoBean orderPayInfoBean = paymentProcessor.getOrderPayInfo(orderId);
        logger.info("getOrderPayInfo end, order: {}", TZBeanUtils.describe(orderPayInfoBean));
        return orderPayInfoBean;
    }
    @Override
    public CommonResponse cancelOrder(String orderId, String operator,String paymentType) {
        logger.info("cancelOrder start, orderId: {}, operator: {}", orderId, operator);
        CommonResponse res = null;
        try {
            res = paymentProcessor.cancelOrder(orderId, operator, paymentType);
        } catch (Exception e) {
            logger.error("cancelOrder failed, orderId: {}", orderId, e);
            res = new CommonResponse();
            res.setSuccess(false);
            res.setErrMsg(e.getMessage());
        }
        logger.info("cancelOrder end, response: {}", TZBeanUtils.describe(res));
        return res;
    }


    @Override
    public CommonResponse cancelOrder(VoucherOrderRefundRequest request) {
        logger.info("cancelVoucherOrder start, orderId: {}, operator: {}", request.getCombineVoucherOrderId() + ":::" + request.getVoucherOrderId(), request.getOperator());
        CommonResponse res = null;
        try {
            res = paymentProcessor.cancelOrder(request);
        } catch (Exception e) {
            logger.error("cancelVoucherOrder failed, orderId: {}", request.getCombineVoucherOrderId() + ":::" + request.getVoucherOrderId() , e);
            res = new CommonResponse();
            res.setSuccess(false);
            res.setErrMsg(e.getMessage());
        }
        logger.info("cancelVoucherOrder end, response: {}", TZBeanUtils.describe(res));
        return res;
    }

    @Override
    public CommonResponse refundOPConfirmOrder(CommonOrderRefundRequest request) {
        logger.info("refundOPConfirmOrder start, params:::{}", TZBeanUtils.describe(request));
        CommonResponse res = null;
        try {
            res = paymentProcessor.refundOPConfirmOrder(request);
        } catch (Exception e) {
            logger.error("refundOPConfirmOrder failed, orderId: {}", request.getCommonOrderId() , e);
            res = new CommonResponse();
            res.setSuccess(false);
            res.setErrMsg(e.getMessage());
        }
        logger.info("refundOPConfirmOrder end, response: {}", TZBeanUtils.describe(res));
        return res;
    }

	@Override
	public String queryWxOrderTradeState(String orderId) {
	      logger.info("query wx order trade_state start, order: {}", orderId);
	        String tradeState = ""  ;
			try {
				tradeState = paymentProcessor.queryWxOrderTradeState(orderId);
			} catch (Exception e) {
				logger.error("query wx order trade_state failed, orderId: {}", orderId, e);
			}
	        logger.info("query wx order :{} trade_state end, response: {}", orderId, tradeState);

	        return tradeState;

	}
	@Override
	public Map<String, Long> queryPaidAmount(String paySerialNum) {
        logger.info("query paidAmount start, paySerialNum:::{}", paySerialNum);
        Map<String,Long> map = null;
        try {
        	map = paymentProcessor.queryPaidAmount(paySerialNum);
        } catch (Exception e) {
            logger.error("refundOPConfirmOrder failed, paySerialNum: {}", paySerialNum , e);
        }
        logger.info("query paidAmount end,response: {}", TZBeanUtils.describe(map.toString()));
        return map;
	}

}
