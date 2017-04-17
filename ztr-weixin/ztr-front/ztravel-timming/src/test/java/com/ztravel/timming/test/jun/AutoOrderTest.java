package com.ztravel.timming.test.jun;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.ztravel.member.client.service.IOperatorMessageClientService;
import com.ztravel.order.client.service.IOrderClientService;
import com.ztravel.order.timming.service.OrderTimmingService;
import com.ztravel.payment.service.IAccountService;
import com.ztravel.product.timming.service.IActivityTimmingService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class AutoOrderTest {

	@Configuration
	@Import({
		com.ztravel.timming.config.AppConfig.class
	})
	@ComponentScan({
		"com.ztravel.order.dao"
	})
	static class TempTimmingConfig {}

	@Resource
	private OrderTimmingService OrderTimmingServiceImpl;
	@Resource
	private IOperatorMessageClientService operatorMessageClientServiceImpl;
	@Resource
	private IActivityTimmingService activityTimmingServiceImpl ;
	
	@Resource
	IOrderClientService orderClientServiceImpl;
	
	@Resource
	IAccountService accountService;
	@Test
	public void testOrder(){
		try {
//			operatorMessageClientServiceImpl.add(MessageTitleType.OUTNOTICE, "001", "横店3天2晚逗比之旅", "https://www.baidu.com");
//			OrderTimmingServiceImpl.setAutoCompleted();
//			OrderTimmingServiceImpl.setAutoCompleted();
//			accountService.addAmount("MEMB1507071721002529", 100l);
			//OrderTimmingServiceImpl.sendBackMsgGift();
//			activityTimmingServiceImpl.setCouponAutoExpired();
			orderClientServiceImpl.cancleOrder("1603281540227907", "pengfeitest");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
