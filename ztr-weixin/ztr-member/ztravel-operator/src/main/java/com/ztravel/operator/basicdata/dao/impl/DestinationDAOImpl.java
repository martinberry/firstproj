package com.ztravel.operator.basicdata.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.Key;
import com.github.jmkgreen.morphia.query.Query;
import com.github.jmkgreen.morphia.query.UpdateOperations;
import com.github.jmkgreen.morphia.query.UpdateResults;
import com.ztravel.operator.basicdata.dao.IDestinationDAO;
import com.ztravel.operator.basicdata.entity.DestinationEntity;

/**
 * MongoDB目的地信息
 * @author MH
 */
@Repository
public class DestinationDAOImpl implements IDestinationDAO {

	@Resource
	private Datastore datastore;

	@Override
	public void saveDestinations(DestinationEntity destinations) {
		Key<DestinationEntity> result = datastore.save(destinations);
	}

	@Override
	public DestinationEntity getDestinations() {
		return datastore.createQuery(DestinationEntity.class).get();
	}

	@Override
	public void deleteDestinationCollection() {
		datastore.getDB().getCollection("destination").drop();
	}

	@Override
	public int updateDefaultDestination(String defaultDest) {
		Query<DestinationEntity> query = datastore.createQuery(DestinationEntity.class);
		UpdateOperations<DestinationEntity> ops = datastore.createUpdateOperations(DestinationEntity.class);
		ops.set("defaultDestination", defaultDest);
		UpdateResults<DestinationEntity> result = datastore.update(query, ops);
		return result.getUpdatedCount();
	}

}
