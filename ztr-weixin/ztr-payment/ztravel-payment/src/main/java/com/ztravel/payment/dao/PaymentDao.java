/**
 *
 */
package com.ztravel.payment.dao;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.common.enums.PaymentType;
import com.ztravel.payment.po.Payment;

/**
 * @author zuoning.shen
 *
 */
public interface PaymentDao extends BaseDao<Payment> {

    Payment selectPayment(String orderId, PaymentType paymentType);
    
    Payment selectByTraceNum(String traceNum);

}
