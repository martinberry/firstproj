package com.ztravel.common.areasearch.dao;

import java.util.List;
import java.util.Map;

import com.ztravel.common.areasearch.entity.ProvCityArea;
import com.ztravel.common.dao.BaseDao;

public interface IAddressDao extends BaseDao<ProvCityArea> {
	public List<ProvCityArea> selectByNameAndLevel(Map<String,String> params);
}
