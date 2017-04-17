/**
 *
 */
package com.ztravel.member.opera.service;

import java.util.List;

import com.ztravel.common.areasearch.entity.ProvCityArea;


/**
 * @author
 *
 */
public interface IProvCityAreaService {
	public List<ProvCityArea> getTopLevelRegionList();   //获取所有省和直辖市
	public List<ProvCityArea> getSecondLevelRegion(String topNo);
//	public List<String> getSecondLevelRegion(String topNo);

}
