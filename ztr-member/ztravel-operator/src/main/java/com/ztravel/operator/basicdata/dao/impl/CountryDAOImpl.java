package com.ztravel.operator.basicdata.dao.impl;

import java.util.LinkedList;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.github.jmkgreen.morphia.Datastore;
import com.mongodb.MongoException;
import com.ztravel.operator.basicdata.dao.ICountryDAO;
import com.ztravel.operator.basicdata.entity.CountryAreaEntity;

/**
 * MongoDB国家数据
 * @author MH
 */
@Repository
public class CountryDAOImpl implements ICountryDAO {

	@Resource
	private Datastore datastore;

	@Override
	public void insertCountryList(LinkedList<CountryAreaEntity> countryList) {
		datastore.save(countryList);
	}

	@Override
	public void deleteCountryAreaCollection() throws MongoException {
		datastore.getDB().getCollection("countryArea").drop();
	}

}
