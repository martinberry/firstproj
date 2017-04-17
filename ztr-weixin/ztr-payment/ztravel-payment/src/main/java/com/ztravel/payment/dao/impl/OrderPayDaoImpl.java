/**
 * 
 */
package com.ztravel.payment.dao.impl;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.payment.dao.OrderPayDao;
import com.ztravel.payment.po.OrderPay;

/**
 * @author zuoning.shen
 *
 */
@Repository
public class OrderPayDaoImpl extends BaseDaoImpl<OrderPay> implements OrderPayDao{

    @Override
    public OrderPay selectByOrderId(String orderId) {
        return session.selectOne(nameSpace + ".selectByOrderId", orderId);
    }

	@Override
	public OrderPay selectForUpdateByOrderId(String orderId) {
		return session.selectOne(nameSpace + ".selectForUpdateByOrderId", orderId);
	}

	@Override
	public OrderPay selectForUpdateByTraceNum(String traceNum) {
		return session.selectOne(nameSpace + ".selectForUpdateByTraceNum", traceNum);
	}
	
	@Override
	public OrderPay selectByTraceNum(String traceNum) {
		return session.selectOne(nameSpace + ".selectByTraceNum", traceNum);
	}

}
