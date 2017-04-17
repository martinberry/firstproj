package com.ztravel.common.adapter.beanFactory;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * 适配器bean工厂，提供static bean对象
 * @author liuzhuo
 *
 */

public class AdapterBeanFactory {
	
	private static ApplicationContext applicationContext;
	
	public AdapterBeanFactory() {
		
	}

	static {
		applicationContext = new ClassPathXmlApplicationContext("spring/spring-producer.xml");
	}
	

	public static <T> T  getBean(Class<T> requiredType) {
		return applicationContext.getBean(requiredType);
	}

}
