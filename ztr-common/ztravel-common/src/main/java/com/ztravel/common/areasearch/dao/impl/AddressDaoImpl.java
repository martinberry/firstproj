package com.ztravel.common.areasearch.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ztravel.common.areasearch.dao.IAddressDao;
import com.ztravel.common.areasearch.entity.ProvCityArea;
import com.ztravel.common.dao.BaseDaoImpl;
@Repository
public class AddressDaoImpl extends BaseDaoImpl<ProvCityArea> implements IAddressDao {
	private static final String SELECT_BY_NAME_AND_LEVEL = ".selectByNameAndLevel";

	@Override
	public List<ProvCityArea> selectByNameAndLevel(Map<String, String> params) {
		return session.selectList(nameSpace + SELECT_BY_NAME_AND_LEVEL, params);
	}

}
