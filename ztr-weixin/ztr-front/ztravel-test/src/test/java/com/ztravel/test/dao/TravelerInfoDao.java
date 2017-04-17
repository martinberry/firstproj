package com.ztravel.test.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.github.jmkgreen.morphia.Datastore;
import com.travelzen.framework.util.DateUtils;
import com.ztravel.test.config.AppConfig;
import com.ztravel.test.dao.TravelerEntity.Credentials;

/**
 * 常旅客
 * @author liuzhuo
 *
 */
public class TravelerInfoDao {

	@Test
	public void add() {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		Datastore datastore = ctx.getBean(Datastore.class);

		TravelerEntity entity = buildTravellerEntity();
		datastore.save(entity);
	}

	public TravelerEntity buildTravellerEntity() {
		TravelerEntity entity = new TravelerEntity();

		entity.setAddress("宝山区呼兰西路123号");
		entity.setBirthday("2015-08-18");
		entity.setEmail("1234@qq.com");
		entity.setFirstEnName("jack");
		entity.setGender("男");
		entity.setIsActive(true);
		entity.setIsDefault(false);
		entity.setLastEnName("rose");
		entity.setMemberId("mem_001");
		entity.setNationality("中国");
		entity.setPhoneNum("1866700");
		entity.setTravelerNameCn("puck");
		entity.setTravelType("成人");


		List<Credentials> list = new LinkedList<Credentials>();
		Credentials temp1 = new Credentials();
		temp1.setType("身份证");
		temp1.setNumber("42900619890322");
		temp1.setDeadLineDay("2014-01-02");

		list.add(temp1);

		entity.setCredentials(list);
		return entity;
	}

	public static void main(String args[]){
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(3);
		list.add(5);
		String bookdDay = "2015-03-11";
		try {
			System.out.println(DateUtils.addDate(bookdDay, 3, list.get(0)));
			System.out.println(DateUtils.addDate(bookdDay, 3, list.get(list.size()-1)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
