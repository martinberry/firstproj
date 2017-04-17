package com.ztravel.back.test;

import java.util.Date;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.mongo.morphia.JodaTimeConverter;
import com.ztravel.common.test.SpringJUnit4ClassRunnerWithLog;
import com.ztravel.datasource.config.DataSourceConfig;
import com.ztravel.front.operator.config.MybatisGlobalConfig;
import com.ztravel.order.dao.IOrderDao;
import com.ztravel.order.po.Order;
import com.ztravel.product.back.activity.entity.Activity;
import com.ztravel.product.dao.IActivityDao;

@RunWith(SpringJUnit4ClassRunnerWithLog.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class JunTester {
	@Configuration
	@Import({
		DataSourceConfig.class,
		MybatisGlobalConfig.class,
	})
//	@ImportResource({
//	    "classpath:spring/ztravel-datastore.xml"
//	})
	@ComponentScan({
//		"com.ztravel.product.dao",
		"com.ztravel.order.dao.impl"
		})
	static class TempConfig{}

//	@Resource
	IActivityDao activityDaoImpl;

	@Resource
	IOrderDao orderDaoImpl;

	@Test
	public void testActivityDao(){
		//5666741f595e85ccfcb9edf6
		Activity ac = activityDaoImpl.getActivityById("5666741f595e85ccfcb9edf6");
		DateTime from = ac.getCoupons().get("1512081435161513").getValidTimeFrom();
		System.out.println(ac.getUpdateTime());
	}

	@Test
	public void jodaTimeConvertTest(){
		JodaTimeConverter dtc = new JodaTimeConverter();
		DateTime now = DateTime.now();
		Date dbDate = (Date)dtc.encode(now);
		long mills = 1449556539000l;
		Date date = new Date(mills);
		DateTime dateTime = new DateTime(mills);
		DateTime jodaTime = (DateTime)dtc.decode(DateTime.class, dbDate);
		System.out.println(45);
	}

	@Test
	public void testTopsConfReader(){
		try {
			System.out.println(Integer.MAX_VALUE);
//			Properties pros = TopsConfReader.getConfProperties("properties/redis.properties", ConfScope.R);
//			System.out.println(pros.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void selectOrder(){
		Order order = orderDaoImpl.selectById("1512302221042401");
		System.out.println(JSONObject.toJSONString(order));
	}
}
