/**
 *
 */
package com.ztravel.payment.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.common.enums.PaymentType;
import com.ztravel.common.enums.TradeType;
import com.ztravel.payment.dao.TradeDao;
import com.ztravel.payment.po.Trade;

/**
 * @author zuoning.shen
 *
 */
@Repository
public class TradeDaoImpl extends BaseDaoImpl<Trade> implements TradeDao {

    @Override
    public Trade selectByOrderId(String orderId, PaymentType paymentType) {
        Map params = new HashMap();
        params.put("orderId", orderId);
        params.put("paymentType", paymentType.name());
        return session.selectOne(nameSpace + ".selectByOrderId", params);
    }

    @Override
    public Trade selectByOrderId(String orderId, PaymentType paymentType, TradeType tradetype) {
        Map params = new HashMap();
        params.put("orderId", orderId);
        params.put("paymentType", paymentType.name());
        params.put("tradeType", tradetype.name());
        return session.selectOne(nameSpace + ".selectByOrderId2", params);
    }

    @Override
    public int countTrades(Map params) {
        return session.selectOne(nameSpace + ".countTrades", params);
    }

    @Override
    public List<Trade> selectTrades(Map params) {
        return session.selectList(nameSpace + ".selectTrades", params);
    }

}
