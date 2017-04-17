/**
 *
 */
package com.ztravel.payment.dao;

import java.util.List;
import java.util.Map;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.common.enums.PaymentType;
import com.ztravel.common.enums.TradeType;
import com.ztravel.payment.po.Trade;

/**
 * @author zuoning.shen
 *
 */
public interface TradeDao extends BaseDao<Trade> {
    Trade selectByOrderId(String orderId, PaymentType paymentType);

    int countTrades(Map params);

    List<Trade> selectTrades(Map params);

    Trade selectByOrderId(String orderId, PaymentType paymentType, TradeType tradetype) ;
}
