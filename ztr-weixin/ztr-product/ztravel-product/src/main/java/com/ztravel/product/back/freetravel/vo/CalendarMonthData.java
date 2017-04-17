package com.ztravel.product.back.freetravel.vo;

import java.util.List;

/**
 * @author wanhaofan
 * 日历数据VO
 * */
public class CalendarMonthData {

	private List<CalendarDayData> monthData ;

	private String month ;

	public List<CalendarDayData> getMonthData() {
		return monthData;
	}

	public void setMonthData(List<CalendarDayData> monthData) {
		this.monthData = monthData;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

}
