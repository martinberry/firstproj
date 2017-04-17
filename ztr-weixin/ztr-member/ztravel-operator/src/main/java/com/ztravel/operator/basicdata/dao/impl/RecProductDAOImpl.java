package com.ztravel.operator.basicdata.dao.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.github.jmkgreen.morphia.Datastore;
import com.mongodb.MongoException;
import com.ztravel.operator.basicdata.dao.IRecProductDAO;
import com.ztravel.operator.basicdata.entity.AdSpotEntity;
import com.ztravel.operator.basicdata.entity.RecProductEntity;


@Repository
public class RecProductDAOImpl implements IRecProductDAO{
	@Resource
	private Datastore datastore;

	@Override
	public void insertRecProductList(List<RecProductEntity> recproductlist) throws MongoException{
		datastore.save(recproductlist);
	}

	@Override
	public void deleteRecProductCollection() throws MongoException{
		datastore.getDB().getCollection("recProduct").drop();
	}

	@Override
	public List<RecProductEntity> searchRecProduct() throws MongoException{
		return datastore.createQuery(RecProductEntity.class).asList();
	}

}
