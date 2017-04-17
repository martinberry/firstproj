package com.ztravel.common.util;

public class ZtrStringUtils {
	
	/**
	 * 字符串连接
	 * e.g 
	 * ZtrStringUtils.contact("a","b","c") return "abc"
	 * @param args
	 * @return
	 */
	public static String contact(Object ...args){
		
		StringBuffer sb = new StringBuffer();
		
		for(Object str: args) {
			sb.append(str.toString());
		}
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(contact("a",",","b",",","c"));
	}

}
