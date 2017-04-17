package com.ztravel.product.front.wo;

import java.util.List;
import java.util.Map;

public class CalendarDataWo {
	private Map<String, DayWo> calendar;//必须按key有序
	private List<FHTipWo> fhTips;//必须时间有序

	//最低成人价格
	private Double lowestAdultPrice;

	//最高市场价格
	private Double highestMarketPrice;

	public List<FHTipWo> getFhTips() {
		return fhTips;
	}
	public void setFhTips(List<FHTipWo> fhTips) {
		this.fhTips = fhTips;
	}
	public Map<String, DayWo> getCalendar() {
		return calendar;
	}
	public void setCalendar(Map<String, DayWo> calendar) {
		this.calendar = calendar;
	}
	public Double getLowestAdultPrice() {
		return lowestAdultPrice;
	}
	public void setLowestAdultPrice(Double lowestAdultPrice) {
		this.lowestAdultPrice = lowestAdultPrice;
	}
	public Double getHighestMarketPrice() {
		return highestMarketPrice;
	}
	public void setHighestMarketPrice(Double highestMarketPrice) {
		this.highestMarketPrice = highestMarketPrice;
	}

}
