/**
 * 
 */
package com.ztravel.payment.dao;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.payment.po.OrderPay;

/**
 * @author zuoning.shen
 *
 */
public interface OrderPayDao extends BaseDao<OrderPay> {
    OrderPay selectByOrderId(String orderId);
    
	OrderPay selectForUpdateByOrderId(String orderId);
	
	OrderPay selectForUpdateByTraceNum(String traceNum);

	OrderPay selectByTraceNum(String traceNum);
}
