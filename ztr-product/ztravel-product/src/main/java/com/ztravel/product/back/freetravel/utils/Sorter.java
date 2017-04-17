package com.ztravel.product.back.freetravel.utils;

import java.util.Comparator;

import com.ztravel.product.back.freetravel.entity.FlightInfo;
import com.ztravel.product.client.wo.FlightInfoWo;


/**
 * @author wanhaofan
 * 排序器
 * */
public class Sorter implements Comparator<Object>{

	@Override
	public int compare(Object origin, Object dest) {
		if(origin == null || dest == null){
			return 1;
		}else{
			if(origin instanceof FlightInfo && dest instanceof FlightInfo){
				FlightInfo f1 = (FlightInfo)origin ;
				FlightInfo f2 = (FlightInfo)dest ;
				return f1.getAirRangeIndex().compareTo(f2.getAirRangeIndex()) ;
			}else if(origin instanceof Integer && dest instanceof Integer){
				Integer i1 = (Integer)origin ;
				Integer i2 = (Integer)dest ;
				return i1.compareTo(i2) ;
			}else if(origin instanceof FlightInfoWo && dest instanceof FlightInfoWo){
				FlightInfoWo f1 = (FlightInfoWo)origin ;
				FlightInfoWo f2 = (FlightInfoWo)dest ;
				return f1.getAirRangeIndex().compareTo(f2.getAirRangeIndex()) ;
			}else{
				return 1 ;
			}
		}
	}

}
