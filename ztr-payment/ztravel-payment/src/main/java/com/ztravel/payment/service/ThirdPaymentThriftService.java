package com.ztravel.payment.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.travelzen.framework.util.TZBeanUtils;
import com.travelzen.swordfish.thrift.client.annotation.ThriftServiceEndpoint;
import com.ztravel.payment.dao.OrderPayDao;
import com.ztravel.payment.paygate.model.PayResultBean;
import com.ztravel.payment.paygate.model.RefundResultBean;


@Service("ThirdPaymentThriftService")
@ThriftServiceEndpoint
public class ThirdPaymentThriftService implements IThirdPaymentThriftService{

	private static Logger logger = LoggerFactory.getLogger(ThirdPaymentThriftService.class);

	@Resource
	private ThirdPaymentService thirdPaymentService ;
	@Resource
	private OrderPayDao orderPayDao;
	@Resource
	private TradeService tradeService;

	@Override
	public boolean notifyOrderPay(PayResultBean payResult) throws Exception{
		boolean ret = false ;
		try{
			logger.error("notifyOrderPay start, payResult:::{}", TZBeanUtils.describe(payResult));
			thirdPaymentService.notifyOrderPay(payResult);
			ret = true ;
		}catch(Exception e){
			logger.error("notifyOrderPay fail", e);
		}
		return ret ;
	}
	
	@Override
	public boolean notifyOrderRefund(RefundResultBean refundResult) throws Exception{
		boolean ret = false ;
		try{
			logger.error("notifyOrderRefund start, refundResult:::{}", TZBeanUtils.describe(refundResult));
			thirdPaymentService.notifyOrderRefund(refundResult);
			ret = true ;
		}catch(Exception e){
			logger.error("notifyOrderRefund fail", e);
		}
		return ret ;
	}

}
