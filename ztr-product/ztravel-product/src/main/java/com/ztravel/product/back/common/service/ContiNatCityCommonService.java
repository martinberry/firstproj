package com.ztravel.product.back.common.service;

import java.util.List;

public interface ContiNatCityCommonService {

	/**
	 * 获取大洲区域
	 * @return
	 */
	public List<String> getContinentList();

	/**
	 * 获取国家
	 * @param continentName
	 * @return
	 */
	public List<String> getNationList( String continentName);

	/**
	 * 获取城市
	 * @param continentName
	 * @return
	 */
	public List<String> getCityList( String nationName);

}
