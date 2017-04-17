package com.ztravel.common.util;

import java.util.HashMap;
import java.util.Map;


public class NumberUtil {
	
	private static Map<Integer,String> map = new HashMap<>();
	
	static{
		map.put(0, "零");
		map.put(1, "一");
		map.put(2, "二");
		map.put(3, "三");
		map.put(4, "四");
		map.put(5, "五");
		map.put(6, "六");
		map.put(7, "七");
		map.put(8, "八");
		map.put(9, "九");
	}
	
	public static String numberUpper(Integer number){
		Integer num = number == null ? 0 : number;
		if(num > 10 || num < 0){
			throw new IllegalArgumentException("数字需大于等于０且小于10");
		}
		return map.get(num);
	}
	
	public static String numberUpper(String number){
		if(null == number || number.length() == 0){
			throw new IllegalArgumentException("转换的数字不能为空");
		}
		Integer num = Integer.parseInt(number);
		return numberUpper(num);
	}
	
	public static void main(String args[]){
		System.out.println(numberUpper("1"));
	}
}
