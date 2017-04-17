package com.ztravel.operator.basicdata.service.impl;

import java.util.LinkedList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ztravel.operator.basicdata.dao.ICountryDAO;
import com.ztravel.operator.basicdata.entity.CountryAreaEntity;
import com.ztravel.operator.basicdata.service.ICountryService;
import com.ztravel.operator.basicdata.util.ExcelUtil;

/**
 * 国家数据
 * @author MH
 */
@Service
public class CountryServiceImpl implements ICountryService {

	@Resource
	private ICountryDAO countryDao;

	@Override
	public void saveCountryData(MultipartFile file) throws Exception {
		LinkedList<CountryAreaEntity> countryList = new LinkedList<CountryAreaEntity>();
		ExcelUtil.resolveCountryExcel(file.getInputStream(), countryList);
		countryDao.insertCountryList(countryList);
	}

	@Override
	public void deleteCountryData() throws Exception {
		countryDao.deleteCountryAreaCollection();
	}

}
