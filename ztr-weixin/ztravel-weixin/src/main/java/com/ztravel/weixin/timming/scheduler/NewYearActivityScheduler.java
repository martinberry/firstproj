package com.ztravel.weixin.timming.scheduler;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.weixin.timming.service.NewYearTimmingService;

@Component
public class NewYearActivityScheduler {
	
	private static Logger logger = RequestIdentityLogger.getLogger(NewYearActivityScheduler.class);
	
	@Resource
	private NewYearTimmingService newYearTimmingServiceImpl;
	
	@Scheduled(cron="0 0/5 * * * ?") //test
	public void setNewYearGiftStatus(){
		try{
			logger.info("===================元旦礼物状态释放超时定时任务开始执行===================");
			newYearTimmingServiceImpl.updateGiftStatus();
			logger.info("===================元旦礼物状态释放超时定时任务执行结束===================");
		}catch(Exception e) {
			logger.error("===================元旦礼物状态释放超时定时任务运行异常===================",e);
		}
	}
	
	@Scheduled(cron="0 0/1 * * * ?") //test
	public void setCarousel(){
		logger.info("===================new year award record set to redis start ===================");
		newYearTimmingServiceImpl.setCarousel();
		logger.info("===================new year award record set to redis end===================");
	}



}
