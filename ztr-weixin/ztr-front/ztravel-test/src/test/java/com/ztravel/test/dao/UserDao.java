package com.ztravel.test.dao;

import java.util.Date;
import java.util.regex.Pattern;

import org.codehaus.plexus.util.StringUtils;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.ztravel.test.config.AppConfig;
import com.ztravel.test.gen.dao.OperatorMapper;
import com.ztravel.test.gen.po.Operator;

public class UserDao {

	@SuppressWarnings("resource")
	@Test
	public void testAddOperator() throws Exception {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

		Operator operator = new Operator();

		operator.setUserId("1234");
//		operator.setUserId(SequenceGenerator.generateId("opera_" ,"operator_id"));
		operator.setUserName("liuzhuo");
		operator.setPassword("123456");
		operator.setIsActive(true);
		operator.setDescription("测试新建后台用户");
		operator.setCreatedby("super opearator");
		operator.setUpdatedby("super opearator");
		operator.setCreated(new DateTime().toDate());
		operator.setUpdated(new Date());


		OperatorMapper operatorMapper = ctx.getBean(OperatorMapper.class);
		int result = operatorMapper.insert(operator);
		System.out.println(result);
	}

	public static void main(String args[]){
		System.out.println(gettime("12:46", "14:25", 1));
	}

	//时间必须是hh:mm格式
	public static String gettime(String start,String end, Integer addDays){
		String rex = "[0-9]{2}[:][0-9]{2}";
		Integer addDay = addDays == null ? 0 : addDays;
		Integer endHours = 0;
		Integer endMin = 0;
		Integer startMin = 0;
		Integer startHour = 0;
		Integer totalMin = 0;
		if(Pattern.compile(rex).matcher(start.trim()).matches() && Pattern.compile(rex).matcher(end.trim()).matches()){
			if(!StringUtils.isBlank(start) && !StringUtils.isBlank(end)){
				startHour = Integer.parseInt(start.substring(0,2));
				startMin = Integer.parseInt(start.substring(3,5));
				endMin = Integer.parseInt(end.substring(3, 5));
				if(addDay > 0){
					endHours = addDay * 24 + Integer.parseInt(end.substring(0,2));
				}else{
					endHours = Integer.parseInt(end.substring(0,2));
				}
				totalMin = (endHours*60+endMin) - (startHour*60 - startMin);
			}else{
				throw new NullPointerException("开始时间和结束时间不能为空");
			}
		}else{
			throw new IllegalArgumentException("时间格式不正确");
		}
		return (totalMin/60)+"h"+(totalMin%60)+"m";
	}

}
