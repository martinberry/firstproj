package com.ztravel.front.operator.config;

import org.springframework.stereotype.Component;

import com.github.jmkgreen.morphia.logging.MorphiaLoggerFactory;
import com.github.jmkgreen.morphia.logging.slf4j.SLF4JLogrImplFactory;



@Component
public class MorphiaLogger {
	
	static {
		System.out.println("reset logger factory");
		MorphiaLoggerFactory.reset();
		System.out.println("register logger factory");
		MorphiaLoggerFactory.registerLogger(SLF4JLogrImplFactory.class);
	}
	
	
	

}
