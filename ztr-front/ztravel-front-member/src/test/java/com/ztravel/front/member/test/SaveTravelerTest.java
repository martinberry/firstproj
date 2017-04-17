package com.ztravel.front.member.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.google.common.collect.Lists;
import com.ztravel.member.po.TravelerEntity;
import com.ztravel.member.po.TravelerEntity.Credentials;
import com.ztravel.member.client.service.TravelerInfoClientService;

public class SaveTravelerTest {

	@Test
	public void testSaveTravelerOrderBook() throws Exception{


		 ApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);
		 TravelerInfoClientService travelInfoClientSerive  = (TravelerInfoClientService) context.getBean("travelerInfoClientServiceImpl");
		TravelerEntity traveler = new TravelerEntity();
		traveler.setFirstEnName("cubo");
		traveler.setLastEnName("tudo");

		List<Credentials> credentials = Lists.newArrayList();
		Credentials credential = new Credentials();
		credential.setDeadLineDay("2020-01-01");
		credential.setNumber("362421199003196810");
		credential.setType("IDCARD");
		credentials.add(credential);

		traveler.setCredentials(credentials);
		traveler.setIsActive(true);
		traveler.setIsDefault(true);
		traveler.setNationality("中国");
		traveler.setBirthday("1990-03-27");
		traveler.setTravelType("ADULT");
		traveler.setGender("MALE");

		String destinationType = "international";

		travelInfoClientSerive.saveTravelerForBookOrder(destinationType, traveler);

	}
}
