package com.ztravel.product.timming.scheduler;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.product.timming.service.IDestinationTimmingService;

@Component
public class AvailableDestinationScheduler {

	private static Logger LOGGER = RequestIdentityLogger.getLogger(AvailableDestinationScheduler.class);

	@Resource
	private IDestinationTimmingService destinationTimmingService;

	@Scheduled(cron="0 0 0/1 * * ?")
	public void updateAvailableDestination(){
		try {
			LOGGER.info("==========DestinationTimer真旅行自由行产品可用目的地定时任务开始执行==========");
			destinationTimmingService.updateAvailableDestination();
			LOGGER.info("==========DestinationTimer真旅行自由行产品可用目的地定时任务执行完毕==========");
		} catch (Exception e) {
			LOGGER.error("DestinationTimer真旅行自由行产品可用目的地定时任务执行异常", e);
		}
	}
	
	@Scheduled(cron="0 0 0/1 * * ?")
	public void updateVisaAvailableDestination(){
		try {
			LOGGER.info("==========DestinationTimer真旅行签证产品可用目的地定时任务开始执行==========");
			destinationTimmingService.updateVisaAvailableDestination();
			LOGGER.info("==========DestinationTimer真旅行签证产品可用目的地定时任务执行完毕==========");
		} catch (Exception e) {
			LOGGER.error("DestinationTimer真旅行签证产品可用目的地定时任务执行异常", e);
		}
	}
	
	@Scheduled(cron="0 0 0/1 * * ?")
	public void updateLocalAvailableDestination(){
		try {
			LOGGER.info("==========DestinationTimer真旅行当地游产品可用目的地定时任务开始执行==========");
			destinationTimmingService.updateLocalAvailableDestination();
			LOGGER.info("==========DestinationTimer真旅行当地游产品可用目的地定时任务执行完毕==========");
		} catch (Exception e) {
			LOGGER.error("DestinationTimer真旅行当地游产品可用目的地定时任务执行异常", e);
		}
	}

}
