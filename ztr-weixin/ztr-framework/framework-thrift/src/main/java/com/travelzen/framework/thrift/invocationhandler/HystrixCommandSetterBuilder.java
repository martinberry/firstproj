//package com.travelzen.framework.thrift.invocationhandler;
//
//import com.netflix.hystrix.HystrixCommand;
//import com.netflix.hystrix.HystrixCommandGroupKey;
//import com.netflix.hystrix.HystrixCommandKey;
//import com.netflix.hystrix.HystrixCommandProperties;
//
//public class HystrixCommandSetterBuilder {
//	
//	private String commandGroup = "DefaultGroup";
//	
//	private String commandKey = "DefaultKey";
//	
//	public HystrixCommandSetterBuilder() {
//		
//	}
//	
//	public HystrixCommand.Setter build() {
//		return HystrixCommand.Setter
//        .withGroupKey(HystrixCommandGroupKey.Factory.asKey(commandGroup))
//        .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey))
//        .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(60 * 1000));
//	}
//	
//	public HystrixCommandSetterBuilder commandGroup(String commandGroup) {
//		this.commandGroup = commandGroup;
//		return this;
//	}
//	
//	public HystrixCommandSetterBuilder commandKey(String commandKey) {
//		this.commandKey = commandKey;
//		return this;
//	}
//	
//}
