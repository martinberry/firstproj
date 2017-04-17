/**
 *
 */
package com.ztravel.member.opera.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ztravel.common.areasearch.entity.ProvCityArea;
import com.ztravel.member.dao.IProvCityAreaDao;
import com.ztravel.member.opera.service.IProvCityAreaService;

/**
 * @author
 *
 */
@Service
public class ProvCityAreaServiceImpl implements IProvCityAreaService {
	@Resource
	private IProvCityAreaDao provCityAreaDao;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ProvCityArea> getTopLevelRegionList() {
		Map params = new HashMap();
		params.put("areaLevel", "1");
		List<ProvCityArea> topLevelRegionList = provCityAreaDao.select(params);
		return topLevelRegionList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ProvCityArea> getSecondLevelRegion(String topNo) {
		Map params = new HashMap();
		params.put("topNo", topNo);
		List<ProvCityArea> secondLevelRegion = provCityAreaDao.select(params);
		return secondLevelRegion;
	}

}
