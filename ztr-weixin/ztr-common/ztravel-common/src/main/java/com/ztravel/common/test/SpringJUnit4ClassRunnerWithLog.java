package com.ztravel.common.test;

import java.io.FileNotFoundException;

import org.junit.runners.model.InitializationError;
import org.slf4j.Logger;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;

/**
  * @author wanhaofan 
  * 
  * junit 测试用例Runwith 使用此类可以打印日志
  * @see TimmingTest.class
  */
public class SpringJUnit4ClassRunnerWithLog extends SpringJUnit4ClassRunner{
	
	private static final Logger logger = RequestIdentityLogger.getLogger(SpringJUnit4ClassRunnerWithLog.class);
	
	public SpringJUnit4ClassRunnerWithLog(Class<?> clazz)
			throws InitializationError {
		super(clazz);
	}

	static{
		try {
			Log4jConfigurer.initLogging("classpath:properties/logback-variables.properties");
		} catch (FileNotFoundException e) {
			logger.error("properties/logback-variables.properties cannot find", e);
		}
	}
	
}
