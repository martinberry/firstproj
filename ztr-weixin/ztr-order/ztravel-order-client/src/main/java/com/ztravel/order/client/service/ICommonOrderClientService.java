package com.ztravel.order.client.service;

import com.ztravel.common.enums.PaymentType;
import com.ztravel.order.entity.OrderCancelNeedToDoEntity;
import com.ztravel.order.po.CommonOrder;


/**
 * 
 * @author wanhaofan
 *
 */
public interface ICommonOrderClientService {
	
	CommonOrder selectByOriginOrderNo(String originOrderNo) throws Exception;

	void updateCommonOrder4Paid(String commonOrderId,
			PaymentType paymentType, String paySerialNum) throws Exception;

	void updateCommonOrder4Refunded(String commonOrderId, String paySerialNum)
			throws Exception;

	OrderCancelNeedToDoEntity orderCancelNeedToDo(String originOrderNo)
			throws Exception;

	int getUnpayConfirmOrderNum(String mid) throws Exception;

	
}
