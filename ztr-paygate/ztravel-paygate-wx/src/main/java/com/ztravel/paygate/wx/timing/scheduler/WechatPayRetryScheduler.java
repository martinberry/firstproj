/**
 * 
 */
package com.ztravel.paygate.wx.timing.scheduler;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.redis.client.RedisClient;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.paygate.wx.client.entity.RefundQueryResponseEntity;
import com.ztravel.paygate.wx.client.entity.UnifieldOrderNotifyEntity;
import com.ztravel.paygate.wx.client.entity.UnifieldOrderNotifyResponse;
import com.ztravel.paygate.wx.service.IWxPayNotifyService;
import com.ztravel.paygate.wx.service.IWxRefundService;

/**
 * @author haofan.wan
 *
 */
@Component
public class WechatPayRetryScheduler {
    private static Logger logger = RequestIdentityLogger.getLogger(WechatPayRetryScheduler.class);
    
    @Resource
    private IWxRefundService wxRefundService;
    
    @Resource
    private IWxPayNotifyService wxPayNotifyService;
    
    private static final RedisClient client = RedisClient.getInstance();
    
    private static final String WECHAT_PAY_SCHEDULER_LOCK = "WECHAT_PAY_SCHEDULER_LOCK" ;
    
    private static final String WECHAT_REFUND_SCHEDULER_LOCK = "WECHAT_REFUND_SCHEDULER_LOCK" ;
    
    private static String REDIS_VALUE = String.valueOf(System.currentTimeMillis());
    
    private static final int THREE_MINIUTES = 3 * 60 ;
    
    //@Scheduled(cron="0 * * * * ?" ) 
    @Scheduled(cron="0 0/1 * * * ?")
    public void WechatPayRetry(){
    	if(needRun(WECHAT_PAY_SCHEDULER_LOCK)){
	        try{
	            logger.info("wechat pay retry start====>");
	            List<UnifieldOrderNotifyEntity> records = wxPayNotifyService.searchUnProceedRecords() ;
	            logger.info("need proceed wechat pay records:::{}", records);
	            if(!CollectionUtils.isEmpty(records)){
	            	for(UnifieldOrderNotifyEntity entity : records){
	            		logger.info("notify orderpay start, request:::{}", TZBeanUtils.describe(entity));
	            		UnifieldOrderNotifyResponse response = wxPayNotifyService.proceed(entity) ;
	            		logger.info("notify orderpay end, response:::{}", response);
	            	}
	            }
	            logger.info("====>wechat pay retry end");
	        }catch(Exception e) {
	            logger.error("wechat pay retry error",e);
	        }
    	}
    }
    
    //@Scheduled(cron="0 * * * * ?" ) 
    @Scheduled(cron="0 0/1 * * * ?")
    public void WechatRefundRetry(){
    	if(needRun(WECHAT_REFUND_SCHEDULER_LOCK)){
	        try{
	            logger.info("wechat refund retry start====>");
	            List<RefundQueryResponseEntity> records = wxRefundService.searchUnProceedRefundRecord() ;
	            logger.info("need proceed wechat refund records:::{}", records);
	            if(!CollectionUtils.isEmpty(records)){
	            	for(RefundQueryResponseEntity entity : records){
	            		logger.info("notify orderrefund start, request:::{}", TZBeanUtils.describe(entity));
	            		CommonResponse response = wxRefundService.proceedForRefund(entity.getRefund_id()) ;
	            		logger.info("notify orderrefund end, response:::{}", TZBeanUtils.describe(response));
	            	}
	            }
	            logger.info("====>wechat refund retry end");
	        }catch(Exception e) {
	            logger.error("wechat refund retry error",e);
	        }
    	}
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
