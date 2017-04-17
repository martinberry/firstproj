package com.ztravel.order.timming.scheduler;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.order.dao.IOrderDao;
import com.ztravel.order.dao.IOrderProductDao;
import com.ztravel.order.timming.service.OrderTimmingService;
import com.ztravel.payment.service.IOrderPaymentService;

@Component
public class OrderScheduler {

	private static Logger logger = RequestIdentityLogger.getLogger(OrderScheduler.class);

	@Resource
	private OrderTimmingService orderTimmingService;

	@Resource
	IOrderDao orderDaoImpl;
	
	@Resource
	IOrderProductDao OrderProductDaoImpl;

	@Resource
	IOrderPaymentService orderPaymentService;

//	@Scheduled(cron="0 0 5 * * ?")
	@Scheduled(cron="0 0/2 * * * ?") //test
	public void setOrderAutoCancel(){
		try{
			logger.info("===================真旅行订单支付超时定时任务开始执行===================");
			orderTimmingService.cancelOrder();
			logger.info("===================真旅行订单支付超时定时任务执行结束===================");
		}catch(Exception e) {
			logger.error("===================真旅行订单支付超时定时任务运行异常===================",e);
		}
	}
	
	@Scheduled(cron="0 0/30 * * * ?") //test
	public void setVoucherOrderAutoCancel(){
		try{
			logger.info("===================真旅行代金券订单支付超时定时任务开始执行===================");
			orderTimmingService.cancelVoucherOrder();
			logger.info("===================真旅行代金券订单支付超时定时任务执行结束===================");
		}catch(Exception e) {
			logger.error("===================真旅行代金券订单支付超时定时任务运行异常===================",e);
		}
	}
	
//	@Scheduled(cron="0 0 5 * * ?")
	@Scheduled(cron="0 1 0/2 * * ?" ) //test
	public void setAutoTravelling(){
		try{
			logger.info("===================真旅行订单自动设置出行状态定时任务开始执行===================");
			orderTimmingService.setAutoTravelling();
			logger.info("===================真旅行订单自动设置出行状态定时任务执行结束===================");
		}catch(Exception e) {
			logger.error("===================真旅行订单自动设置出行状态定时任务运行异常===================",e);
		}
	}

//	@Scheduled(cron="0 0 5 * * ?")
	@Scheduled(cron="0 59 0/1 * * ?" ) //test
	public void setAutoCompleted(){
		try{
			logger.info("===================真旅行订单自动完成出行定时任务开始执行===================");
			orderTimmingService.setAutoCompleted();
			logger.info("===================真旅行订单自动完成出行定时任务执行结束===================");
		}catch(Exception e) {
			logger.error("===================真旅行订单自动完成出行定时任务运行异常===================",e);
		}
	}

	@Scheduled(cron="0 0 20 * * ?" )//完已成状态变更后的44小时执行，即第三天20:00点
	public void setAutoNoticeAfterCompleted(){
		try{
			logger.info("===================真旅行订单完成后发送评价提醒任务执行开始===================");
			orderTimmingService.setAutoNotice();
			logger.info("===================真旅行订单完成后发送评价提醒任务执行结束===================");
		}catch(Exception e) {
			logger.error("===================真旅行订单完成后发送评价提醒任务执行异常===================",e);
		}
	}

	@Scheduled(cron="0 2 0 * * ?" )//离产品出行还有15天（360小时），但礼盒发放还未点击
	public void sendBackMessageGift(){
		try{
			logger.info("===================真旅行订单礼盒发放后台消息任务执行开始===================");
			orderTimmingService.sendBackMsgGift();
			logger.info("===================真旅行订单礼盒发放后台消息任务执行结束===================");
		}catch(Exception e) {
			logger.error("===================真旅行订单礼盒发放后台消息任务执行异常===================",e);
		}
	}
	@Scheduled(cron="0 2 0 * * ?" )//离产品出行还有2天（48小时），但出行通知还未点击
	public void sendBackMessageOutNotice(){
		try{
			logger.info("===================真旅行订单出行通知后台消息任务执行开始===================");
			orderTimmingService.sendBackMsgOutNotice();
			logger.info("===================真旅行订单出行通知后台消息任务执行结束===================");
		}catch(Exception e) {
			logger.error("===================真旅行订单出行通知后台消息任务执行异常===================",e);
		}
	}

}
