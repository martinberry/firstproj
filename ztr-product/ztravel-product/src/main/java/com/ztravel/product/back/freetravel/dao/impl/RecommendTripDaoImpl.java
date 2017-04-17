package com.ztravel.product.back.freetravel.dao.impl;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.query.Query;
import com.github.jmkgreen.morphia.query.UpdateOperations;
import com.github.jmkgreen.morphia.query.UpdateResults;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.product.back.freetravel.dao.IRecommendTripDao;
import com.ztravel.product.back.freetravel.entity.Product;

@Repository
public class RecommendTripDaoImpl implements IRecommendTripDao {

	@Resource
	private Datastore datastore ;

	private static final RedisClient redisClient = RedisClient.getInstance();

	@Override
	public Product queryRTById(String id) {
		Query<Product> query = datastore.createQuery(Product.class);
		query.field("id").equal(new ObjectId(id));
		query.retrievedFields(true, "id","pid","pName","tripDays","tripNights","progress", "status", "recommendTrips");
		return query.get();
	}

	@Override
	public int updateRT(Product product) {
		Query<Product> query = datastore.createQuery(Product.class);
		query.field("id").equal(product.getId());
		UpdateOperations<Product> ups = datastore.createUpdateOperations(Product.class);
		if(CollectionUtils.isNotEmpty(product.getRecommendTrips()))ups.set("recommendTrips", product.getRecommendTrips());
		if(product.getProgress() != null && product.getProgress() == 4)ups.set("progress", product.getProgress());
		ups.set("updatedTime", DateTime.now()) ;
		ups.set("updateBy", redisClient.get(OperatorSidHolder.get())) ;
		UpdateResults<Product> results = datastore.update(query, ups);
		return results.getUpdatedCount();
	}

}
