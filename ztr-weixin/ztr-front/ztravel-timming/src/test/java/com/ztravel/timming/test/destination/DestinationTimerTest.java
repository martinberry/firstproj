package com.ztravel.timming.test.destination;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.ztravel.product.timming.service.IDestinationTimmingService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class DestinationTimerTest {

	@Configuration
	@Import({
		com.ztravel.timming.config.AppConfig.class
	})
	@ComponentScan({
		"com.ztravel.product.dao"
	})
	static class TempTimmingConfig {}

	@Resource
	private IDestinationTimmingService destinationTimmingService;

//	@Test
	public void executeDestinationTimer(){
		try {
			destinationTimmingService.updateAvailableDestination();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	public void executeVisaDestinationTimer(){
		try {
			destinationTimmingService.updateVisaAvailableDestination();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void executeLocalDestinationTimer(){
		try {
			destinationTimmingService.updateLocalAvailableDestination();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
