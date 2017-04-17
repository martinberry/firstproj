/**
 *
 */
package com.ztravel.payment.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.common.enums.PaymentType;
import com.ztravel.payment.dao.PaymentDao;
import com.ztravel.payment.po.Payment;

/**
 * @author zuoning.shen
 *
 */
@Repository
public class PaymentDaoImpl extends BaseDaoImpl<Payment> implements PaymentDao {

    @Override
    public Payment selectPayment(String orderId, PaymentType paymentType) {
        Map params = new HashMap();
        params.put("orderId", orderId);
        params.put("paymentType", paymentType.name());
        return session.selectOne(nameSpace + ".selectPayment", params);
    }

    @Override
    public Payment selectByTraceNum(String traceNum) {
        return session.selectOne(nameSpace + ".selectByTraceNum", traceNum);
    }

}
