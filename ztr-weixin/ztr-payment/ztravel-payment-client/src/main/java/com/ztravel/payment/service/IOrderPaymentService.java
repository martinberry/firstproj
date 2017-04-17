/**
 *
 */
package com.ztravel.payment.service;

import java.util.Map;

import com.ztravel.common.payment.OrderPayBean;
import com.ztravel.common.payment.OrderPayInfoBean;
import com.ztravel.common.payment.PaymentResponse;
import com.ztravel.common.payment.WxPaymentResponse;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.payment.paygate.model.CommonOrderRefundRequest;
import com.ztravel.payment.paygate.model.VoucherOrderRefundRequest;

/**
 * @author zuoning.shen
 *
 */
public interface IOrderPaymentService {
    String reqChecksum(OrderPayBean order);

    boolean verifyChecksum(OrderPayBean order, String checksum);

    OrderPayInfoBean getOrderPayInfo(String orderId);

    PaymentResponse placeOrder(OrderPayBean order);

    CommonResponse cancelOrder(String orderId, String operator, String paymentType);

	WxPaymentResponse placeWxOrder(OrderPayBean order);

	void closeWxOrder(String orderId);

	String queryWxOrderTradeState(String orderId);

	CommonResponse cancelOrder(VoucherOrderRefundRequest request);

	CommonResponse refundOPConfirmOrder(CommonOrderRefundRequest request);

	Map<String, Long> queryPaidAmount(String paySerialNum);

}
