package com.ztravel.operator.financeMantain.util;

import java.text.SimpleDateFormat;

import org.joda.time.DateTime;

public class DateTime2StrUtil {
	public static String dateTime2Str(DateTime dateTime ){
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(null != dateTime){
			try{
				str =  sdf.format(dateTime.toDate());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return str;
	}
}
