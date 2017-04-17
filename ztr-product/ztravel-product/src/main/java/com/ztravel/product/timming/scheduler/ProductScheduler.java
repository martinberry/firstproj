package com.ztravel.product.timming.scheduler;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.product.timming.service.IProductTimmingService;

@Component
public class ProductScheduler {

	private static Logger logger = RequestIdentityLogger.getLogger(ProductScheduler.class);

	@Resource
	private IProductTimmingService productTimmingServiceImpl;

	@Scheduled(cron="0 0 5 * * ?")
	public void setProductAutoExpired(){
		try{
			logger.info("真旅行产品过期定时任务开始执行");
			productTimmingServiceImpl.setAutoExpired();
		}catch(Exception e) {
			logger.error("真旅行产品过期定时任务运行异常", e);
		}
	}


}
