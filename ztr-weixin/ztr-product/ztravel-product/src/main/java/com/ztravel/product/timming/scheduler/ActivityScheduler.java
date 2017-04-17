package com.ztravel.product.timming.scheduler;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.product.timming.service.IActivityTimmingService;

@Component
public class ActivityScheduler {


	private static Logger logger = RequestIdentityLogger.getLogger(ActivityScheduler.class);

	@Resource
	private IActivityTimmingService activityTimmingServiceImpl;

	@Scheduled(cron="0 0/1 * * * ?")
	public void setActivityAutoExpired(){
		try{
			logger.info("真旅行活动过期定时任务开始执行");
			activityTimmingServiceImpl.setActivityAutoExpired();
		}catch(Exception e) {
			logger.error("真旅行产品过期定时任务运行异常", e);
		}
	}

	@Scheduled(cron="0 */1 * * * ?")
	public void setCouponAutoExpired(){
		try{
			logger.info("真旅行代金券过期定时任务开始执行");
			activityTimmingServiceImpl.setCouponAutoExpired();
		}catch(Exception e) {
			logger.error("真旅行代金券过期定时任务运行异常", e);
		}
	}

	@Scheduled(cron="0 0 0/1 * * ?")
	public void setCouponToFinished(){
		try{
			logger.info("真旅行代金券已发放定时任务开始执行");
			activityTimmingServiceImpl.setSendingToFinished();
		}catch(Exception e) {
			logger.error("真旅行代金券已发放定时任务运行异常", e);
		}
	}



}
