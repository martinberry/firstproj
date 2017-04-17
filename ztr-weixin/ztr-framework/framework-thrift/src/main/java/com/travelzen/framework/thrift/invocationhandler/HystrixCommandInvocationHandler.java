//package com.travelzen.framework.thrift.invocationhandler;
//
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//
//import com.netflix.hystrix.HystrixCommand;
//
//public class HystrixCommandInvocationHandler implements InvocationHandler {
//
//	final InvocationHandler handler;
//	
//	final HystrixCommandSetterBuilder setterBuilder;
//	
//	public HystrixCommandInvocationHandler(InvocationHandler handler, HystrixCommandSetterBuilder setterBuilder) {
//		this.handler = handler;
//		this.setterBuilder = setterBuilder;
//	}
//	
//	@Override
//	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
//		
//		HystrixCommand.Setter setter = setterBuilder.build();
//		HystrixCommand<Object> cmd = new HystrixCommand<Object>(setter) {
//
//			@Override
//			protected Object run() throws Exception {
//				if (handler != null) {
//					try {
//						return handler.invoke(proxy, method, args);
//					} catch (Throwable e) {
//						e.printStackTrace();
//					}
//				}
//				return null;
//			}
//			
//			@Override
//			protected Object getFallback() {
//				return null;
//			}
//			
//		};
//		return cmd.execute();
//	}
//	
//}
