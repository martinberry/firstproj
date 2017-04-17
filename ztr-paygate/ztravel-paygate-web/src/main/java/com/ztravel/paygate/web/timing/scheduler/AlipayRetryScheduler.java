/**
 * 
 */
package com.ztravel.paygate.web.timing.scheduler;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.redis.client.RedisClient;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.enums.GateType;
import com.ztravel.paygate.core.po.gen.Alipay;
import com.ztravel.paygate.core.po.gen.AlipayRefund;
import com.ztravel.paygate.web.service.IAlipayService;
import com.ztravel.payment.paygate.model.PayResultBean;
import com.ztravel.payment.paygate.model.RefundResultBean;
import com.ztravel.payment.service.IThirdPaymentThriftService;

/**
 * @author haofan.wan
 *
 */
@Component
public class AlipayRetryScheduler {
    private static Logger logger = RequestIdentityLogger.getLogger(AlipayRetryScheduler.class);
    
    @Resource
    private IAlipayService alipayService;
    
    @Resource
    private IThirdPaymentThriftService thirdPaymentThriftService ;
    
    private static final RedisClient client = RedisClient.getInstance();
    
    private static final String ALIPAY_SCHEDULER_LOCK = "ALIPAY_SCHEDULER_LOCK" ;
    
    private static final String ALIREFUND_SCHEDULER_LOCK = "ALIREFUND_SCHEDULER_LOCK" ;
    
    private static String REDIS_VALUE = String.valueOf(System.currentTimeMillis());
    
    private static final int THREE_MINIUTES = 3 * 60 ;
    
    //@Scheduled(cron="0 * * * * ?" ) 
    @Scheduled(cron="0 0/1 * * * ?")
    public void alipayPayRetry(){
    	if(needRun(ALIPAY_SCHEDULER_LOCK)){
	        try{
	            logger.info("alipay pay retry start====>");
	            List<Alipay> records = alipayService.searchUnProceedPayRecord() ;
	            logger.info("need proceed alipay records:::{}", records);
	            if(!CollectionUtils.isEmpty(records)){
	            	for(Alipay alipay : records){
	            		PayResultBean payResult = buildFromAlipay(alipay) ;
	            		logger.info("notify order start, payResult:::{}", TZBeanUtils.describe(payResult));
	            		boolean result = thirdPaymentThriftService.notifyOrderPay(payResult) ;
	            		logger.info("notify order end, response:::{}", result);
	            		if(result){
	            			Alipay entity = new Alipay() ;
	            			entity.setAlipayId(alipay.getAlipayId());
	            			entity.setConfirmResult("T");
	            			alipayService.updateSelectiveById(entity);
	            		}
	            	}
	            }
	            logger.info("====>alipay pay retry end");
	        }catch(Exception e) {
	            logger.error("alipay pay retry error",e);
	        }
    	}
    }
    
    //@Scheduled(cron="0 * * * * ?" ) 
    @Scheduled(cron="0 0/1 * * * ?")
    public void alipayRefundRetry(){
    	if(needRun(ALIREFUND_SCHEDULER_LOCK)){
	        try{
	            logger.info("alipay refund retry start====>");
	            List<AlipayRefund> records = alipayService.searchUnProceedRefundRecord() ;
	            logger.info("need proceed alirefund records:::{}", records);
	            if(!CollectionUtils.isEmpty(records)){
	            	for(AlipayRefund alipay : records){
	            		RefundResultBean refundResult = buildFromAlipay(alipay) ;
	            		logger.info("notify order start, refundResult:::{}", TZBeanUtils.describe(refundResult));
	            		boolean result = thirdPaymentThriftService.notifyOrderRefund(refundResult) ;
	            		logger.info("notify order end, refundResult:::{}", result);
	            		if(result){
	            			AlipayRefund entity = new AlipayRefund() ;
	            			entity.setAlipayRefundId(alipay.getAlipayRefundId());
	            			entity.setConfirmResult("T");
	            			alipayService.updateSelectiveById(entity);
	            		}
	            	}
	            }
	            logger.info("====>alipay refund retry end");
	        }catch(Exception e) {
	            logger.error("alipay refund retry error",e);
	        }
    	}
    }
    
    
    private PayResultBean buildFromAlipay(Alipay alipay){
    	PayResultBean payResult = new PayResultBean() ;
    	payResult.setTraceNum(alipay.getTraceNum());
    	payResult.setOrderNum(alipay.getOrderNum());
    	if (StringUtils.isNotBlank(alipay.getGmtPayment())) {
    		payResult.setBankPaymentTime(alipay.getGmtPayment());
		}
    	if(alipay.getTransAmount() != 0){
			payResult.setAmount(alipay.getTransAmount());		
		}
    	payResult.setGateType(GateType.Alipay.getGateCode());
    	return payResult ;
    }
    
    private RefundResultBean buildFromAlipay(AlipayRefund alipay){
    	RefundResultBean refundResult = new RefundResultBean() ;
    	refundResult.setTraceNum(alipay.getTraceNum());
    	refundResult.setOrderNum(alipay.getOrderNum());
    	refundResult.setGateType(GateType.Alipay.getGateCode());
    	refundResult.setRefundAmount(alipay.getRefundAmount());
    	return refundResult ;
    }
    
    
    private synchronized boolean needRun(String lock){
    	try {
    		String value = client.get(lock);
    		logger.info("redis value:"+REDIS_VALUE+",server redis value:"+value);
	    	if (null == value) {
	    		client.setNoJSON(lock, REDIS_VALUE, THREE_MINIUTES);
	    		return true;
	    	}else{
	    		if (value.equals(REDIS_VALUE)) {
	                // 更新Redis
	    			client.setNoJSON(lock, REDIS_VALUE, THREE_MINIUTES);
	                return true;
		        } else {
		            return false;
		        }
	    	}
    	} catch (Exception e) {
            logger.error("Redis异常信息:", e);
	    }
	    return false;
    }
}
