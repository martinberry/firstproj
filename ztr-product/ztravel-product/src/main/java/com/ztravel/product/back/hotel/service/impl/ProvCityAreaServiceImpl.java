/**
 *
 */
package com.ztravel.product.back.hotel.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ztravel.product.back.hotel.dao.IProvCityAreaDao;
import com.ztravel.product.back.hotel.po.ProvCityArea;
import com.ztravel.product.back.hotel.service.IProvCityAreaService;

/**
 * @author
 * 由于酒店资源目的地数据库未提供，暂时用国内t_prov_city_area，以后删除
 */
@Service(value="prodRegionService")
public class ProvCityAreaServiceImpl implements IProvCityAreaService {
	@Resource(name="prodRegionDao")
	private IProvCityAreaDao provCityAreaDao;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ProvCityArea> getTopLevelRegionList() {
		Map params = new HashMap();
		params.put("areaLevel", "1");
		List<ProvCityArea> topLevelRegionList = provCityAreaDao.select(params);
		return topLevelRegionList;
	}


}
