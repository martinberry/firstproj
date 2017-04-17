package com.ztravel.product.back.freetravel.vo;

import java.util.List;

/**
 * @author wanhaofan
 * 成本数据VO
 * */
public class CostDayData{

	private Integer day ;

	private List<Double> price ;

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public List<Double> getPrice() {
		return price;
	}

	public void setPrice(List<Double> price) {
		this.price = price;
	}

}
