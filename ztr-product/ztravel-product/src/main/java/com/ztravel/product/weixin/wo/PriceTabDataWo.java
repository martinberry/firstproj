package com.ztravel.product.weixin.wo;

import java.util.Map;
import com.ztravel.product.front.wo.DayWo;

public class PriceTabDataWo {
	private Map<String, DayWo> calendar;//必须按key有序
	//最低成人价格 
	private Double lowestAdultPrice;
	//最高市场价格 
	private Double highestMarketPrice;
	
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
