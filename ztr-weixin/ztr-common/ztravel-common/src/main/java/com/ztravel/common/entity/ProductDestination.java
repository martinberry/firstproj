package com.ztravel.common.entity;

import java.util.List;

/**
 * 目的地信息，用于前台首页、产品列表页页面展示
 * 只需前两级目的地
 * @author MH
 */
public class ProductDestination {
	/**
	 * 目的地名称
	 */
	private String destName;
	/**
	 * 下一级目的地
	 */
	private List<String> subDestinations;

	public String getDestName() {
		return destName;
	}
	public void setDestName(String destName) {
		this.destName = destName;
	}
	public List<String> getSubDestinations() {
		return subDestinations;
	}
	public void setSubDestinations(List<String> subDestinations) {
		this.subDestinations = subDestinations;
	}

}
