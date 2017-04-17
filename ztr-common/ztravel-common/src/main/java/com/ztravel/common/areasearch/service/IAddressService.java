package com.ztravel.common.areasearch.service;

import java.util.List;

import com.ztravel.common.areasearch.entity.ProvCityArea;



public interface IAddressService {
	/**
	 * 国内地址查询,传null表示查找顶级的省(直辖市)
	 * @param topNo
	 * @return
	 */
	public List<ProvCityArea> getSubAddress(String topNo);
	/**
	 * 根据名称，级别查询地址
	 * @param areaName
	 * @param areaLevel 字符串1,2,3
	 * @return
	 */
	public List<ProvCityArea> getAddressByNameAndLevel(String parentAreaName, String areaLevel);
}
