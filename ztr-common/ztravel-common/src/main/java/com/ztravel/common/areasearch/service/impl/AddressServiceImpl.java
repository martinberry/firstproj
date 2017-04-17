package com.ztravel.common.areasearch.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.ztravel.common.areasearch.dao.IAddressDao;
import com.ztravel.common.areasearch.entity.ProvCityArea;
import com.ztravel.common.areasearch.service.IAddressService;


@Service
public class AddressServiceImpl implements IAddressService{
	@Resource
	private IAddressDao adressDaoImpl;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ProvCityArea> getSubAddress(String topNo) {
		List<ProvCityArea> subAddress = new ArrayList<ProvCityArea>();
		Map params = new HashMap();
		if(StringUtils.isNotBlank(topNo)){
			params.put("topNo", topNo);
		}else{
			params.put("areaLevel", "1");//查找顶级(省)
		}
		subAddress = adressDaoImpl.select(params);
		return subAddress;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ProvCityArea> getAddressByNameAndLevel(String parentAreaName, String areaLevel) {
		List<ProvCityArea> address = new ArrayList<ProvCityArea>();
		Map params = new HashMap();
		int intAreaLevel;
		try {
			intAreaLevel = Integer.parseInt(areaLevel);
		} catch (NumberFormatException e) {
			return address;
		}
		if(intAreaLevel == 1){
			params.put("areaLevel", areaLevel);
		}else if(intAreaLevel == 2 || intAreaLevel == 3){
			if(StringUtils.isNotBlank(parentAreaName)){
				params.put("parentAreaName", parentAreaName);
				params.put("parentAreaLevel", ""+(intAreaLevel-1));
			}else{
				return address;
			}
		}else{
			return address;
		}
		address = adressDaoImpl.selectByNameAndLevel(params);
		return address;
	}
}
