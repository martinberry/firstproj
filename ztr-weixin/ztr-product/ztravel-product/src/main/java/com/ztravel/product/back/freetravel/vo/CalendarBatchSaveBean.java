package com.ztravel.product.back.freetravel.vo;


/**
 * @author wanhaofan
 * 批量修改新增用
 * */
public class CalendarBatchSaveBean extends CalendarAbstractSaleBean{

	//开始时间
	private String start ;

	//结束时间
	private String end ;

	//周
	private String weekDays ;
	
	//是否修改价格信息
	private boolean needUpdPrice ;

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

	public boolean isNeedUpdPrice() {
		return needUpdPrice;
	}

	public void setNeedUpdPrice(boolean needUpdPrice) {
		this.needUpdPrice = needUpdPrice;
	}

}
