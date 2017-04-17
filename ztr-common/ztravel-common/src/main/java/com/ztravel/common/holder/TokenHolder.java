package com.ztravel.common.holder;
/**
 * 在filter中存放TokenBean，
 * 注：filter是线程不安全的
 * @author liuzhuo
 *
 */
public class TokenHolder {

	private static ThreadLocal<String> tokenHolder = new ThreadLocal<String>();

	public static void set(String sid) {
		tokenHolder.set(sid);
	}

	public static String get() {
		return tokenHolder.get();
	}

	public static void remove() {
		tokenHolder.remove();
	}

}