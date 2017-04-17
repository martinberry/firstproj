/**
 *
 */
package com.ztravel.payment.service;

import com.ztravel.payment.paygate.model.PayResultBean;
import com.ztravel.payment.paygate.model.RefundResultBean;

/**haofan.wan zuoning.shen
 *
 */
public interface IThirdPaymentThriftService {

	boolean notifyOrderPay(PayResultBean payResult) throws Exception ;
	
	boolean notifyOrderRefund(RefundResultBean refundResult) throws Exception ;
	
}
