package com.ztravel.order.client.service;

import java.util.List;

import com.ztravel.common.order.OrderPaidBean;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.order.entity.OrderCancelNeedToDoEntity;
import com.ztravel.order.po.VoucherOrder;

public interface IOrderThriftClientService {

	CommonResponse updateOrderStatus(OrderPaidBean orderPayedBean);

	CommonResponse updateOrderToRefunding(String orderCode);

	CommonResponse updateOrderToCancled(String orderCode);

	CommonResponse updateOrderToRefundFailed(String orderCode);

	CommonResponse weatherOrderCancled(String orderCode);

	CommonResponse weatherOrderPayedByOrderCode(String orderCode);

	void sendOperatorMessage(String orderCode,Boolean isSuccess);

	CommonResponse notifyVoucherOrder4Paid(OrderPaidBean orderPaidBean) ;

	CommonResponse updateCombineVoucherOrderToRefunding(String voucherOrderId,
			String combineVoucherOrderId);

	CommonResponse isVoucherOrderRefunded(String voucherOrderId);

	List<VoucherOrder> selectVoucherOrderbyCBID(String combineOrderId);

	long selectCouponAmountbyId(String voucherOrderId);

	CommonResponse notifyVoucherOrder4Refunded(String orderId, long refundAmount);

	void sendPayVoucherOrderSuccessMessage(String combineOrderId);

	CommonResponse updateCommonOrder4Paid(OrderPaidBean orderPayedBean);

	CommonResponse isOPConfirmOrderRefunded(String orderId);

	CommonResponse updateOpConfirmOrderToRefunded(String opConfirmOrderId,
			String paySerialNum);

	OrderCancelNeedToDoEntity needToDoBeforeOrderCancel(String orderNo) throws Exception;

	String getOrderFromCommonOrder(String commonOrderId);

}
