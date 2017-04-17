package com.ztravel.operator.basicdata.dao;

import java.util.LinkedList;

import com.mongodb.MongoException;
import com.ztravel.operator.basicdata.entity.CountryAreaEntity;

/**
 * MongoDB国家数据
 * @author MH
 */
public interface ICountryDAO {
	/**
	 * 保存国家数据List
	 * @param countryList
	 */
	public void insertCountryList(LinkedList<CountryAreaEntity> countryList);
	/**
	 * 删除国家地区库
	 * @throws MongoException
	 */
	public void deleteCountryAreaCollection() throws MongoException;

}
