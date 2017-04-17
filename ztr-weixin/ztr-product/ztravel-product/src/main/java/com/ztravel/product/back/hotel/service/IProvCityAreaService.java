/**
 *
 */
package com.ztravel.product.back.hotel.service;

import java.util.List;

import com.ztravel.product.back.hotel.po.ProvCityArea;


/**
 * @author
 * 由于酒店资源目的地数据库未提供，暂时用国内t_prov_city_area，以后删除
 */
public interface IProvCityAreaService {
	public List<ProvCityArea> getTopLevelRegionList();   //获取所有省和直辖市

}
