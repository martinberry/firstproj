/**
 *
 */
package com.ztravel.product.back.hotel.dao.impl;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.product.back.hotel.dao.IProvCityAreaDao;
import com.ztravel.product.back.hotel.po.ProvCityArea;

/**
 * @author
 * 由于酒店资源目的地数据库未提供，暂时用国内t_prov_city_area，以后删除
 */
@Repository(value="prodRegionDao")
public class ProvCityAreaDaoImpl extends BaseDaoImpl<ProvCityArea> implements IProvCityAreaDao {

}
