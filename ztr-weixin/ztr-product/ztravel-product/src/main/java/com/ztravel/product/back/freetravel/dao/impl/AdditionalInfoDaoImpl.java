package com.ztravel.product.back.freetravel.dao.impl;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.query.Query;
import com.github.jmkgreen.morphia.query.UpdateOperations;
import com.github.jmkgreen.morphia.query.UpdateResults;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.product.back.freetravel.dao.IAdditionalInfoDao;
import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.back.freetravel.enums.Status;

@Repository
public class AdditionalInfoDaoImpl implements IAdditionalInfoDao {
	@Resource
	private Datastore datastore ;

	private static final RedisClient redisClient = RedisClient.getInstance();

	@Override
	public Product queryAIById(String id) {
		Query<Product> query = datastore.createQuery(Product.class);
		query.field("id").equal(new ObjectId(id));
		query.retrievedFields(true, "id","pid","status","progress","additionalInfos", "travelTips");
		return query.get();
	}

	@Override
	public int updateAI(Product product) {
		Query<Product> query = datastore.createQuery(Product.class);
		query.field("id").equal(product.getId());
		UpdateOperations<Product> ups = datastore.createUpdateOperations(Product.class);
		if(product.getAdditionalInfos() != null && !product.getAdditionalInfos().isEmpty()){
			ups.set("additionalInfos", product.getAdditionalInfos());
		}
		if(product.getTravelTips() != null && !product.getTravelTips().isEmpty()){
			ups.set("travelTips", product.getTravelTips());
		}
		if(product.getProgress() != null && product.getProgress() == 5){
			ups.set("progress", product.getProgress());
		}
		ups.set("updatedTime", DateTime.now()) ;
		ups.set("updateBy", redisClient.get(OperatorSidHolder.get())) ;
		UpdateResults<Product> results = datastore.update(query, ups);
		return results.getUpdatedCount();
	}

	@Override
	public int release(String id){
		Query<Product> query = datastore.createQuery(Product.class);
		query.field("id").equal(new ObjectId(id));
		UpdateOperations<Product> ups = datastore.createUpdateOperations(Product.class);
		ups.set("status",Status.RELEASED);
		ups.set("updatedTime", DateTime.now()) ;
		ups.set("updateBy", redisClient.get(OperatorSidHolder.get())) ;
		UpdateResults<Product> results = datastore.update(query, ups);
		return results.getUpdatedCount();
	}

}
