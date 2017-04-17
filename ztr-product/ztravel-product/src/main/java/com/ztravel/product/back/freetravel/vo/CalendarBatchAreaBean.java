package com.ztravel.product.back.freetravel.vo;


/**
 * @author wanhaofan
 * 批量操作
 * */
public class CalendarBatchAreaBean{
	//产品ID
	private String id ;
	
	//开始时间
	private String start ;

	//结束时间
	private String end ;

	//周
	private String weekDays ;

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getWeekDays() {
		return weekDays;
	}

	public void setWeekDays(String weekDays) {
		this.weekDays = weekDays;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
