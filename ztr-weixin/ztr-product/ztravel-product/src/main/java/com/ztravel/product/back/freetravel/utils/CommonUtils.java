package com.ztravel.product.back.freetravel.utils;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

public class CommonUtils {
	public static List<DateTime> convertAvaiDays(String start, String end, String weeks) {
		List<DateTime> days = new ArrayList<DateTime>() ;
		DateTime sd = new DateTime(start) ;
		DateTime ed = new DateTime(end) ;
		List<Integer> weeksNum = new ArrayList<Integer>() ;
		String[] weeksArr = weeks.split(" ") ;
		for(int w=0;w<weeksArr.length;w++){
			if(weeksArr[w].indexOf("一")>-1){
				weeksNum.add(1) ;
			}else if(weeksArr[w].indexOf("二")>-1){
				weeksNum.add(2) ;
			}else if(weeksArr[w].indexOf("三")>-1){
				weeksNum.add(3) ;
			}else if(weeksArr[w].indexOf("四")>-1){
				weeksNum.add(4) ;
			}else if(weeksArr[w].indexOf("五")>-1){
				weeksNum.add(5) ;
			}else if(weeksArr[w].indexOf("六")>-1){
				weeksNum.add(6) ;
			}else if(weeksArr[w].indexOf("日")>-1){
				weeksNum.add(7) ;
			}
		}
		int weeknum = 1 ;
		while(!sd.isAfter(ed) || sd.isEqual(ed)){
			weeknum = sd.dayOfWeek().get() ;
			DateTime newDT = sd ;
			if(weeksNum.contains(weeknum)){
				days.add(newDT) ;
			}
			sd = sd.plusDays(1) ;
		}
		return days ;
	}
}
