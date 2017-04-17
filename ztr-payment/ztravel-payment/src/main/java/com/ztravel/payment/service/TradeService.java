/**
 *
 */
package com.ztravel.payment.service;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ztravel.common.constants.OperatorConstants;
import com.ztravel.common.enums.PaymentType;
import com.ztravel.common.enums.TradeStatus;
import com.ztravel.common.payment.TradeBean;
import com.ztravel.common.util.IdGeneratorUtil;
import com.ztravel.payment.dao.TradeDao;
import com.ztravel.payment.po.Trade;
import com.ztravel.payment.po.factory.ModelFactory;

/**
 * @author zuoning.shen
 *
 */
@Service
public class TradeService {
    private static Logger logger = LoggerFactory.getLogger(TradeService.class);

    @Resource
    private TradeDao tradeDao;
    @Resource
	private IdGeneratorUtil idGeneratorUtil ;

    @Profiled
    @Transactional(value = "ztravel-txManager", propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public void recordTradeRequest(TradeBean bean) throws Exception{
        Trade trade = tradeDao.selectByOrderId(bean.getOrderId(), bean.getPaymentType());
        if(trade == null){
            trade = convertFromTradeBean(bean);
            tradeDao.insert(trade);
        }else{
            if(TradeStatus.SUCCESS.equals(trade.getTradeStatus())){
                throw new Exception("订单已支付");
            }
            trade.setTradeStatus(bean.getTradeStatus().name());
            trade.setUpdatedby(OperatorConstants.AUTO_USER);
            trade.setUpdated(DateTime.now());
            tradeDao.update(trade);
        }
    }

    @Profiled
    @Transactional(value = "ztravel-txManager", propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public void recordTradeResult(TradeBean bean) throws Exception{
        String orderId = bean.getOrderId();
        PaymentType paymentType = bean.getPaymentType();
        Trade trade = tradeDao.selectByOrderId(orderId, paymentType, bean.getTradeType());
        if(trade == null){
            trade = convertFromTradeBean(bean);
            tradeDao.insert(trade);
        }else{
            if(TradeStatus.SUCCESS.equals(trade.getTradeStatus())){
                logger.warn("Trade already succeed, orderId: {}",  bean.getOrderId());
                return;
            }
            if(paymentType.isNetPaymentType()){
                trade.setTraceNum(bean.getTraceNum());
                trade.setBankPaymentTime(bean.getBankPaymentTime());
            }
            trade.setTradeStatus(bean.getTradeStatus().name());
            trade.setUpdatedby(OperatorConstants.AUTO_USER);
            trade.setUpdated(DateTime.now());
            tradeDao.update(trade);
        }
    }

    private Trade convertFromTradeBean(TradeBean bean) throws Exception{
        Trade trade = ModelFactory.createTrade(idGeneratorUtil.getTradeId());
        trade.setMemberId(bean.getMemberId());
        trade.setOrderId(bean.getOrderId());
        trade.setOrderType(bean.getOrderType().name());
        trade.setTradeType(bean.getTradeType().name());
        trade.setProductType(bean.getProductType().name());
        trade.setOrderAmount(bean.getOrderAmount());
        trade.setTradeAmount(bean.getTradeAmount());
        trade.setPaymentType(bean.getPaymentType().name());
        trade.setComment(bean.getComment());
        trade.setTradeDate(bean.getTradeDate());
        trade.setTradeStatus(bean.getTradeStatus().name());
        if(bean.getPaymentType().isNetPaymentType()){
            trade.setTraceNum(bean.getTraceNum());
            trade.setBankPaymentTime(bean.getBankPaymentTime());
        }
        if(PaymentType.Coupon.equals(bean.getPaymentType())){
            trade.setCouponItemId(bean.getCouponItemId());
        }
        return trade;
    }

    public Trade getAliTradeByOrderId(String orderId) throws Exception{
    	  PaymentType paymentType = PaymentType.Alipay;
          Trade trade = tradeDao.selectByOrderId(orderId, paymentType);
    	  return trade;
    }
}
