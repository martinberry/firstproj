package com.ztravel.common.holder;

/**
 * 后台会话id holder
 * 
 * @author liuzhuo
 *
 */

public class OperatorSidHolder {
	
	private static ThreadLocal<String> sessionIdHolder = new ThreadLocal<String>();
	
	public static  void set(String sid) {
		sessionIdHolder.set(sid);
	}
	
	public static String get() {
		return sessionIdHolder.get();
	}
	
	public static void remove() {
		sessionIdHolder.remove();
	}

}
