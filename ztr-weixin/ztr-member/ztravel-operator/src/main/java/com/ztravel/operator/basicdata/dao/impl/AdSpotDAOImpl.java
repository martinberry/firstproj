package com.ztravel.operator.basicdata.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.github.jmkgreen.morphia.Datastore;
import com.mongodb.MongoException;
import com.ztravel.operator.basicdata.dao.IAdSpotDAO;
import com.ztravel.operator.basicdata.entity.AdSpotEntity;

/**
 * MongoDb 基础数据 -->广告位
 * @author zhoubo
 *
 */
@Repository
public class AdSpotDAOImpl implements IAdSpotDAO {

	@Resource
	private Datastore datastore;

	@Override
	public void insertAdSpotList(List<AdSpotEntity> adSpotList) {
		datastore.save(adSpotList);
	}

	@Override
	public void deleteAll() throws MongoException {
		datastore.getDB().getCollection("AdSpotEntity").drop();
	}

	@Override
	public List<AdSpotEntity> getAdSpotList() throws MongoException {
		return datastore.createQuery(AdSpotEntity.class).order("priority").asList();
	}

}
